package com.spire.ppmtool.services;

import com.spire.ppmtool.domain.Project;
import com.spire.ppmtool.exceptions.ProjectIdException;
import com.spire.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null)  {
            throw new ProjectIdException("ProjectID " + projectId+ " does not exists");
        }

        return project;
    }

    public Iterable<Project> findAllProjects() {
       return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId);

        if(project == null) {
            throw new ProjectIdException("Cannot Project with Id '"+ projectId+"'. This project does not exist");
        }

         projectRepository.delete(project);
    }

}
