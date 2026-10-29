# Task API – Spring Boot

## Project Overview
Task API is a secure REST API developed using Spring Boot. It provides user authentication, role-based authorization, and CRUD operations for task management.

## Features
- RESTful API development using Spring Boot
- JWT-based authentication
- Role-based authorization
- CRUD operations for tasks
- PostgreSQL database integration
- Input validation and error handling
- Unit testing using JUnit and Mockito

## Technologies Used
- Java
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit and Mockito

## Prerequisites
- Java JDK
- Maven
- PostgreSQL
- Git

## Setup and Installation

1. Clone the repository:

   ```bash
   git clone YOUR_GITHUB_REPOSITORY_URL
   ```

2. Navigate to the project folder:

   ```bash
   cd task-api
   ```

3. Configure your PostgreSQL database and application settings.

4. Run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows, use:

   ```bash
   mvnw.cmd spring-boot:run
   ```

## API Endpoints
The API supports authentication and task management operations. Refer to the source code for the exact endpoint URLs and required request formats.

## Security
- JWT authentication for protected endpoints
- Role-based access control
- Input validation and error handling
- Keep database passwords and JWT secrets out of version control.

## Testing

Run the tests using:

```bash
mvn test
```
