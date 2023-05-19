/***
 * This class represents the EmployeeRepository.
 * This is a JPA Repository interface for Employee.
 * This has few custom methods defined that acts on active entities etc!
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.repository;

import fi.eke.exercise.employeeproject.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "SELECT * from Employee where status='active'", nativeQuery = true)
    List<Employee> findAllActiveEmployee();

    @Query(value = "SELECT * from Employee where status='removed'", nativeQuery = true)
    List<Employee> findAllInActiveEmployee();
}
