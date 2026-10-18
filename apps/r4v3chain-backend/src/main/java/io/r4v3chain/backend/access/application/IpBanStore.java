package io.r4v3chain.backend.access.application;

/** Persistance des bans IP (mémoire ou Postgres). */
public interface IpBanStore {

    boolean exists(String ipAddress);

    void save(String ipAddress, String reason, String userAgent);
}
