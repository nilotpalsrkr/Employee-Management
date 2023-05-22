/***
 * This class represents the DepartmentService.
 * This is a service layer for Department Service.
 * This has all the methods that the controller would
 * need to interact to the DB layer via this service layer.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.service;

import fi.eke.exercise.employeeproject.exception.ResourceCannotBeRemovedException;
import fi.eke.exercise.employeeproject.exception.ResourceNotFoundException;
import fi.eke.exercise.employeeproject.models.Department;
import fi.eke.exercise.employeeproject.models.Employee;
import fi.eke.exercise.employeeproject.models.rest.*;
import fi.eke.exercise.employeeproject.repository.DepartmentRepository;
import fi.eke.exercise.employeeproject.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static fi.eke.exercise.employeeproject.util.BussinessUtil.translateToDepartmentResponse;
import static fi.eke.exercise.employeeproject.util.Constants.*;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Optional<DepartmentResponse> add(DepartmentCreateRequest departmentCreateRequest) {
        if (departmentCreateRequest == null) {
            throw new ResourceNotFoundException("DEPARTMENT_DETAILS_NOT_PROVIDED", "Department details to be provided", "");
        }
        else  {
            Department e = departmentRepository.save(Department.builder().city(departmentCreateRequest.city())
                    .name(departmentCreateRequest.name())
                    .status("active")
                    .build());
            if (e != null) {
                return Optional.of(translateToDepartmentResponse(e));
            }
            else return Optional.empty();
        }
    }
    public boolean remove(IdRequest idRequest) throws IllegalArgumentException{
        if (idRequest == null) {
            throw new ResourceNotFoundException(DEPT_ID_NOT_PROVIDED, "DeptId is to be provided", idRequest.id());
        }
        else  {
            Optional<Department> e = departmentRepository.findActiveDepartmentById(idRequest.id());
            if (e.isPresent()) {
                Department department = e.get();
                if (department.getStatus() != null && department.getStatus().equals("active")) {
                    if (!department.hasEmployees()) {
                        department.setStatus("removed");
                        departmentRepository.save(e.get());
                        return true;
                    }
                    else {
                        throw new ResourceCannotBeRemovedException("DEPARTMENT_CANNOT_BE_DELETED_WITH_EMPLOYEES", "Only Departments with no employees can be marked removed. Associated employees : "+ department.getEmployees().stream().map(x -> x.getEmployeeId()).collect(Collectors.toList()));
                    }

                }
                else if (department.getStatus().equals("removed")) {
                    throw new ResourceNotFoundException(DEPT_ALREADY_REMOVED, "The department already seems to be removed", idRequest.id());
                }
            }
            else throw new ResourceNotFoundException(DEPT_ID_NOT_FOUND, "DeptId dosent exist", idRequest.id());
        }
        return false;
    }

    public Optional<DepartmentResponse> modify(DepartmentModifyRequest departmentModifyRequest) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentModifyRequest.deptId());

        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();

            if (departmentModifyRequest.name() != null && !departmentModifyRequest.name().isEmpty()) {
                department.setName(departmentModifyRequest.name());
            }
            if (departmentModifyRequest.city() != null && !departmentModifyRequest.city().isEmpty()) {
                department.setCity(departmentModifyRequest.city());
            }


            Department departmentModified = departmentRepository.save(department);
            if (departmentModified != null) {
                return Optional.of(translateToDepartmentResponse(department));
            }
        }
        else {
            throw new ResourceNotFoundException("DEPT_ID_NOT_FOUND", "DeptId dosent exist", departmentModifyRequest.deptId());
        }
        return Optional.empty();
    }

    public List<DepartmentResponse> listActive() {
        List<Department> departmentList = departmentRepository.findAllActiveDepartment();
        List<DepartmentResponse> responseList = null;
        if (departmentList.size() > 0) {
            responseList = new ArrayList<>();
            for (Department department : departmentList) {
                responseList.add(translateToDepartmentResponse(department));
            }
        }
        return responseList;
    }

    public List<DepartmentResponse> listInActive() {
        List<Department> inActiveDepartments = departmentRepository.findAllInActiveDepartments();
        List<DepartmentResponse> responseList = null;
        if (inActiveDepartments.size() > 0) {
            responseList = new ArrayList<>();
            for (Department department : inActiveDepartments) {
                responseList.add(translateToDepartmentResponse(department));
            }
        }
        return responseList;
    }

    public List<DepartmentResponse> list() {
        List<Department> departmentList = departmentRepository.findAll();
        List<DepartmentResponse> responseList = null;
        if (departmentList.size() > 0) {
            responseList = new ArrayList<>();
            for (Department department : departmentList) {
                responseList.add(translateToDepartmentResponse(department));
            }
        }
        return responseList;
    }

    public Optional<Department> getDepartmentById(Integer deptId) {
        return departmentRepository.findById(deptId);
    }
}
