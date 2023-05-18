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
