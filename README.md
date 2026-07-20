# Taxi Navigator API

## About
The Taxi Navigator API is a RESTful Spring Boot application that helps commuters find public taxi routes between two locations in South Africa. It was created to make commuting easier by providing route information digitally, reducing the need to rely on strangers for directions.

The API allows administrators to manage taxi routes while providing public endpoints that commuters can use to discover available routes.

## Live API
The application is deployed on Render.

**Base URL**
```text
https://taxinavigator-api.onrender.com
```

To use the hosted API, simply replace:
```text
http://localhost:8080
```
with
```text
https://taxinavigator-api.onrender.com
```
when making requests from Postman or any HTTP client.

## Features
- JWT authentication and authorization using Spring Security.
- Secure admin endpoints under `/api/admin/**`.
- Public endpoints for commuters under `/api/users/**`.
- Find available taxi routes between two locations.
- Supports direct and multi-stop route navigation.
- Bidirectional routing for every stored route.
- Create, update and delete taxi routes.
- Bulk route uploads using CSV files.
- Downloadable CSV template for importing routes.
- Spring Boot Actuator health endpoint.
- Dockerized for local development.
- Deployed on Render with automatic deployment on push to `main`.

## Technologies

**Backend**
- Java 17
- Spring Boot 3
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Spring Boot Actuator

**Utilities**
- Lombok
- MapStruct
- OpenCSV

**Testing**
- JUnit 5
- Mockito
- MockMvc
- AssertJ
- H2 (in-memory database, used for integration tests)

**DevOps**
- Docker (local development)
- GitHub Actions (CI)
- Qodana (static code analysis)
- Render (hosting and deployment)

## Running Locally

**Prerequisites**
- Java 17
- Maven
- Docker Desktop

Ensure Docker Desktop is running before starting the application.

**Clone the repository**
```bash
git clone https://github.com/mokgadiphasha/TaxiNavigator.git
cd TaxiNavigator
```

**Build the project**
```bash
mvn clean package
```

**Start the application**
```bash
docker compose up --build
```

The API will be available at:
```text
http://localhost:8080
```

## Testing
The project contains both unit and integration tests, currently covering core happy-path scenarios. Negative and edge-case coverage is in progress.

**Testing Frameworks**
- JUnit 5
- Mockito
- MockMvc
- AssertJ
- H2 (in-memory database)

Run all tests using:
```bash
mvn test
```
or
```bash
mvn clean verify
```

## Continuous Integration & Deployment
Every push and pull request automatically triggers GitHub Actions to:
- Build the application
- Run automated tests
- Verify the Maven build
- Execute Qodana static code analysis

Render is connected directly to the repository. Every push to `main` that passes CI automatically triggers a new deployment, so the live API always reflects the latest committed code.

## API Documentation
- **Postman Collection:** https://documenter.getpostman.com/view/43881123/2sB3HeuiVg
- **Demo Video:** https://youtu.be/M5OqfpXyBh8
- **CSV Template:** https://tinyurl.com/292jsamb

## Future Improvements
- Improve route-finding algorithms for more optimal journeys.
- Support location aliases (e.g. "MTN Noord", "Noord Taxi Rank", "Johannesburg Taxi Rank").
- Add filtering and searching capabilities for administrators.
- Log unavailable route requests so they can be added in future updates.
- Improve exception monitoring and application logging.
- Expand taxi route coverage across South Africa.
- Develop a front-end application for commuters.

## Contact
If you have any feedback, suggestions, or would like to collaborate, feel free to get in touch.

Email: phashamokgadi631@gmail.com
