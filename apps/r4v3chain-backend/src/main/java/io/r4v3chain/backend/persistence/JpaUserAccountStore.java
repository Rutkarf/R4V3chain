package io.r4v3chain.backend.persistence;

import io.r4v3chain.backend.auth.application.AuthException;
import io.r4v3chain.backend.auth.application.AuthNormalizer;
import io.r4v3chain.backend.auth.model.UserAccount;
import io.r4v3chain.backend.auth.store.UserAccountStore;
import io.r4v3chain.backend.persistence.entity.UserEntity;
import io.r4v3chain.backend.persistence.repository.UserJpaRepository;
import io.r4v3chain.backend.shared.utils.WalletValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@ConditionalOnProperty(name = "r4v3chain.persistence.mode", havingValue = "postgres")
public class JpaUserAccountStore implements UserAccountStore {

    private final UserJpaRepository userRepository;

    public JpaUserAccountStore(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAccount> findById(String id) {
        return parseUuid(id).flatMap(userRepository::findById).map(UserEntityMapper::toAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAccount> findByUsername(String username) {
        return userRepository
                .findByUsername(AuthNormalizer.normalizeUsername(username))
                .map(UserEntityMapper::toAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAccount> findByEmail(String email) {
        return userRepository
                .findByEmail(AuthNormalizer.normalizeEmail(email))
                .map(UserEntityMapper::toAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAccount> findByWalletAddress(String walletAddress) {
        String normalized = WalletValidator.normalize(walletAddress);
        if (normalized == null || normalized.isBlank()) {
            return Optional.empty();
        }

        return userRepository.findByWalletAddressIgnoreCase(normalized).map(UserEntityMapper::toAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAccount> findAll() {
        return userRepository.findAll().stream().map(UserEntityMapper::toAccount).toList();
    }

    @Override
    @Transactional
    public UserAccount create(UserAccount account) {
        UserEntity saved = userRepository.save(UserEntityMapper.toEntity(account));
        return UserEntityMapper.toAccount(saved);
    }

    @Override
    @Transactional
    public UserAccount updateWallet(String userId, String walletAddress, String publicKey) {
        UserEntity entity = parseUuid(userId)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new AuthException(404, "Utilisateur introuvable"));

        entity.setWalletAddress(walletAddress);
        entity.setWalletPublicKey(publicKey);
        return UserEntityMapper.toAccount(userRepository.save(entity));
    }

    @Override
    @Transactional
    public UserAccount updatePassword(String userId, String passwordHash) {
        UserEntity entity = parseUuid(userId)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new AuthException(404, "Utilisateur introuvable"));

        entity.setPasswordHash(passwordHash);
        entity.setPasswordSalt("");
        return UserEntityMapper.toAccount(userRepository.save(entity));
    }

    @Override
    @Transactional
    public UserAccount markEmailVerified(String userId) {
        UserEntity entity = requireUser(userId);
        entity.setEmailVerified(true);
        return UserEntityMapper.toAccount(userRepository.save(entity));
    }

    @Override
    @Transactional
    public UserAccount updateTotp(String userId, String totpSecret, boolean totpEnabled) {
        UserEntity entity = requireUser(userId);
        entity.setTotpSecret(totpSecret);
        entity.setTotpEnabled(totpEnabled);
        return UserEntityMapper.toAccount(userRepository.save(entity));
    }

    @Override
    @Transactional
    public UserAccount updateRole(String userId, io.r4v3chain.backend.auth.model.UserRole role) {
        UserEntity entity = parseUuid(userId)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new AuthException(404, "Utilisateur introuvable"));
        entity.setRole(role == null ? "USER" : role.name());
        return UserEntityMapper.toAccount(userRepository.save(entity));
    }

    private UserEntity requireUser(String userId) {
        return parseUuid(userId)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new AuthException(404, "Utilisateur introuvable"));
    }

    private Optional<UUID> parseUuid(String id) {
        try {
            return Optional.of(UUID.fromString(id));
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }
}
