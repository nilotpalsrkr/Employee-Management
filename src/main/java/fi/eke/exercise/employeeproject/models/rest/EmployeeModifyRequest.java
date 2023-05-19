/***
 * This class represents the EmployeeModifyRequest.
 * This is a DTO for EmployeeModifyRequest. This would be used in restapi's, RequestBody.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.models.rest;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;

public record EmployeeModifyRequest(Integer empId, String firstname, String familyName, String email, Integer deptId, List<Integer> projectIds) {
    public EmployeeModifyRequest {
        if (empId == null) throw new IllegalArgumentException("EmpId is mandatory to modify an employee details.");
    }
}
