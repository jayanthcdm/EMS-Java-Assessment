package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.*;
import com.example.employeemanagement.entity.*;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentService {
    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) { this.departmentRepository = departmentRepository; this.employeeRepository = employeeRepository; }
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    private DepartmentDTO toDTO(Department dept) {
        return new DepartmentDTO(dept.getId(), dept.getName());
    }

    private EmployeeDTO toEmployeeDTO(Employee e) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setEmail(e.getEmail());
        dto.setSalary(e.getSalary());
        if (e.getDepartment() != null) dto.setDepartmentId(e.getDepartment().getId());
        return dto;
    }

    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        Department dept = new Department();
        dept.setName(dto.getName());
        return toDTO(departmentRepository.save(dept));
    }

    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public DepartmentWithEmployeesDTO getDepartmentWithEmployees(Long id) {
        Department dept = departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        List<EmployeeDTO> employees = dept.getEmployees().stream().map(this::toEmployeeDTO).toList();
        return new DepartmentWithEmployeesDTO(dept.getId(), dept.getName(), employees);
    }

    public EmployeeDTO assignEmployeeToDepartment(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        employee.setDepartment(department);
        return toEmployeeDTO(employeeRepository.save(employee));
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesOfDepartment(Long departmentId) {
        if (!departmentRepository.existsById(departmentId))
            throw new ResourceNotFoundException("Department not found with id: " + departmentId);
        return employeeRepository.findByDepartmentId(departmentId).stream().map(this::toEmployeeDTO).toList();
    }
}
