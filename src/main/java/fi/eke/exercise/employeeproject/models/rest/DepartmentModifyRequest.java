package fi.eke.exercise.employeeproject.models.rest;

public record DepartmentModifyRequest(Integer deptId, String name, String city) {
    public DepartmentModifyRequest(Integer deptId, String name, String city) {
        if (deptId == null) throw new IllegalArgumentException("deptId is needed for modification.");

        this.deptId = deptId;
        this.name = name;
        this.city = city;
    }
}
