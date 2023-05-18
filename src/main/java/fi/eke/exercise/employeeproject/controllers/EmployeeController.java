/***
 * This class represents the Employee Controller.
 * This class has all the REST endpoints related to a employee.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.controllers;

import fi.eke.exercise.employeeproject.models.rest.*;
import fi.eke.exercise.employeeproject.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> add(@RequestBody EmployeeCreateRequest employeeCreateRequest) {
        Optional<EmployeeResponse> employeeOptional = employeeService
                .addEmployee(employeeCreateRequest);
        if (employeeOptional.isPresent()) {
            return ResponseEntity.ok(employeeOptional.get());
        }
        else  {
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping
    public ResponseEntity<SuccessMessage> remove(@RequestBody IdRequest employeeRequest) {
        boolean empRemoved = employeeService
                .removeEmployee(employeeRequest);
        if (empRemoved) {
            return ResponseEntity.ok(new SuccessMessage("Employed marked removed."));
        }
        else  {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<EmployeeResponse> modify(@RequestBody EmployeeModifyRequest employeeModifyRequest) {
        Optional<EmployeeResponse> employeeOptional = employeeService
                .modifyEmployee(employeeModifyRequest);
        if (employeeOptional.isPresent()) {
            return ResponseEntity.ok(employeeOptional.get());
        }
        else  {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> list(@RequestParam(value = "status", required = false) String status) {
        List<EmployeeResponse> employeeResponseList = null;
        if (status == null || status.isEmpty() || status.equalsIgnoreCase("active")) {
            employeeResponseList = employeeService
                    .listActive();
        }
        else if (status.equalsIgnoreCase("inactive")) {
            employeeResponseList = employeeService
                    .listInActive();
        }
        else if (status.equalsIgnoreCase("all")) {
            employeeResponseList = employeeService
                    .list();
        }
        return ResponseEntity.ok(employeeResponseList);
    }
    @PostMapping(value = "/project")
    public ResponseEntity<EmployeeResponse> addProject(@RequestBody EmployeeModifyRequest employeeModifyRequest) {
        EmployeeResponse employeeResponse = employeeService.addProject(employeeModifyRequest);
        if (employeeResponse != null) {
            return ResponseEntity.ok(employeeResponse);
        }
        else {
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping(value = "/project")
    public ResponseEntity<EmployeeResponse> removeProject(@RequestBody EmployeeModifyRequest employeeModifyRequest) {
        EmployeeResponse employeeResponse = employeeService.removeProject(employeeModifyRequest);
        if (employeeResponse != null) {
            return ResponseEntity.ok(employeeResponse);
        }
        else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
