# Task Management System

## Overview
A standalone Spring Boot application that automatically assigns tasks to employees based on required skills, current workload, available capacity, and task priority.

## Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- JUnit 5 and Mockito
- Postman for API testing

## Features
- Create, retrieve, and update tasks.
- Manage employee skills and task capacity.
- Automatically assign tasks to eligible employees.
- Select employees based on the lowest active workload.
- Keep tasks in a waiting queue when no eligible employee has available capacity.
- Automatically assign waiting tasks when an employee completes a task.
- Prioritize waiting tasks by priority: HIGH, MEDIUM, LOW.
- Handle missing tasks and invalid assignment scenarios.

## Prerequisites
- JDK 21
- MySQL
- Maven (or the included Maven Wrapper)

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone <https://github.com/Neha-Mukherjee/Task-management.git>
   cd task-management
   ```

2. Create a MySQL database.

3. Configure your database connection in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/task_management
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   ```

4. Run the application:
   ```bash
   .\mvnw.cmd spring-boot:run
   ```

The application runs on port `8080` by default, unless configured otherwise.

## API Overview

Base URL: `http://localhost:8080/api/task-management/tasks`

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/` | Create a task |
| GET | `/` | Retrieve tasks |
| GET | `/{id}` | Retrieve a task by ID |
| PUT | `/{id}?status=COMPLETED` | Update task status |
| PUT | `/{id}/assign` | Assign a task manually |

## Task Assignment Logic

1. Find employees with the required skill.
2. Check each employee's active workload and capacity.
3. Assign the task to an eligible employee with the lowest workload.
4. Keep the task in `WAITING` status if eligible employees have no available capacity.
5. When a task is completed, attempt to assign the highest-priority eligible waiting task.

## Testing

Run all tests using the Maven Wrapper:

```bash
.\mvnw.cmd test
```

Run service tests only:

```bash
.\mvnw.cmd test -Dtest=TasksServiceImplTest
```

## Future Improvements
- Improve handling of simultaneous task assignments.
- Add authentication and authorization.
- Add API documentation using Swagger/OpenAPI.
- Improve validation and error responses.
