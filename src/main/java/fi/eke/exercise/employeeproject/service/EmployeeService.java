/***
 * This class represents the EmployeeService.
 * This is a service layer for Employee Service.
 * This has all the methods that the controller would
 * need to interact to the DB layer via this service layer.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.service;

import fi.eke.exercise.employeeproject.exception.ResourceNotFoundException;
import fi.eke.exercise.employeeproject.models.Department;
import fi.eke.exercise.employeeproject.models.Employee;
import fi.eke.exercise.employeeproject.models.Project;
import fi.eke.exercise.employeeproject.models.rest.*;
import fi.eke.exercise.employeeproject.repository.EmployeeRepository;
import fi.eke.exercise.employeeproject.util.BussinessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fi.eke.exercise.employeeproject.util.BussinessUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static fi.eke.exercise.employeeproject.util.Constants.*;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired DepartmentService departmentService;

    @Autowired ProjectService projectService;

    public Optional<EmployeeResponse> addEmployee(EmployeeCreateRequest employeeCreateRequest) {
        //StringBuilder message = new StringBuilder();
        if (employeeCreateRequest == null) {
            throw new ResourceNotFoundException("EMPLOYEE_DETAILS_NOT_PROVIDED", "Employee details to be provided", "");
        }
        else  {
            Department department = null;
            List<Project> projects = new ArrayList<>();
            if (employeeCreateRequest.deptId() != null) {
                department = departmentService.getDepartmentById(employeeCreateRequest.deptId())
                        .orElseThrow(() -> new ResourceNotFoundException(DEPT_ID_NOT_FOUND, "deptId not found", employeeCreateRequest.deptId()));

                    if (!department.getStatus().equals(ACTIVE)) {
                        throw new ResourceNotFoundException("DEPARTMENT_NO_LONGER_EXITS", "Department was removed and cannot be associated", employeeCreateRequest.deptId());
                    }
            }
            if (employeeCreateRequest.projectId() != null) {
                employeeCreateRequest.projectId()
                        .forEach(x -> {
                            projects.add(projectService.getProjectById(x)
                                    .orElseThrow(() -> new ResourceNotFoundException(PROJECT_ID_NOT_FOUND, "Project Not found", employeeCreateRequest.projectId())));
                        });
            }
            Employee e = employeeRepository.save( Employee.builder()
                    .firstName(employeeCreateRequest.firstname())
                    .FamilyName(employeeCreateRequest.familyName())
                    .Email(employeeCreateRequest.email())
                    .status("active")
                    .department(department)
                    .projects(projects)
                    .build());
            if (e != null) {
                return Optional.of(translateToResponse(e));
            }

            else return Optional.empty();
        }
    }
    public boolean removeEmployee(IdRequest idRequest) throws IllegalArgumentException{
        if (idRequest == null) {
            throw new ResourceNotFoundException("EMP_ID_NOT_PROVIDED", "EmpId is to be provided",idRequest.id());
        }
        else  {
            Optional<Employee> e = employeeRepository.findById(idRequest.id());
            if (e.isPresent()) {
                Employee employee = e.get();
                if (employee.getStatus() !=null && employee.getStatus().equals("active")) {
                    employee.setStatus("removed");
                    employeeRepository.save(e.get());
                    return true;
                }
                else if (employee.getStatus().equals("removed")) {
                    throw new ResourceNotFoundException("EMP_ALREADY_REMOVED", "The employee already seems to be removed", idRequest.id());
                }
            }
            else throw new ResourceNotFoundException("EMP_ID_NOT_FOUND", "EmpId dosent exist", idRequest.id());
        }
        return false;
    }

    public Optional<EmployeeResponse> modifyEmployee(EmployeeModifyRequest employeeModifyRequest) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeModifyRequest.empId());
        Department department = null;
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();

            if (employeeModifyRequest.email() != null && !employeeModifyRequest.email().isEmpty()) {
                employee.setEmail(employeeModifyRequest.email());
            }
            if (employeeModifyRequest.firstname() != null && !employeeModifyRequest.firstname().isEmpty()) {
                employee.setFirstName(employeeModifyRequest.firstname());
            }
            if (employeeModifyRequest.familyName()!=null && !employeeModifyRequest.familyName().isEmpty()) {
                employee.setFamilyName(employeeModifyRequest.familyName());
            }

            if (employeeModifyRequest.deptId() != null) {
                department = departmentService.getDepartmentById(employeeModifyRequest.deptId())
                        .orElseThrow(() -> new ResourceNotFoundException(DEPT_ID_NOT_FOUND, "deptId not found.", employeeModifyRequest.deptId()));

                if (!department.getStatus().equals(ACTIVE)) {
                    throw new ResourceNotFoundException("DEPARTMENT_NO_LONGER_EXITS", "Department was removed and cannot be associated", employeeModifyRequest.deptId());
                }
                employee.setDepartment(department);
            }
            if (employeeModifyRequest.projectIds() != null && !employeeModifyRequest.projectIds().isEmpty()) {
                addProjects(employeeModifyRequest.projectIds(), employee);
            }
            Employee employeeUpdated = employeeRepository.save(employee);
            if (employeeUpdated != null) {
                return Optional.of(translateToResponse(employee));
            }
        }
        else {
            throw new ResourceNotFoundException("EMP_ID_NOT_FOUND", "EmpId dosent exist", employeeModifyRequest.empId());
        }
        return Optional.empty();
    }

    private void addProjects(List<Integer> projectIds, Employee employee) {
        List<Integer> existingProjects = employee.getProjects().stream().map(x -> x.getProjectId()).collect(Collectors.toList());
        projectIds
                .forEach(x -> {
                    if (!existingProjects.contains(x))
                    employee.getProjects().add(projectService.getProjectById(x)
                            .orElseThrow(() -> new ResourceNotFoundException(PROJECT_ID_NOT_FOUND, "Project Not found - ", x)));
                });
        employeeRepository.save(employee);
    }

    private void removeProjects(List<Integer> projectIds, Employee employee) {
        projectIds
                .forEach(x -> {
                        employee.getProjects().remove(projectService.getProjectById(x)
                                .orElseThrow(() -> new ResourceNotFoundException(PROJECT_ID_NOT_FOUND, "Project Not found - ", x)));
                });
        employeeRepository.save(employee);
    }

    private EmployeeResponse translateToResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getFamilyName(),
                employee.getEmail(),
                employee.getDepartment() != null ? employee.getDepartment().getDepartmentId() : -1,
                employee.getProjects() != null ?
                        (employee.getProjects().stream().map( x -> BussinessUtil.translateToProjectResponse(x)).collect(Collectors.toList())):
                        null);
    }

    public List<EmployeeResponse> listActive() {
        List<Employee> employeeList = employeeRepository.findAllActiveEmployee();
        List<EmployeeResponse> responseList = null;
        if (employeeList.size() > 0) {
            responseList = new ArrayList<>();
            for (Employee employee : employeeList) {
                responseList.add(translateToResponse(employee));
            }
        }
        return responseList;
    }

    public List<EmployeeResponse> listInActive() {
        List<Employee> employeeList = employeeRepository.findAllInActiveEmployee();
        List<EmployeeResponse> responseList = null;
        if (employeeList.size() > 0) {
            responseList = new ArrayList<>();
            for (Employee employee : employeeList) {
                responseList.add(translateToResponse(employee));
            }
        }
        return responseList;
    }

    public List<EmployeeResponse> list() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeResponse> responseList = null;
        if (employeeList.size() > 0) {
            responseList = new ArrayList<>();
            for (Employee employee : employeeList) {
                responseList.add(translateToResponse(employee));
            }
        }
        return responseList;
    }

    public EmployeeResponse addProject(EmployeeModifyRequest employeeModifyRequest) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeModifyRequest.empId());
        if (employeeOptional.isPresent()) {
            if (employeeModifyRequest.projectIds() != null && !employeeModifyRequest.projectIds().isEmpty()) {
                addProjects(employeeModifyRequest.projectIds(), employeeOptional.get());
                return translateToResponse(employeeRepository.findById(employeeModifyRequest.empId()).get());
            }
            else {
                throw new IllegalArgumentException("No ProjectIds provided");
            }
        }
        else {
            throw new ResourceNotFoundException("EMP_ID_NOT_FOUND", "EmpId dosent exist", employeeModifyRequest.empId());
        }
    }
    public EmployeeResponse removeProject(EmployeeModifyRequest employeeModifyRequest) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeModifyRequest.empId());
        if (employeeOptional.isPresent()) {
            if (employeeModifyRequest.projectIds() != null && !employeeModifyRequest.projectIds().isEmpty()) {
                removeProjects(employeeModifyRequest.projectIds(), employeeOptional.get());
                return translateToResponse(employeeRepository.findById(employeeModifyRequest.empId()).get());
            }
            else {
                throw new IllegalArgumentException("No ProjectIds provided");
            }
        }
        else {
            throw new ResourceNotFoundException("EMP_ID_NOT_FOUND", "EmpId dosent exist", employeeModifyRequest.empId());
        }
    }
}
