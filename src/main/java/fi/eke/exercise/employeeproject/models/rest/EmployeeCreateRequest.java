/***
 * This class represents the EmployeeCreateRequest.
 * This is a DTO for EmployeeCreateRequest. This would be used in restapi's, RequestBody.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.models.rest;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;

public record EmployeeCreateRequest(String firstname, String familyName, String email, Integer deptId, List<Integer> projectId) {
    public EmployeeCreateRequest(String firstname, String familyName, String email, Integer deptId, List<Integer> projectId) {
        if (firstname == null || firstname.isEmpty()) throw new IllegalArgumentException("Firstname for an employee is mandatory");
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Email for an employee is mandatory");
        if (!EmailValidator.getInstance()
                .isValid(email)) throw new IllegalArgumentException("Email should be in correct format");

        this.firstname = firstname;
        this.familyName = familyName;
        this.email = email;
        this.deptId = deptId;
        this.projectId = projectId;
    }
}
