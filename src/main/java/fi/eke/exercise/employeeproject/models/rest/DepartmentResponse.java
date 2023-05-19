/***
 * This class represents the DepartmentResponse.
 * This is a DTO for DepartmentResponse. This would be used in restapi's, ResponseBody.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.models.rest;

public record DepartmentResponse(Integer deptId, String name, String city) {
}
