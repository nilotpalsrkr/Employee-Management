/***
 * This class represents the ProjectRepository.
 * This is a JPA Repository interface for Project.
 * This has few custom methods defined that acts on active entities etc!
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.repository;

import fi.eke.exercise.employeeproject.models.Department;
import fi.eke.exercise.employeeproject.models.Employee;
import fi.eke.exercise.employeeproject.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query(value = "SELECT * from Project where status='active'", nativeQuery = true)
    List<Project> findAllActiveProject();

    @Query(value = "SELECT * from Project where status='removed'", nativeQuery = true)
    List<Project> findAllInActiveProject();
}
