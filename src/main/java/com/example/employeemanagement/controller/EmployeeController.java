package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    public EmployeeController(EmployeeService employeeService) { this.employeeService = employeeService; }
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO dto) {
        return new ResponseEntity<>(employeeService.createEmployee(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/department/{deptId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@PathVariable Long deptId) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(deptId));
    }

    @GetMapping("/salary/greater-than")
    public ResponseEntity<List<EmployeeDTO>> getBySalaryGreaterThan(@RequestParam Double amount) {
        return ResponseEntity.ok(employeeService.getEmployeesWithSalaryGreaterThan(amount));
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDTO>> searchByName(@RequestParam String keyword) {
        return ResponseEntity.ok(employeeService.searchEmployeesByName(keyword));
    }

    @GetMapping("/salary/between")
    public ResponseEntity<List<EmployeeDTO>> getBySalaryRange(@RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(employeeService.getEmployeesBySalaryRange(min, max));
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<EmployeeDTO>> getPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(employeeService.getEmployeesPaginated(page, size, sortBy, direction));
    }

    @GetMapping("/department/{deptId}/sorted-by-salary")
    public ResponseEntity<List<EmployeeDTO>> getByDeptSortedBySalary(@PathVariable Long deptId) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartmentSortedBySalary(deptId));
    }

    @GetMapping("/department/{deptId}/average-salary")
    public ResponseEntity<Map<String, Double>> getAverageSalary(@PathVariable Long deptId) {
        Double avg = employeeService.getAverageSalaryByDepartment(deptId);
        return ResponseEntity.ok(Map.of("averageSalary", avg == null ? 0.0 : avg));
    }
}
