## **📌 PayrollServices Microservice - Project Outline**  

#### **📂 Project Structure**  
The project follows a standard **Spring Boot layered architecture** to ensure modularity, maintainability, and scalability.  

```
PayrollServices/  
│── src/main/java/com/payroll/payrollservices/  
│   ├── controller/       # Handles API requests and responses  
│   ├── service/          # Contains business logic  
│   ├── repository/       # Handles database interactions using JPA  
│   ├── dto/              # Data Transfer Objects for request/response models  
│   ├── entity/           # Defines database entities  
│   ├── util/             # Utility classes and constants  
│   ├── exception/        # Centralized exception handling if needed
│   ├── PayrollServicesApplication.java  # Main entry point, run this to start the application  
│  
│── src/main/resources/  
│   ├── application.properties  # Configuration settings  
│  
│── pom.xml  # Maven dependencies  
│── README.md  # Project documentation  
│── .gitignore  # Git ignore rules  
```
![image](https://github.com/user-attachments/assets/503a047a-3f3d-4583-b859-1c3e1a586aa1)

---

## 🏁 Steps to Run Payroll Services Microservice

To run the **Payroll Services Microservice**, follow the steps below:

### 1️⃣ **Prerequisites**

Before running the application, ensure that the following software is installed on your machine:

- **Java 17** or later: Since the project uses Java 17, make sure it's installed and set up.
  - Download Java from [Oracle's official website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or use [OpenJDK](https://adoptium.net/).
  - Confirm the Java installation with the command:  
    ```bash
    java -version
    ```

- **Maven**: The project uses Maven for dependency management and building the application.
  - Install Maven from [here](https://maven.apache.org/install.html) if not already installed.
  - Verify Maven installation with the command:  
    ```bash
    mvn -version
    ```

### 2️⃣ **Clone the Repository**

If you haven't already cloned the repository, use the following Git command to get the project:

```bash
git clone https://github.com/HBrahmbhatt/payroll-services.git
```

### 3️⃣ **Import the Project into Your IDE (Optional)**

If you are using an IDE, you can import the project as a Maven project.

- **For IntelliJ IDEA**:
  1. Open IntelliJ IDEA.
  2. Select **File > Open**, and navigate to the folder where the repository was cloned.
  3. Select the **`pom.xml`** file and click **OK** to import the project.

- **For Eclipse**:
  1. Open Eclipse.
  2. Go to **File > Import > Existing Maven Projects**.
  3. Select the project folder and click **Finish**.

### 4️⃣ **Build the Project**

If you are using a terminal or command prompt, navigate to the root directory of the project where the **`pom.xml`** file is located and run the following Maven command to build the project and download dependencies:

```bash
mvn clean install
```

This will download all required dependencies and build the application.

### 5️⃣ **Run the Application**

To run the **Payroll Services** microservice, execute the following command from the root directory of the project:

```bash
mvn spring-boot:run
```

Alternatively, you can run the `PayrollServicesApplication` class from your IDE as a **Spring Boot Application**.

### 6️⃣ **Access the API**

Once the application is running, you can access the API endpoints through `localhost` (default port is **8080**).

- **Base URL**:  
  `http://localhost:8080`

- Example endpoints:
  - **Get all employees**:  
    `GET http://localhost:8080/api/employees`
  - **Get employee by ID**:  
    `GET http://localhost:8080/api/employees/{id}`
  - **Add a new employee**:  
    `POST http://localhost:8080/api/employees`
  - **Update employee**:  
    `PUT http://localhost:8080/api/employees`
  - **Delete employee**:  
    `DELETE http://localhost:8080/api/employees/{id}`


### Troubleshooting:

- **Database**: The project uses **H2 Database** (in-memory database) by default. If you want to use another database (e.g., MySQL, PostgreSQL), you'll need to modify the **`application.properties`** file to provide the appropriate database configuration.

- **Port conflicts**: If the default port (8080) is already being used by another application, you can change the port by adding the following property in **`application.properties`**:
  
  ```properties
  server.port=8081
  ```


Now you're all set to run and interact with the **Payroll Services Microservice**! 🎉

---

## [📌 1. Controller Layer](https://github.com/HBrahmbhatt/payroll-services/tree/main/src/main/java/com/payroll/payrollservices/controller)

The **Controller Layer** in the `controller/` package is responsible for handling HTTP requests, processing input, and returning appropriate responses. It interacts with the **Service Layer**, ensuring clean separation between business logic and API endpoints.
The **PayrollController** ensures that only **valid employees** are processed by enforcing input validation using **Jakarta Validation (`@Valid`)**. This prevents incomplete or incorrect data from being passed to the service layer, maintaining data integrity.
Additionally, the controller **does not directly expose entity objects** in API responses. Instead, it uses **Data Transfer Objects (DTOs)** to ensure a clean separation between internal database structures and external API contracts. DTOs help in **data transformation, validation, and security**, preventing unnecessary exposure of sensitive fields.


### **Key Responsibilities:** 
✔️ Exposes RESTful endpoints for CRUD operations on Employee records  
✔️ Handles API requests, validates input, and ensures only **valid employees** are processed  
✔️ Uses **DTOs** to separate internal entity models from API responses, improving security and maintainability  
✔️ Forwards requests to the **Service Layer** while implementing error handling and logging for better debugging  

### **Endpoints in `PayrollController.java`**  
| HTTP Method | Endpoint | Functionality |
|------------|----------|--------------|
| `GET` | `/api/employees/test` | Health check endpoint |
| `GET` | `/api/employees/all-employees` | Fetches all employees |
| `GET` | `/api/employees/{id}` | Retrieves employee details by ID |
| `POST` | `/api/employees/add-employee` | Adds a new employee after checking for validations|
| `PUT` | `/api/employees/update-employee` | Updates an existing employee after checking for validations |
| `DELETE` | `/api/employees/delete/{id}` | Deletes an employee by ID |

### **Implementation Details:**  
- **`@RestController`**: Defines the class as a REST controller  
- **`@RequestMapping("/api/employees")`**: Base path for all endpoints  
- **`@Autowired PayrollServicesImpl payrollServices;`**: Injects the service layer dependency  
- **`ResponseEntity<ResponseStatus>`**: Ensures standardized API responses

---

## [📌 2. Service Layer](https://github.com/HBrahmbhatt/payroll-services/tree/main/src/main/java/com/payroll/payrollservices/services)

The **Service Layer** in the `PayrollServices` microservice is responsible for implementing the business logic required to manage employee data. It acts as an intermediary between the **Controller Layer** and the **Data Access Layer** (Repository), ensuring data integrity and business rule enforcement.  

This layer is divided into two key components:  

1. **Service Interface (`PayrollServices`)**    
   - Ensures abstraction between the controller and implementation layers.

2. **Service Implementation (`PayrollServicesImpl`)**  
   - Implements business logic and data transformations.  
   - Interacts with the **Repository Layer** for database operations.  
   - Uses **DTOs (Data Transfer Objects)** to ensure API response security and separation of concerns.  
   - Logs important events and handles exceptions gracefully.  

### **Key Responsibilities:**  
✔️ Implements CRUD operations on employee records.  
✔️ Ensures **only valid employees** are processed by validating input data.  
✔️ Uses **DTOs** to convert between entity models and API responses.  
✔️ Logs important events for debugging and monitoring.  
✔️ Handles errors and prevents technical failures from affecting the API response.  

---

## [📌 3. Repository Layer](https://github.com/HBrahmbhatt/payroll-services/tree/main/src/main/java/com/payroll/payrollservices/dao)

The **Repository Layer** in the `PayrollServices` microservice is responsible for direct interaction with the database. It abstracts away the complexities of data persistence, providing a simple and clean interface for data operations. This layer leverages **Spring Data JPA** to perform CRUD operations efficiently.  

### **Key Responsibilities:**  
✔️ Provides database access methods for employee records.  
✔️ Uses **Spring Data JPA** to simplify query execution.  
✔️ Enables seamless interaction between the **Service Layer** and the database.  
✔️ Supports custom queries when required (e.g., fetching employees based on specific criteria).  

### **Key Components:**  

#### 1️⃣ **Employee Repository (`PayrollRepository`)**  
This interface extends `JpaRepository`, providing built-in CRUD operations without requiring explicit implementation. It acts as a bridge between the database and the service layer, ensuring that employee data is easily retrievable and modifiable.  

### **How It Works**  

- The repository layer interacts directly with the **Employee Entity**, allowing the service layer to perform operations like retrieving all employees, finding an employee by ID, saving new employee records, updating existing ones, and deleting employees.  
- Since it extends **JpaRepository**, it comes with pre-implemented methods for efficient data access.  
- Additional custom queries can be added to fetch employees based on specific business requirements, such as retrieving employees by department, salary range, or joining date.  

The **Repository Layer** ensures smooth data handling, making it easier for the **Service Layer** to focus on business logic without worrying about database interactions.

---

## [📌 4. Entity ](https://github.com/HBrahmbhatt/payroll-services/tree/main/src/main/java/com/payroll/payrollservices/entity)

The **Entity Layer** in the `PayrollServices` microservice represents the **data model** and defines how employee records are stored in the database. It is responsible for mapping Java objects to relational database tables using **JPA (Java Persistence API)**.  

### **Key Responsibilities:**  
✔️ Defines the **Employee** entity and its attributes.  
✔️ Maps class fields to database columns using **JPA annotations**.  
✔️ Implements **validation constraints** to ensure data integrity.  
✔️ Serves as the foundation for database interactions in the **Repository Layer**.  

### **Key Components:**  

#### 1️⃣ **Employee Entity (`Employee.java`)**  
The `Employee` class is the central entity in the payroll system. It represents an employee record in the database, ensuring proper storage and retrieval of employee-related data.  

### **How It Works**  

- The class is annotated with `@Entity`, indicating that it is a JPA entity mapped to a database table.  
- It contains **fields like `firstName`, `lastName`, `dob`, `gender`, `email`, `phone`, `address`, `role`, and `joiningDate`**, each representing a column in the database.  
- The primary key is `employeeId`, which is auto-generated using `@GeneratedValue`.  
- The class enforces **validation constraints** using annotations like `@NotNull`, `@Size`, `@Pattern`, and `@Past`, ensuring only valid employee records are processed.  
- The **Lombok library** (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@ToString`) reduces boilerplate code by automatically generating getter/setter methods and constructors.  

This **Entity Layer** ensures that all employee data adheres to business rules and is stored in a structured format, making it easy for the **Repository Layer** to access and manipulate records. 🚀

---

## 📌 5. Dependencies

The **`payroll-services`** microservice leverages a variety of dependencies to handle data persistence, web requests, validation, logging, and more. These dependencies, defined in the **[`pom.xml`](https://github.com/HBrahmbhatt/payroll-services/blob/main/pom.xml)** file, include:

### Key Dependencies:
✔️ **Spring Boot Starter Web** - For building RESTful web services and handling HTTP requests.  
✔️ **Spring Boot Starter Data JPA** - For seamless integration with databases using Spring Data JPA.  
✔️ **H2 Database** - In-memory database used for runtime development and testing.  
✔️ **Spring Boot Starter Validation** - For enforcing validation constraints on request data.  
✔️ **SLF4J** - For logging and debugging with SLF4J API.  
✔️ **Gson** - For JSON handling.  
✔️ **Lombok** - For reducing boilerplate code (e.g., getters, setters).  
✔️ **Springdoc OpenAPI** - For generating and displaying API documentation in Swagger UI.  
✔️ **Spring Boot DevTools** - For development-time features like automatic restart and live reload.

These dependencies enable the microservice to function efficiently while adhering to best practices in Spring Boot development.
