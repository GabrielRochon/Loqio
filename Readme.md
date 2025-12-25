# LOQIO

*Etymology check: Loqio comes from Latin loqui ("to speak"). It's a witty word play for which I have no credit for (thanks ChatGPT.)*

**Loqio** is a comprehensive AI-powered language learning platform built with a microservice architecture. It features personalized learning paths tailored to individual progress, an integrated AI assistant for real-time guidance, and gamification elements to make language acquisition engaging and effective.

This full-stack web application serves as an educational project exploring modern software technologies, from backend microservices and databases to cloud deployment and DevOps practices.

## Setup

Before running the microservices locally, ensure your current IP address is allow-listed in the Azure Portal for the PostgreSQL database to avoid connection timeouts:

1. Go to the Azure Portal and navigate to the `psql-languages-prod` PostgreSQL server resource.
2. Select **Networking** from the left-hand menu.
3. Under **Firewall rules**, click **Add current client IP address** to allow-list your IP.

## Running the Microservices

To run all microservices simultaneously during development:

```bash
.\gradlew --parallel :hello-world-service:bootRun :language-content-service:bootRun
```

This will start:
- **Hello World Service** on http://localhost:8081/
- **Language Content Service** on http://localhost:8082/

To run individual services:
- Hello World: `.\gradlew :hello-world-service:bootRun`
- Language Content: `.\gradlew :language-content-service:bootRun`

## Running the Frontend

To run the React frontend application:

```bash
cd frontend
npm start
```

This will start the React development server on http://localhost:3000/

**Prerequisites:**
- Node.js and npm must be installed
- Backend services must be running (see above)

## Learning Checklist

|   | Category | Technology Used | Microservice Requiring Creation | Feature |
| - | -------- | --------------- | ------------------------------- | ------- |
| ✅ | Backend (Microservice-Friendly) | <ul><li>Java</li><li>SpringBoot</li></ul> | - | <ul><li>Basic project skeleton</li></ul> |
| ✅ | Unit Tests | <ul><li>JUnit</li></ul> | - | <ul><li>Basic endpoint tests</li></ul> |
| ✅ | Relational DB | <ul><li>PostgreSQL</li></ul> | Language Content | <ul><li>Fetch a list of languages</li><li>Fetch their curriculum</li></ul> |
| 🏗️ | UI | <ul><li>React</li><li>Typescript</li><li>SCSS</li><li>Azure Blob Storage</li></ul> | - | <ul><li>Navigate to a language course</li><li>Display the modules / sentences per module</li><li>Display background images</li></ul> |
| ⬜ | Non-Relational DB | <ul><li>MongoDB</li></ul> | User Progress | <ul><li>Track the words learned</li><li>Track experience points of a user</li></ul> |
| ⬜ | Rest API Documentation | <ul><li>Swagger</li></ul> | - | <ul><li>Document all microservices' API endpoints</li></ul>
| ⬜ | Caching | <ul><li>Redis</li></ul> | - | <ul><li>Reduce operation time to fetch the same language's curriculum many times</li></ul> |
| ⬜ | Authentication | <ul><li>Spring Authorization Server (OAuth2)</li></ul> | Authentication | <ul><li>Keep user's progress</li></ul> |
| ⬜ | Events / Pub-Sub | <ul><li>Kafka</li></ul> | - | <ul><li>Calculate newly acquired experience points after lesson completion</li></ul> |
| ⬜ | Telemetry | <ul><li>OpenTelemetry</li></ul> | Analytics | <ul><li>Emit lesson completion metrics</li></ul> |
| ⬜ | Monitoring | <ul><li>Grafana</li><li>Prometheus</li></ul> | - | <ul><li>Consume metrics</li><li>Display them on graphs</li></ul> |
| ⬜ | CI/CD | <ul><li>GitHub Actions</li></ul> | - | <ul><li>Run unit tests upon raising PRs</li></ul> |
| ⬜ | Code Quality | <ul><li>SonarQube</li></ul> | - | <ul><li>Highlight possible code bugs</li><li>Highlight quality improvements upon running Merge Validation pipeline when raising PR</li></ul>
| ⬜ | Deployment + Cloud | <ul><li>Kubernetes</li><li>Docker</li><li>Azure (AKS)</li></ul> | - | <ul><li>Running end-to-end tests</li><li>Overseeing deployments</li></ul> |
| ⬜ | AI Integration | <ul><li>Claude</li></ul> | - | <ul><li>Add an AI assistant to ask questions during the lesson</li></ul>
