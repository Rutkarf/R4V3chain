package io.r4v3chain.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "r4v3chain.mail")
public class MailProperties {

    private String host = "";
    private int port = 587;
    private String username = "";
    private String password = "";
    private String from = "noreply@r4v3chain.review";
    private boolean starttls = true;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? "" : host.trim();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? "" : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? "" : password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from == null || from.isBlank() ? "noreply@r4v3chain.review" : from.trim();
    }

    public boolean isStarttls() {
        return starttls;
    }

    public void setStarttls(boolean starttls) {
        this.starttls = starttls;
    }

    public boolean isConfigured() {
        return host != null && !host.isBlank();
    }
}
