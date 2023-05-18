package fi.eke.exercise.employeeproject.models.rest;

import java.util.Date;

public record ProjectModifyRequest(Integer projId, String name, Date startDate, Date endDate) {
    public ProjectModifyRequest(Integer projId, String name, Date startDate, Date endDate) {
        if (projId == null) throw new IllegalArgumentException("projId for a project is mandatory for modification");
        this.projId = projId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
