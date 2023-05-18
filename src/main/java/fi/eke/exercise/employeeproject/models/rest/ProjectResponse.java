package fi.eke.exercise.employeeproject.models.rest;

import java.util.Date;

public record ProjectResponse(Integer projectId, String name, Date startDate, Date endDate) {
}
