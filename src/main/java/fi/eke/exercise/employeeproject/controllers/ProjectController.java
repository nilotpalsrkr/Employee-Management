/***
 * This class represents the Project Controller.
 * This class has all the REST endpoints related to a project.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.controllers;

import fi.eke.exercise.employeeproject.models.rest.*;
import fi.eke.exercise.employeeproject.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/proj")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> add(@RequestBody ProjectCreateRequest projectCreateRequest) {
        Optional<ProjectResponse> employeeOptional = projectService
                .add(projectCreateRequest);
        if (employeeOptional.isPresent()) {
            return ResponseEntity.ok(employeeOptional.get());
        }
        else  {
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping
    public ResponseEntity<SuccessMessage> remove(@RequestBody IdRequest employeeRequest) {
        boolean empRemoved = projectService
                .remove(employeeRequest);
        if (empRemoved) {
            return ResponseEntity.ok(new SuccessMessage("Project marked removed."));
        }
        else  {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<ProjectResponse> modify(@RequestBody ProjectModifyRequest projectModifyRequest) {
        Optional<ProjectResponse> projectResponseOptional = projectService
                .modify(projectModifyRequest);
        if (projectResponseOptional.isPresent()) {
            return ResponseEntity.ok(projectResponseOptional.get());
        }
        else  {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> list(@RequestParam(value = "status", required = false) String status) {
        List<ProjectResponse> employeeResponseList = null;
        if (status == null || status.isEmpty() || status.equalsIgnoreCase("active")) {
            employeeResponseList = projectService
                    .listActive();
        }
        else if (status.equalsIgnoreCase("inactive")) {
            employeeResponseList = projectService
                    .listInActive();
        }
        else if (status.equalsIgnoreCase("all")) {
            employeeResponseList = projectService
                    .list();
        }
        return ResponseEntity.ok(employeeResponseList);
    }
}
