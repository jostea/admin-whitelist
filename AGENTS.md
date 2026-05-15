# AGENTS.md

## Project Overview

This is a Spring Boot application for managing Project Zomboid server whitelist and admin functionality.

Main responsibilities of the application:
- Manage player whitelist
- Manage player access levels
- Execute RCON-related operations
- Provide admin-related endpoints
- Provide monitoring/health endpoints

Project structure:
- Controllers are located in `web`
- Business logic is located in `domain/service`
- Repositories are located in `domain/repository`
- Database configuration is located in `config/database`
- SQLite support classes are located in `support/database`
- Process/RCON utilities are located in `support/process`

---

## Build System

This project uses Maven.

Always prefer Maven Wrapper commands.

Linux/macOS:

```bash
./mvnw clean test
```

Windows:

```bash
mvnw.cmd clean test
```

---

## Java and Spring Standards

- Use constructor injection.
- Avoid field injection.
- Keep controllers thin.
- Controllers should delegate business logic to services.
- Services should contain business logic.
- Repositories should only handle persistence.
- Avoid duplicated logic.
- Keep methods focused and small.
- Prefer readable code to clever code.

---

## Project Architecture

Layer responsibilities:

### web
Contains:
- REST controllers
- HTTP request handling
- Response handling

Controllers must NOT:
- contain business logic
- access repositories directly

### domain/service
Contains:
- business rules
- orchestration logic
- validation logic

### domain/repository
Contains:
- persistence logic
- JPA repositories

### support
Contains:
- infrastructure utilities
- process execution
- SQLite support
- RCON utilities

---

## Testing Strategy

Use RestAssured for API and integration tests.

Test source root:

```text
src/test/java/com/jostea/zomboid/whitelist
```

Preferred test structure:

```text
src/test/java/com/jostea/zomboid/whitelist/
├── api
├── config
├── fixtures
├── support
└── integration
```

---

## RestAssured Rules

When creating API tests:

- Use SpringBootTest with RANDOM_PORT
- Inject server port dynamically
- Use RestAssured
- Validate status codes
- Validate response body
- Cover happy path and negative scenarios
- Keep tests isolated
- Prefer deterministic tests
- Avoid external dependencies where possible

Preferred annotations:

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
```

---

## Test Naming Rules

Use descriptive names.

Examples:

```text
shouldReturn200WhenServerIsHealthy
shouldRejectUnknownPlayer
shouldReturnWhitelistEntries
```

Avoid names like:

```text
test1
checkApi
verifyStuff
```

---

## Safety Rules

Be careful with:
- RCON commands
- process execution
- restart/quit operations

Never:
- execute destructive real server commands during tests
- hardcode credentials
- expose secrets
- commit private server configs
- modify rcon.exe

---

## Files That Should Not Be Modified Without Explicit Need

Avoid modifying:

```text
rcon/rcon.exe
rcon/rcon.yaml
src/main/resources/application.properties
src/main/java/com/jostea/zomboid/whitelist/config/database/*
src/main/java/com/jostea/zomboid/whitelist/support/database/*
```

unless the task explicitly requires it.

---

## API Testing Guidelines

For controller testing:

Preferred approach:
- full integration tests using RestAssured

Avoid:
- excessive mocking
- fragile implementation-coupled tests

Tests should validate:
- HTTP contract
- response payload
- validation behavior
- error handling

---

## Database Rules

- Do not use production database credentials
- Prefer isolated test configuration
- Prefer in-memory or dedicated test database when possible
- Keep database setup reproducible

---

## Logging Rules

- Do not log secrets
- Do not log passwords
- Avoid excessive console noise in tests

---

## Refactoring Rules

When refactoring:
- preserve behavior
- keep changes minimal
- avoid unnecessary architecture rewrites
- run tests after changes

---

## Agent Workflow

Before changing code:

1. Analyze related controller/service/repository
2. Understand current behavior
3. Make minimal required changes
4. Add or update tests
5. Run tests
6. Summarize changes

---

## Preferred Commands

Run all tests:

```bash
./mvnw clean test
```

Run a single test class:

```bash
./mvnw -Dtest=ClassName test
```

Run Spring Boot app:

```bash
./mvnw spring-boot:run
```

---

## Definition of Done

A task is complete only when:

- code compiles
- relevant tests pass
- no secrets were introduced
- architecture rules were respected
- API tests were updated if behavior changed
- changes are minimal and focused

---

## Preferred Development Style

Prefer:
- readable code
- explicit naming
- maintainability
- deterministic tests
- small commits

Avoid:
- unnecessary abstractions
- overengineering
- hidden side effects
- large unrelated refactors