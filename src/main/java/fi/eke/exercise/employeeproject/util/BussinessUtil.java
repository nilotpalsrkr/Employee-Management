/***
 * This class represents the BussinessUtil.
 * This is a Util class for various if the bussiness util
 * methods required accros the classes.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.util;

import fi.eke.exercise.employeeproject.models.Department;
import fi.eke.exercise.employeeproject.models.Project;
import fi.eke.exercise.employeeproject.models.rest.DepartmentResponse;
import fi.eke.exercise.employeeproject.models.rest.ProjectResponse;

public class BussinessUtil {
    public static ProjectResponse translateToProjectResponse(Project project) {
        return new ProjectResponse(project.getProjectId(), project.getName(), project.getStartingDate(), project.getEndDate());
    }
    public static DepartmentResponse translateToDepartmentResponse(Department department) {
        return new DepartmentResponse(department.getDepartmentId(), department.getName(), department.getCity());
    }
}
