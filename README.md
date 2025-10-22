# Todo List API

This is a simple Todo List API built with Spring Boot that allows users to manage their to-do lists and tasks. It uses JWT for authentication and authorization.

## Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- PostgreSQL
- JWT (JSON Web Tokens)
- Maven
- SpringDoc (for API documentation)

## How to Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Malak-Essam/todo-backend.git
   ```
2. **Navigate to the project directory:**
   ```bash
   cd todo-backend
   ```
3. **Set up the database:**
   - Make sure you have PostgreSQL installed and running.
   - Create a new database for the project.
   - Update the `src/main/resources/application.properties` file with your database credentials:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/your-database-name
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     ```
4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
The application will be running at `http://localhost:8080`.

## API Endpoints

The API documentation is available at `http://localhost:8080/swagger-ui.html`.

### Authentication

- `POST /api/auth/register`: Register a new user.
- `POST /api/auth/login`: Log in and get a JWT token.

### Users

- `GET /api/users`: Get all users.
- `GET /api/users/{userId}`: Get a user by ID.
- `POST /api/users`: Create a new user.
- `PUT /api/users/{id}`: Update a user.
- `DELETE /api/users/{id}`: Delete a user.

### Todo Lists

- `GET /api/lists`: Get all to-do lists for the authenticated user.
- `GET /api/lists/{listId}`: Get a to-do list by ID.
- `POST /api/lists`: Create a new to-do list.
- `PUT /api/lists/{listId}`: Update a to-do list.
- `DELETE /api/lists/{listId}`: Delete a to-do list.

### Tasks

- `GET /api/tasks`: Get all tasks for the authenticated user.
- `GET /api/tasks/list/{listId}`: Get all tasks in a specific to-do list.
- `GET /api/tasks/{taskId}`: Get a task by ID.
- `POST /api/tasks?listId={listId}`: Create a new task in a specific to-do list.
- `PUT /api/tasks/{taskId}`: Update a task.
- `DELETE /api/tasks/{taskId}`: Delete a task.
