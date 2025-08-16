# LEARNING MANAGEMENT SYSTEM
*This Spring Boot project is a Learning Management System REST API built using Java, Spring Boot, JPA, Hibernate, and MySQL. 
*The purpose of the project is to demonstrate how CRUD (Create, Read, Update, Delete) operations can be performed on student data through RESTful endpoints.
*The project is structured into different layers, each with its own responsibility. 
*The controller layer (e.g., StudentController) handles incoming HTTP requests and maps them to specific endpoints such as GET /api/students for listing all students, POST /api/students for creating a new student,   PUT /api/students/{id} for updating a student, and DELETE /api/students/{id} for removing a student. 
*The service layer (e.g., StudentService) acts as the business logic layer; it defines how the operations should be processed, validates data, and ensures rules are applied before interacting with the database.
*The repository layer (e.g., StudentRepository) is an interface that extends Spring Data JPA, allowing database interaction with minimal boilerplate code. 
*The entity layer (e.g., Student) represents the database table structure, where fields in the class map to columns in the database.

When you run this project, Spring Boot automatically configures the application using the dependencies defined in the pom.xml file. The database connection is managed through the application.properties file, where you set the MySQL URL, username, password, and JPA configurations. On startup, the application connects to the database and creates the necessary tables if they do not already exist. Once running, you can test the endpoints using Postman, or a browser for GET requests. For example, you can fetch all students by sending a GET request to http://localhost:8080/api/students, create a new student with a POST request containing JSON data, update an existing student with PUT, or delete with DELETE.
