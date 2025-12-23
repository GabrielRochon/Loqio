# How Our Spring Boot App Works with PostgreSQL Database

This document explains how our vocabulary learning application connects to and retrieves data from an Azure PostgreSQL database. We'll break it down step by step, assuming you have no prior knowledge of Spring Boot or databases.

## What is This Application?

Our app is a language learning tool that stores vocabulary words with their translations. It uses Java and Spring Boot framework to connect to a PostgreSQL database hosted on Microsoft Azure.

## Database Connection Setup

The app connects to Azure PostgreSQL using these configuration settings in `application.properties`:

```
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:postgres}?sslmode=require
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:password}
```

- **JDBC URL**: Tells the app how to connect to the database
- **SSL Mode**: Ensures secure connection to Azure (required)
- **Environment Variables**: Database credentials are stored as environment variables for security

## The Three Key Components

### 1. Item.java - The Data Model

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

**What it does**: This class represents one row in the database table. Each `Item` object corresponds to one vocabulary entry with an ID, Tagalog word, and English translation.

### 2. ItemRepository.java - The Database Access Layer

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

**What it does**: This is like a librarian that knows how to talk to the database. It automatically provides methods like:
- `findAll()` - Get all vocabulary items
- `findById(id)` - Find one item by its ID
- `save(item)` - Save a new item to the database
- `delete(item)` - Remove an item from the database

### 3. ItemService.java - The Business Logic Layer

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

**What it does**: This is the middleman between the app's interface (controllers) and the database. It contains the business logic and decides what data to fetch and how to process it.

## How Data Flows Through the System

1. **User Request**: Someone visits the app's webpage
2. **Controller**: Receives the request and calls the service
3. **Service**: Asks the repository to get data from database
4. **Repository**: Executes SQL query on PostgreSQL
5. **Database**: Returns the raw data
6. **Repository**: Converts database rows into Item objects
7. **Service**: May process the data further
8. **Controller**: Formats data for display
9. **View**: Shows the vocabulary list to the user

## Example: Fetching All Vocabulary Words

When the app needs to show all vocabulary words:

```java
// In the controller
List<Item> vocabulary = itemService.getAllItems();

// This executes: SELECT * FROM vocabulary
// And returns a list of Item objects
```
