package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);
    List<Employee> findBySalaryGreaterThan(Double salary);
    List<Employee> findByNameContainingIgnoreCase(String keyword);
    List<Employee> findBySalaryBetween(Double minSalary, Double maxSalary);

    @Query("SELECT e FROM Employee e WHERE e.department.id = :deptId ORDER BY e.salary DESC")
    List<Employee> findEmployeesByDepartmentSortedBySalary(@Param("deptId") Long deptId);

    @Query(value = "SELECT AVG(salary) FROM employees WHERE department_id = :deptId", nativeQuery = true)
    Double findAverageSalaryByDepartmentId(@Param("deptId") Long deptId);
}
