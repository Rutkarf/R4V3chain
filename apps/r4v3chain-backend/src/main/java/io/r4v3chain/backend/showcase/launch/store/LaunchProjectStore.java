package io.r4v3chain.backend.showcase.launch.store;

import io.r4v3chain.backend.showcase.model.LaunchProject;

import java.util.List;

public interface LaunchProjectStore {

    List<LaunchProject> findAll();

    void save(LaunchProject project);

    void saveAll(List<LaunchProject> projects);

    boolean existsBySymbol(String symbol);
}
