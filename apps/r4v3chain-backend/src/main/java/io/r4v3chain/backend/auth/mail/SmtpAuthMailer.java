package io.r4v3chain.backend.auth.mail;

import io.r4v3chain.backend.auth.application.AuthException;
import io.r4v3chain.backend.config.MailProperties;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class SmtpAuthMailer implements AuthMailer {

    private final MailProperties mailProperties;

    public SmtpAuthMailer(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    @Override
    public boolean isConfigured() {
        return mailProperties.isConfigured();
    }

    @Override
    public void sendVerificationCode(String to, String username, String code) {
        if (!isConfigured()) {
            throw new AuthException(
                    503,
                    "L'envoi d'email n'est pas configuré. Renseignez R4V3CHAIN_MAIL_HOST, R4V3CHAIN_MAIL_USERNAME et R4V3CHAIN_MAIL_PASSWORD."
            );
        }

        try {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(mailProperties.getHost());
            sender.setPort(mailProperties.getPort());
            if (!mailProperties.getUsername().isBlank()) {
                sender.setUsername(mailProperties.getUsername());
                sender.setPassword(mailProperties.getPassword());
            }
            sender.setJavaMailProperties(sessionProperties());

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, StandardCharsets.UTF_8.name());
            helper.setFrom(mailProperties.getFrom());
            helper.setTo(to);
            helper.setSubject("R4v3chain — confirmez votre inscription");
            helper.setText("""
                    Bonjour %s,

                    Votre code de confirmation R4v3chain est : %s

                    Il expire dans 15 minutes. Si vous n'êtes pas à l'origine de cette inscription, ignorez ce message.
                    """.formatted(username, code));
            sender.send(message);
        } catch (AuthException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new AuthException(502, "Impossible d'envoyer l'email de confirmation. Vérifiez la configuration SMTP.");
        }
    }

    private Properties sessionProperties() {
        Properties properties = new Properties();
        boolean authenticate = !mailProperties.getUsername().isBlank();
        properties.put("mail.smtp.auth", Boolean.toString(authenticate));
        properties.put("mail.smtp.starttls.enable", Boolean.toString(mailProperties.isStarttls()));
        properties.put("mail.smtp.starttls.required", Boolean.toString(mailProperties.isStarttls()));
        properties.put("mail.smtp.connectiontimeout", "8000");
        properties.put("mail.smtp.timeout", "8000");
        properties.put("mail.smtp.writetimeout", "8000");
        if (mailProperties.getPort() == 465) {
            properties.put("mail.smtp.ssl.enable", "true");
        }
        return properties;
    }
}
