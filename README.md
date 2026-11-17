# Employee & Department Management API

Spring Boot 3.2.5, Java 21, Spring Data JPA, MySQL and Maven.

## Run

1. Install Java 21, Maven and MySQL.
2. Create a MySQL user/database or update `src/main/resources/application.properties`.
   The configured URL creates `employee_db` automatically when the MySQL user has permission.
3. Start MySQL.
4. From this folder run:

```bash
mvn spring-boot:run
```

API base URL: `http://localhost:8080`

## Quick start

Create a department first:

```http
POST /api/departments
Content-Type: application/json

{"name":"Engineering"}
```

Create an employee and assign it to department ID 1:

```http
POST /api/employees
Content-Type: application/json

{"name":"John Doe","email":"john.doe@example.com","salary":75000,"departmentId":1}
```

## Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/departments` | Create department |
| GET | `/api/departments` | List departments |
| GET | `/api/departments/{id}` | Department with employees |
| GET | `/api/departments/{id}/employees` | List department employees |
| PUT | `/api/departments/{deptId}/assign-employee/{employeeId}` | Assign employee |
| POST | `/api/employees` | Create employee |
| GET | `/api/employees` | List employees |
| GET | `/api/employees/{id}` | Get employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |
| GET | `/api/employees/department/{deptId}` | Employees by department |
| GET | `/api/employees/salary/greater-than?amount=50000` | Salary greater than |
| GET | `/api/employees/search?keyword=john` | Search by name |
| GET | `/api/employees/salary/between?min=30000&max=80000` | Salary range |
| GET | `/api/employees/paginated?page=0&size=5&sortBy=name&direction=asc` | Pagination and sorting |
| GET | `/api/employees/department/{deptId}/sorted-by-salary` | Department employees by salary |
| GET | `/api/employees/department/{deptId}/average-salary` | Department average salary |

## Notes

- Validation errors return HTTP 400.
- Missing resources return HTTP 404.
- Successful create operations return HTTP 201; deletes return HTTP 204.
- `departmentId` is optional when creating an employee. Use the assignment endpoint later if needed.
- Configure your database credentials before running. Do not use the sample `root/root` credentials in production.

## If Maven reports missing Lombok getters/setters

This project pins Lombok 1.18.36 and configures it as an explicit Maven annotation processor.
After extracting the updated ZIP, run:

```bash
mvn clean compile
mvn spring-boot:run
```

If your IDE still shows Lombok errors while Maven compiles successfully, enable annotation processing in the IDE and install/enable its Lombok support.


## Compiler compatibility note

This version intentionally does not use Lombok. Getters, setters, constructors, and dependency-injection constructors are written explicitly, avoiding Lombok/JDK annotation-processor compatibility errors such as `TypeTag :: UNKNOWN`.
