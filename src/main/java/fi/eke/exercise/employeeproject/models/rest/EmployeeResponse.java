/***
 * This class represents the EmployeeResponse.
 * This is a DTO for EmployeeResponse. This would be used in restapi's, ResponseBody.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.models.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record EmployeeResponse(Integer empId, String firstname, String familyName, String email, Integer deptId, @JsonProperty("projects") List<ProjectResponse> projectResponses) {
}
