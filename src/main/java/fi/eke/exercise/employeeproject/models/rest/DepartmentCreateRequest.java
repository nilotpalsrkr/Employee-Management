package fi.eke.exercise.employeeproject.models.rest;

import org.apache.commons.validator.routines.EmailValidator;

public record DepartmentCreateRequest(String name, String city) {
    public DepartmentCreateRequest(String name, String city) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name for a department is mandatory");
        if (city == null || city.isEmpty()) throw new IllegalArgumentException("City for a department is mandatory");
        this.name = name;
        this.city = city;
    }
}
