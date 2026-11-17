package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) { this.employeeRepository = employeeRepository; this.departmentRepository = departmentRepository; }
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    private EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setSalary(employee.getSalary());
        if (employee.getDepartment() != null) dto.setDepartmentId(employee.getDepartment().getId());
        return dto;
    }

    private Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        return employee;
    }

    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        Employee employee = toEntity(dto);
        if (dto.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + dto.getDepartmentId()));
            employee.setDepartment(dept);
        }
        return toDTO(employeeRepository.save(employee));
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeById(Long id) {
        return toDTO(employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id)));
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee existing = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setSalary(dto.getSalary());
        if (dto.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + dto.getDepartmentId()));
            existing.setDepartment(dept);
        } else {
            existing.setDepartment(null);
        }
        return toDTO(employeeRepository.save(existing));
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        employeeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesByDepartment(Long departmentId) {
        if (!departmentRepository.existsById(departmentId))
            throw new ResourceNotFoundException("Department not found with id: " + departmentId);
        return employeeRepository.findByDepartmentId(departmentId).stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesWithSalaryGreaterThan(Double salary) {
        return employeeRepository.findBySalaryGreaterThan(salary).stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> searchEmployeesByName(String keyword) {
        return employeeRepository.findByNameContainingIgnoreCase(keyword).stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesBySalaryRange(Double min, Double max) {
        return employeeRepository.findBySalaryBetween(min, max).stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public Page<EmployeeDTO> getEmployeesPaginated(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return employeeRepository.findAll(PageRequest.of(page, size, sort)).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesByDepartmentSortedBySalary(Long departmentId) {
        if (!departmentRepository.existsById(departmentId))
            throw new ResourceNotFoundException("Department not found with id: " + departmentId);
        return employeeRepository.findEmployeesByDepartmentSortedBySalary(departmentId).stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public Double getAverageSalaryByDepartment(Long departmentId) {
        if (!departmentRepository.existsById(departmentId))
            throw new ResourceNotFoundException("Department not found with id: " + departmentId);
        return employeeRepository.findAverageSalaryByDepartmentId(departmentId);
    }
}
