package com.example.employeemanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Employee name cannot be blank")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    @Column(nullable = false, unique = true)
    private String email;
    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
    @Column(nullable = false)
    private Double salary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee() {}
    public Employee(Long id, String name, String email, Double salary, Department department) {
        this.id = id; this.name = name; this.email = email; this.salary = salary; this.department = department;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}
