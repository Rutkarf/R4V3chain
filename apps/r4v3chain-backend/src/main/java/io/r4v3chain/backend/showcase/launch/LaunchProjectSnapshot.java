package io.r4v3chain.backend.showcase.launch;

import io.r4v3chain.backend.showcase.model.LaunchProject;

import java.util.List;

public class LaunchProjectSnapshot {

    private List<LaunchProject> projects = List.of();

    public LaunchProjectSnapshot() {
    }

    public List<LaunchProject> getProjects() {
        return projects;
    }

    public void setProjects(List<LaunchProject> projects) {
        this.projects = projects != null ? projects : List.of();
    }
}
