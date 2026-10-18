package io.r4v3chain.backend.persistence;

import io.r4v3chain.backend.showcase.launch.store.LaunchProjectStore;
import io.r4v3chain.backend.showcase.model.LaunchProject;
import io.r4v3chain.backend.showcase.model.LaunchStatus;
import io.r4v3chain.backend.persistence.entity.LaunchProjectEntity;
import io.r4v3chain.backend.persistence.repository.LaunchProjectJpaRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
@ConditionalOnProperty(name = "r4v3chain.persistence.mode", havingValue = "postgres")
public class JpaLaunchProjectStore implements LaunchProjectStore {

    private final LaunchProjectJpaRepository repository;

    public JpaLaunchProjectStore(LaunchProjectJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LaunchProject> findAll() {
        return repository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    @Transactional
    public void save(LaunchProject project) {
        repository.save(toEntity(project));
    }

    @Override
    @Transactional
    public void saveAll(List<LaunchProject> projects) {
        repository.deleteAllInBatch();
        if (projects != null && !projects.isEmpty()) {
            repository.saveAll(projects.stream().map(this::toEntity).toList());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBySymbol(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            return false;
        }
        return repository.existsBySymbolIgnoreCase(symbol.trim());
    }

    private LaunchProject toModel(LaunchProjectEntity entity) {
        return new LaunchProject(
                entity.getId(),
                entity.getName(),
                entity.getSymbol(),
                LaunchStatus.valueOf(entity.getStatus()),
                entity.getRaisedAmount(),
                entity.getTargetAmount(),
                entity.getCreatedAt(),
                entity.getLogoUrl(),
                entity.getDescription(),
                entity.getChain(),
                entity.getWhitepaperUrl(),
                entity.getWebsite(),
                entity.getLaunchDate()
        );
    }

    private LaunchProjectEntity toEntity(LaunchProject project) {
        LaunchProjectEntity entity = new LaunchProjectEntity();
        entity.setId(project.getId());
        entity.setName(project.getName());
        entity.setSymbol(project.getSymbol());
        entity.setStatus(project.getStatus().name());
        entity.setRaisedAmount(project.getRaisedAmount() != null ? project.getRaisedAmount() : BigDecimal.ZERO);
        entity.setTargetAmount(project.getTargetAmount() != null ? project.getTargetAmount() : BigDecimal.ZERO);
        entity.setCreatedAt(project.getCreatedAt() != null ? project.getCreatedAt() : Instant.now());
        entity.setLogoUrl(project.getLogoUrl());
        entity.setDescription(project.getDescription());
        entity.setChain(project.getChain());
        entity.setWhitepaperUrl(project.getWhitepaperUrl());
        entity.setWebsite(project.getWebsite());
        entity.setLaunchDate(project.getLaunchDate());
        return entity;
    }
}
