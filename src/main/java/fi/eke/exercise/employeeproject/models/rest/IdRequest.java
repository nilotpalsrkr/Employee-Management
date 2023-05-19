/***
 * This class represents the IdRequest.
 * This is a DTO for IDRequest. This would be used in restapi's, RequestBody.
 * This is typically used when an id is used in request body and this typically would be unique.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.models.rest;

public record IdRequest(Integer id) {
}
