# Language Content Service API Documentation

This document describes the REST API endpoints for the Language Content Service.

### Using Invoke-WebRequest (PowerShell):
```powershell
# Get all languages
Invoke-WebRequest -Uri http://localhost:8082/languages

# Add a language
Invoke-WebRequest -Method POST -Uri http://localhost:8082/languages -Headers @{ "Content-Type" = "application/json" } -Body '{"name":"Italian"}'

# Get modules for a language by language ID
Invoke-WebRequest -Uri http://localhost:8082/languages/1

# Delete a language by ID
Invoke-WebRequest -Method DELETE -Uri http://localhost:8082/languages/1

# Add a module
Invoke-WebRequest -Method POST -Uri http://localhost:8082/modules -Headers @{ "Content-Type" = "application/json" } -Body '{"language":{"id":1,"name":"English"},"name":"Basic Vocabulary"}'

# Delete a module by ID
Invoke-WebRequest -Method DELETE -Uri http://localhost:8082/modules/1

# Get sentences for a module by module ID
Invoke-WebRequest -Uri http://localhost:8082/modules/1/sentences

# Add a sentence
Invoke-WebRequest -Method POST -Uri http://localhost:8082/sentences -Headers @{ "Content-Type" = "application/json" } -Body '{"module":{"id":1,"name":"Basic Greetings","language":{"id":1,"name":"English"}},"position":1,"learningText":"Hello","translationText":"Hola","speaker":1}'

# Delete a sentence by ID
Invoke-WebRequest -Method DELETE -Uri http://localhost:8082/sentences/1
