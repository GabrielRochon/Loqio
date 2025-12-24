# Language Content Service API Documentation

This document describes the REST API endpoints for the Language Content Service.

### Using Invoke-WebRequest (PowerShell):
```powershell
# Get all languages
Invoke-WebRequest -Uri http://localhost:8082/languages

# Add a language
Invoke-WebRequest -Method POST -Uri http://localhost:8082/languages -Headers @{ "Content-Type" = "application/json" } -Body '{"name":"Italian"}'
