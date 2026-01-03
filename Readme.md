# LOQIO

*Etymology check: Loqio comes from Latin loqui ("to speak"). It's a witty word play for which I have no credit for (thanks ChatGPT.)*

**Loqio** is a comprehensive AI-powered language learning platform built with a microservice architecture. It features personalized learning paths tailored to individual progress, an integrated AI assistant for real-time guidance, and gamification elements to make language acquisition engaging and effective.

This full-stack web application serves as an **educational project** exploring modern software technologies, from backend microservices and databases to cloud deployment and DevOps practices.

![](./documentation-assets/Loqio%20Screenshot%201.png)

![](./documentation-assets/Loqio%20Screenshot%202.png)

## Setup

### Prerequisites

- **Docker** must be installed (for running microservices in containers)
- **Node.js and npm** must be installed (for frontend development if not using Docker)
- **Java 21** and **Gradle** must be installed (for backend development if not using Docker)

### Database Configuration

Before running the microservices locally, ensure your current IP address is allow-listed in the Azure Portal for the PostgreSQL database to avoid connection timeouts:

1. Go to the Azure Portal and navigate to the `psql-languages-prod` PostgreSQL server resource.
2. Select **Networking** from the left-hand menu.
3. Under **Firewall rules**, click **Add current client IP address** to allow-list your IP.

## Running the Microservices

| Use Case | Docker Command | Description |
|----------|----------------|-------------|
| Bring up all containers | `docker compose up` | Starts all microservices and frontend simultaneously |
| Rebuild all services | `docker compose up --build` | Rebuilds all Docker images and restarts containers after code changes |
| Rebuild single service | `docker compose up --build <service-name>` | Rebuilds and restarts only the specified service (e.g., `language-content-service`, `hello-world-service`, `frontend`) |

**Service Ports:**
- Frontend: http://localhost:3000/
- Hello World Service: http://localhost:8081/
- Language Content Service: http://localhost:8082/

## Learning Checklist

|   | Category | Technology Used | Microservice Requiring Creation | Feature |
| - | -------- | --------------- | ------------------------------- | ------- |
| ✅ | Backend (Microservice-Friendly) | <ul><li>Java</li><li>SpringBoot</li></ul> | - | <ul><li>Basic project skeleton</li></ul> |
| ✅ | Unit Tests | <ul><li>JUnit</li></ul> | - | <ul><li>Basic endpoint tests</li></ul> |
| ✅ | Relational DB | <ul><li>PostgreSQL</li></ul> | Language Content | <ul><li>Fetch a list of languages</li><li>Fetch their curriculum</li></ul> |
| ✅ | UI | <ul><li>React</li><li>Typescript</li><li>SCSS</li><li>Azure Blob Storage</li></ul> | Frontend | <ul><li>Navigate to a language course</li><li>Display the modules / sentences per module</li><li>Display background images</li></ul> |
| ✅ | Containerization | <ul><li>Docker</li></ul> | - | <ul><li>Launch the web app in one command</li><li>Prepare for Kubernetes later</li></ul> |
| ✅ | Caching | <ul><li>Redis</li></ul> | - | <ul><li>Reduce operation time to fetch the same language's curriculum many times</li></ul> |
| ⬜ | Non-Relational DB | <ul><li>MongoDB</li></ul> | User Progress | <ul><li>Track the words learned</li><li>Track experience points of a user</li></ul> |
| ⬜ | Authentication | <ul><li>Spring Authorization Server (OAuth2)</li></ul> | Authentication | <ul><li>Keep user's progress</li></ul> |
| ⬜ | Authorization | <ul><li>Azure Entra Authentication</li></ul> | - | <ul><li>Remove the need to manually allow-list every IP that should have access to the PostgreSQL database</li></ul> |
| ⬜ | Events / Pub-Sub | <ul><li>Kafka</li></ul> | - | <ul><li>Calculate newly acquired experience points after lesson completion</li></ul> |
| ⬜ | CI/CD | <ul><li>GitHub Actions</li></ul> | - | <ul><li>Run unit tests upon raising PRs</li></ul> |
| ⬜ | Deployment + Cloud | <ul><li>Kubernetes</li><li>Azure (AKS)</li></ul> | - | <ul><li>Running end-to-end tests</li><li>Overseeing deployments</li></ul> |

## Nice-to-haves

|   | Category | Technology Used | Microservice Requiring Creation | Feature |
| - | -------- | --------------- | ------------------------------- | ------- |
| ⬜ | Rest API Documentation | <ul><li>Swagger</li></ul> | - | <ul><li>Document all microservices' API endpoints</li></ul>
| ⬜ | Telemetry | <ul><li>OpenTelemetry</li></ul> | Analytics | <ul><li>Emit lesson completion metrics</li></ul> |
| ⬜ | Monitoring | <ul><li>Grafana</li><li>Prometheus</li></ul> | - | <ul><li>Consume metrics</li><li>Display them on graphs</li></ul> |
| ⬜ | Code Quality | <ul><li>SonarQube</li></ul> | - | <ul><li>Highlight possible code bugs</li><li>Highlight quality improvements upon running Merge Validation pipeline when raising PR</li></ul>
| ⬜ | AI Integration | <ul><li>Claude</li></ul> | - | <ul><li>Add an AI assistant to ask questions during the lesson</li></ul>
