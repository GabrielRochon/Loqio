# Language Content Service API Documentation

This document describes the REST API endpoints for the Language Content Service.

## Base URL
All endpoints are relative to the base URL of the service (e.g., `http://localhost:8082`).

## Endpoints

### Hello World
- **Method**: GET
- **URL**: `/`
- **Description**: Returns a simple hello world message.
- **Response**: String
  ```json
  "Hello, World!"
  ```

### Get All Languages
- **Method**: GET
- **URL**: `/languages`
- **Description**: Retrieves a list of all languages.
- **Response**: Array of Language objects
  ```json
  [
    {
      "id": 1,
      "name": "English"
    },
    {
      "id": 2,
      "name": "Spanish"
    }
  ]
  ```

### Add Language
- **Method**: POST
- **URL**: `/languages`
- **Description**: Adds a new language to the database.
- **Request Body**: Language object (without id, as it's auto-generated)
  ```json
  {
    "name": "French"
  }
  ```
- **Response**: Created Language object with generated id
  ```json
  {
    "id": 3,
    "name": "French"
  }
  ```

### Get Modules by Language
- **Method**: GET
- **URL**: `/languages/{id}`
- **Description**: Retrieves all modules for a specific language.
- **Parameters**: `id` (Long) - The language ID
- **Response**: Array of Module objects
  ```json
  [
    {
      "id": 1,
      "language": {
        "id": 1,
        "name": "English"
      },
      "name": "Basic Greetings"
    }
  ]
  ```

### Get Sentences by Module
- **Method**: GET
- **URL**: `/modules/{id}/sentences`
- **Description**: Retrieves all sentences for a specific module.
- **Parameters**: `id` (Long) - The module ID
- **Response**: Array of Sentence objects
  ```json
  [
    {
      "id": 1,
      "module": {
        "id": 1,
        "language": {
          "id": 1,
          "name": "English"
        },
        "name": "Basic Greetings"
      },
      "position": 1,
      "learningText": "Hello",
      "translationText": "Hola",
      "speaker": 1
    }
  ]
  ```

## Data Models

### Language
- `id` (Long): Unique identifier (auto-generated)
- `name` (String): Language name

### Module
- `id` (Long): Unique identifier (auto-generated)
- `language` (Language): Associated language
- `name` (String): Module name

### Sentence
- `id` (Long): Unique identifier (auto-generated)
- `module` (Module): Associated module
- `position` (Integer): Position in the module
- `learningText` (String): Text to learn
- `translationText` (String): Translation
- `speaker` (Integer): Speaker identifier

## Example Usage

### Using curl (bash/Linux):
```bash
curl -X POST http://localhost:8082/languages \
  -H "Content-Type: application/json" \
  -d '{"name":"French"}'
```

### Using Invoke-WebRequest (PowerShell):
```powershell
Invoke-WebRequest -Method POST -Uri http://localhost:8082/languages -Headers @{ "Content-Type" = "application/json" } -Body '{"name":"Italian"}'
