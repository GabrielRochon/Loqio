# LOQIO

*Etymology check: Loqio comes from Latin loqui ("to speak"). It's a witty word play for which I have no credit for (thanks ChatGPT.)*

**Loqio** is a comprehensive AI-powered language learning platform built with a microservice architecture. It features personalized learning paths tailored to individual progress, an integrated AI assistant for real-time guidance, and gamification elements to make language acquisition engaging and effective.

This full-stack web application serves as an educational project exploring modern software technologies, from backend microservices and databases to cloud deployment and DevOps practices.

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

## Learning Checklist

|   | Category | Technology Used | Microservice Requiring Creation | Feature |
| - | -------- | --------------- | ------------------------------- | ------- |
| ✅ | Backend (Microservice-Friendly) | Java / SpringBoot | - | Basic project skeleton |
| ✅ | Unit Tests | JUnit | - | Basic endpoint tests |
| 🏗️ | Relational DB | PostgreSQL | Language Content | Fetch a list of languages and their curriculum |
| ⬜ | UI | React | - | Display the list of words per language |
| ⬜ | Non-Relational DB | MongoDB | User Progress | Track the words learned and experience points of a user |
| ⬜ | Rest API Documentation | Swagger | - | Document all microservices' API endpoints 
| ⬜ | Caching | Redis | - | Reduce operation time to fetch the same language's curriculum many times |
| ⬜ | Authentication | Spring Authorization Server (OAuth2) | Authentication | Keep user's progress |
| ⬜ | Events / Pub-Sub | Kafka | - | Calculate newly acquired experience points after lesson completion |
| ⬜ | Telemetry | OpenTelemetry | Analytics | Emit lesson completion metrics |
| ⬜ | Monitoring | Grafana / Prometheus | - | Consume metrics and display them on graphs |
| ⬜ | CI/CD | GitHub Actions | - | Run unit tests upon raising PRs |
| ⬜ | Code Quality | SonarQube | - | Highlight possible code bugs and quality improvements upon running Merge Validation pipeline when raising PR
| ⬜ | Deployment + Cloud | Kubernetes + Docker + Azure (AKS) | - | Running end-to-end tests + overseeing deployments |
| ⬜ | AI Integration | Claude | - | Add an AI assistant to ask questions during the lesson
