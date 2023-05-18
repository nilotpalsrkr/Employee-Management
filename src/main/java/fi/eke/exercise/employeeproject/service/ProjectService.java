package fi.eke.exercise.employeeproject.service;

import fi.eke.exercise.employeeproject.exception.ResourceNotFoundException;
import fi.eke.exercise.employeeproject.models.Project;
import fi.eke.exercise.employeeproject.models.rest.*;
import fi.eke.exercise.employeeproject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fi.eke.exercise.employeeproject.util.BussinessUtil.translateToProjectResponse;
import static fi.eke.exercise.employeeproject.util.Constants.PROJECT_ID_NOT_FOUND;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Optional<ProjectResponse> add(ProjectCreateRequest projectCreateRequest) {
        if (projectCreateRequest == null) {
            throw new ResourceNotFoundException("PROJECT_DETAILS_NOT_PROVIDED", "Project details to be provided", "");
        }
        else  {
            Project e = projectRepository.save(Project.builder().name(projectCreateRequest.name())
                    .startingDate(projectCreateRequest.startDate())
                    .endDate(projectCreateRequest.endDate())
                    .status("active")
                    .build());
            if (e != null) {
                return Optional.of(translateToProjectResponse(e));
            }
            else return Optional.empty();
        }
    }
    public boolean remove(IdRequest idRequest) throws IllegalArgumentException{
        if (idRequest == null) {
            throw new ResourceNotFoundException("PROJECT_ID_NOT_PROVIDED", "ProjId is to be provided", idRequest.id());
        }
        else  {
            Optional<Project> e = projectRepository.findById(idRequest.id());
            if (e.isPresent()) {
                Project project = e.get();
                if (project.getStatus()!=null && project.getStatus().equals("active")) {
                    project.setStatus("removed");
                    projectRepository.save(e.get());
                    return true;
                }
                else if (project.getStatus().equals("removed")) {
                    throw new ResourceNotFoundException("PROJECT_ALREADY_REMOVED", "The project already seems to be removed", idRequest.id());
                }
            }
            else throw new ResourceNotFoundException("PROJECT_ID_NOT_FOUND", "ProjectId dosent exist", idRequest.id());
        }
        return false;
    }

    public Optional<ProjectResponse> modify(ProjectModifyRequest projectModifyRequest) {
        Optional<Project> projectOptional = projectRepository.findById(projectModifyRequest.projId());

        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();


            if (projectModifyRequest.name() != null && !projectModifyRequest.name().isEmpty()) {
                project.setName(projectModifyRequest.name());
            }
            if (projectModifyRequest.startDate() != null && projectModifyRequest.startDate() != null) {
                project.setStartingDate(projectModifyRequest.startDate());
            }
            if (projectModifyRequest.endDate() != null && projectModifyRequest.endDate() != null) {
                project.setEndDate(projectModifyRequest.endDate());
            }

            Project projectModified = projectRepository.save(project);
            if (projectModified != null) {
                return Optional.of(translateToProjectResponse(project));
            }
        }
        else {
            throw new ResourceNotFoundException(PROJECT_ID_NOT_FOUND, "ProjectId dosent exist", projectModifyRequest.projId());
        }
        return Optional.empty();
    }

    public List<ProjectResponse> listActive() {
        List<Project> allActiveProject = projectRepository.findAllActiveProject();
        List<ProjectResponse> responseList = null;
        if (allActiveProject.size() > 0) {
            responseList = new ArrayList<>();
            for (Project pro : allActiveProject) {
                responseList.add(translateToProjectResponse(pro));
            }
        }
        return responseList;
    }

    public List<ProjectResponse> listInActive() {
        List<Project> allInActiveProject = projectRepository.findAllInActiveProject();
        List<ProjectResponse> responseList = null;
        if (allInActiveProject.size() > 0) {
            responseList = new ArrayList<>();
            for (Project project : allInActiveProject) {
                responseList.add(translateToProjectResponse(project));
            }
        }
        return responseList;
    }

    public List<ProjectResponse> list() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectResponse> responseList = null;
        if (projects.size() > 0) {
            responseList = new ArrayList<>();
            for (Project project : projects) {
                responseList.add(translateToProjectResponse(project));
            }
        }
        return responseList;
    }

    public Optional<Project> getProjectById(Integer projectId) {
        return projectRepository.findById(projectId);
    }
}
