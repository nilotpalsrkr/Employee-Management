/***
 * This class represents the Department Controller.
 * This class has all the REST endpoints related to a department.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.controllers;

import fi.eke.exercise.employeeproject.models.rest.*;
import fi.eke.exercise.employeeproject.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponse> add(@RequestBody DepartmentCreateRequest departmentCreateRequest) {
        Optional<DepartmentResponse> departmentResponseOptional = departmentService
                .add(departmentCreateRequest);
        if (departmentResponseOptional.isPresent()) {
            return ResponseEntity.ok(departmentResponseOptional.get());
        }
        else  {
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping
    public ResponseEntity<SuccessMessage> remove(@RequestBody IdRequest employeeRequest) {
        boolean departmentemoved = departmentService
                .remove(employeeRequest);
        if (departmentemoved) {
            return ResponseEntity.ok(new SuccessMessage("Department marked removed."));
        }
        else  {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<DepartmentResponse> modify(@RequestBody DepartmentModifyRequest departmentModifyRequest) {
        Optional<DepartmentResponse> departmentResponse = departmentService
                .modify(departmentModifyRequest);
        if (departmentResponse.isPresent()) {
            return ResponseEntity.ok(departmentResponse.get());
        }
        else  {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> list(@RequestParam(value = "status", required = false) String status) {
        List<DepartmentResponse> departmentResponses = null;
        if (status == null || status.isEmpty() || status.equalsIgnoreCase("active")) {
            departmentResponses = departmentService
                    .listActive();
        }
        else if (status.equalsIgnoreCase("inactive")) {
            departmentResponses = departmentService
                    .listInActive();
        }
        else if (status.equalsIgnoreCase("all")) {
            departmentResponses = departmentService
                    .list();
        }
        return ResponseEntity.ok(departmentResponses);
    }
}
