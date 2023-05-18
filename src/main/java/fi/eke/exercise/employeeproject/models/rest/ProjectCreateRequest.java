package fi.eke.exercise.employeeproject.models.rest;

import java.util.Date;

public record ProjectCreateRequest(String name, Date startDate, Date endDate) {
    public ProjectCreateRequest(String name, Date startDate, Date endDate) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name for a project is mandatory");
        if (startDate == null) throw new IllegalArgumentException("startdate for a project is mandatory");
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
