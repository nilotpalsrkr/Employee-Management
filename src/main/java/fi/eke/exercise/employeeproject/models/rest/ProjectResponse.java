/***
 * This class represents the ProjectResponse.
 * This is a DTO for ProjectResponse. This would be used in restapi's, ResponseBody.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.models.rest;

import java.util.Date;

public record ProjectResponse(Integer projectId, String name, Date startDate, Date endDate) {
}
