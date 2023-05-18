package fi.eke.exercise.employeeproject.repository;

import fi.eke.exercise.employeeproject.models.Department;
import fi.eke.exercise.employeeproject.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query(value = "SELECT * from Department where status='active'", nativeQuery = true)
    List<Department> findAllActiveDepartment();

    @Query(value = "SELECT * from Department where status='removed'", nativeQuery = true)
    List<Department> findAllInActiveDepartments();

    @Query(value = "SELECT * from Department where status='active' and dept_id=?1", nativeQuery = true)
    Optional<Department> findActiveDepartmentById(Integer id);
}
