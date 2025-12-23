# Loqio Microservices Architecture

This document explains the microservice architecture of the Loqio language learning platform. We've evolved from a monolithic application to a microservices-based system, breaking down functionality into independent, deployable services. Each service has clear boundaries, responsibilities, and data ownership.

## Microservice Architecture Overview

Loqio now consists of multiple independent services that communicate with each other. This architecture provides:
- **Scalability**: Each service can be scaled independently
- **Technology Flexibility**: Services can use different technologies
- **Fault Isolation**: Issues in one service don't affect others
- **Independent Deployment**: Services can be updated without affecting the entire system

## Current Microservices

### 1. Hello World Service
**Purpose**: Demonstration microservice for learning microservice concepts

**Boundaries & Responsibilities**:
- Provides a simple REST endpoint
- Returns static "Hello, World!" response
- Serves as a template for new microservices

**Technical Details**:
- **Port**: 8081
- **Database**: None (stateless service)
- **Endpoints**: `GET /` → "Hello, World!"
- **Package**: `com.gabrielrochon.helloworld`

**Configuration**:
```
server.port=8081
spring.application.name=hello-world-service
```

### 2. Language Content Service
**Purpose**: Manages language learning content and vocabulary data

**Boundaries & Responsibilities**:
- Stores and retrieves vocabulary items
- Manages language content (Tagalog-English translations)
- Provides CRUD operations for vocabulary data
- Owns the vocabulary domain logic

**Technical Details**:
- **Port**: 8082
- **Database**: PostgreSQL (vocabulary table)
- **Endpoints**:
  - `GET /` → "Hello, World!" (placeholder)
  - `GET /items` → List all vocabulary items
- **Package**: `com.gabrielrochon.languagecontent`

**Database Configuration**:
```
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:postgres}?sslmode=require
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:password}
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Data Ownership & Boundaries

### Database Ownership
- **Language Content Service**: Owns PostgreSQL `vocabulary` table
- **Future Services**: Each service will own its own database schema

### Service Boundaries
- **No Shared Databases**: Each service manages its own data
- **Independent Scaling**: Services can be deployed on different infrastructure
- **API Communication**: Services communicate via REST APIs (future implementation)

## Service Communication & Future Architecture

As we add more microservices (User Progress, Authentication, Analytics), they will communicate through:
- **REST APIs**: Synchronous communication between services
- **Message Queues**: Asynchronous event-driven communication (future Kafka implementation)
- **API Gateway**: Single entry point for external clients (future implementation)

## Language Content Service Components

### Database Connection Setup

The Language Content Service connects to Azure PostgreSQL using these configuration settings in `language-content-service/src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:postgres}?sslmode=require
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:password}
```

- **JDBC URL**: Tells the service how to connect to its database
- **SSL Mode**: Ensures secure connection to Azure (required)
- **Environment Variables**: Database credentials are stored as environment variables for security

### The Three Key Components

#### 1. Item.java - The Data Model

```java
/**
 * JPA entity representing a vocabulary item in the PostgreSQL database.
 * This class maps to the "vocabulary" table and contains translations
 * between Tagalog and English words.
 */
@Entity
@Table(name = "vocabulary")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;           // Unique identifier (auto-generated)

    private String tagalog;    // The Tagalog word
    private String english;    // The English translation
}
```

**What it does**: This class represents one row in the Language Content Service's database table. Each `Item` object corresponds to one vocabulary entry with an ID, Tagalog word, and English translation.

#### 2. ItemRepository.java - The Database Access Layer

```java
/**
 * Repository interface for managing Item entities in the PostgreSQL database.
 * Provides CRUD operations and query methods for vocabulary items.
 * Extends JpaRepository to inherit standard database operations.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // Custom query methods can be added here if needed
}
```

**What it does**: This is like a librarian that knows how to talk to the Language Content Service's database. It automatically provides methods like:
- `findAll()` - Get all vocabulary items
- `findById(id)` - Find one item by its ID
- `save(item)` - Save a new item to the database
- `delete(item)` - Remove an item from the database

#### 3. ItemService.java - The Business Logic Layer

```java
/**
 * Service class for managing vocabulary items (Items).
 * Provides business logic for retrieving and manipulating Item data
 * from the PostgreSQL database through the ItemRepository.
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Retrieves all vocabulary items from the database.
     * @return List of all Item entities containing Tagalog-English translations
     */
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
```

**What it does**: This is the middleman between the Language Content Service's REST endpoints (controllers) and its database. It contains the business logic and decides what data to fetch and how to process it.

### 4. HomeController.java - The REST API Layer

```java
@RestController
public class HomeController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String home() {
        return "Hello, World!";
    }

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }
}
```

**What it does**: This controller exposes REST endpoints for the Language Content Service:
- `GET /` - Simple health check endpoint
- `GET /items` - Returns all vocabulary items from the database

## How Data Flows Through the Language Content Service

1. **Client Request**: A request comes to `http://localhost:8082/items`
2. **Controller**: `HomeController.getAllItems()` receives the request
3. **Service**: `ItemService.getAllItems()` processes the business logic
4. **Repository**: `ItemRepository.findAll()` executes `SELECT * FROM vocabulary`
5. **Database**: PostgreSQL returns the raw data
6. **Repository**: Converts database rows into `Item` objects
7. **Service**: May process the data further (currently just passes through)
8. **Controller**: Returns the list of `Item` objects as JSON
9. **Client**: Receives the vocabulary data

## Example: Fetching All Vocabulary Words

When a client requests all vocabulary words from the Language Content Service:

```java
// In HomeController.java
@GetMapping("/items")
public List<Item> getAllItems() {
    return itemService.getAllItems();  // Calls service
}

// In ItemService.java
public List<Item> getAllItems() {
    return itemRepository.findAll();  // Executes SELECT * FROM vocabulary
}

// Returns JSON like:
// [{"id": 1, "tagalog": "kumusta", "english": "hello"}, ...]
```

## Development & Deployment

### Running Services
```bash
# Run all services
.\gradlew :hello-world-service:bootRun :language-content-service:bootRun

# Run individual services
.\gradlew :hello-world-service:bootRun      # Port 8081
.\gradlew :language-content-service:bootRun # Port 8082
```

### Service URLs
- **Hello World Service**: http://localhost:8081/
- **Language Content Service**: http://localhost:8082/

### Future Services (Planned)
- **User Progress Service**: MongoDB, tracks learning progress (Port 8083)
- **Authentication Service**: OAuth2, manages user sessions (Port 8084)
- **Analytics Service**: Metrics collection and reporting (Port 8085)
