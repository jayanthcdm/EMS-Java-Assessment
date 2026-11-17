package com.example.employeemanagement.dto;

import jakarta.validation.constraints.*;

public class EmployeeDTO {
    private Long id;
    @NotBlank(message = "Employee name cannot be blank")
    private String name;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;
    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
    private Double salary;
    private Long departmentId;

    public EmployeeDTO() {}
    public EmployeeDTO(Long id, String name, String email, Double salary, Long departmentId) {
        this.id = id; this.name = name; this.email = email; this.salary = salary; this.departmentId = departmentId;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
}
