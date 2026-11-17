package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.*;
import com.example.employeemanagement.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    public DepartmentController(DepartmentService departmentService) { this.departmentService = departmentService; }
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO dto) {
        return new ResponseEntity<>(departmentService.createDepartment(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentWithEmployeesDTO> getDepartmentWithEmployees(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentWithEmployees(id));
    }

    @PutMapping("/{deptId}/assign-employee/{employeeId}")
    public ResponseEntity<EmployeeDTO> assignEmployee(@PathVariable Long deptId, @PathVariable Long employeeId) {
        return ResponseEntity.ok(departmentService.assignEmployeeToDepartment(employeeId, deptId));
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesOfDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getEmployeesOfDepartment(id));
    }
}
