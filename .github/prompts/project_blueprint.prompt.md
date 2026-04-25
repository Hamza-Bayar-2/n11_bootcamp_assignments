# Project Modernization & Implementation Rules (Java / CQRS / DDD)

This document serves as a guide for implementing new features, services, and endpoints in the project. Always follow these rules to maintain architectural consistency.

## 1. Architectural Layers & Workflow

- **API Layer (`api.controller`):** REST Endpoints. Only responsible for receiving requests and returning responses.
- **DTO Layer (`api.dto`):** Data Transfer Objects for response bodies.
- **Application Layer (`application.handler`):** Business logic entry points using the CQRS pattern.
- **Service Layer (`application.service`):** Domain logic and infrastructure orchestration.
- **Domain Layer (`domain.entity`, `domain.repository`):** Core entities and data access interfaces.

## 2. CQRS Pattern (Commands & Handlers)

- For any **write operation** (Create, Update, Delete, Login, etc.), use a **Command** and a **CommandHandler**.
- **Naming Convention:** `Action` + `Command` (e.g., `RegisterCommand`).
- **Command Structure:**
  - Use Java `record` for commands.
  - Let the Command be used directly as the `@RequestBody` in the controller if it's a simple mapping.
  - **Validation:** Use Jakarta Validator annotations (e.g., `@NotBlank`, `@Email`, `@PasswordMatches`) directly on the Command record fields or at the record level for complex validations.
- **Handler Structure:**
  - Must have a `handle(Command command)` method.
  - Path: `com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.[feature]`
  - Must return `Result<T>` to handle flow control without exceptions.

## 3. Service Layer Rules

- Services must be decoupled.
- Use **Constructor Injection** with `private final` fields.
- **Naming:** Fields should have an underscore prefix (e.g., `private final UserService _userService`).
- Avoid mixing concerns: `CookieService` handles cookies, `JwtTokenService` handles token generation, `UserService` handles DB operations.

## 4. Response Strategy (`Result<T>` Pattern)

- **NEVER** use Exceptions for business flow control.
- Always wrap return types in the `Result<T>` or `Result` class.
- **Controller Flow:** Use the "fail-fast" approach. If `result.isSuccess()` is false, return immediately with `ResponseEntity.badRequest()`. Successful logic should continue outside the `if` block.
  - *Example:*
    ```java
    if (!result.isSuccess()) return ResponseEntity.badRequest().body(result.errorMessage());
    // continue with success logic...
    ```

## 5. Security & Authentication

- **Stateless:** Ensure the application remains stateless.
- **JWT & Cookies:**
  - JWT and Refresh Tokens must be stored in **HttpOnly, Secure, Strict** cookies.
  - Use `CookieService` for all cookie operations.
- **Validation:** Use standard Jakarta/JSR-303 annotations (`@NotNull`, `@NotBlank`) on Commands and Entities.

## 6. Implementation Checklist for New Features

1. **Domain:** Create/Update Entity and Repository.
2. **Service:** Add necessary methods to the Service interface and implementation.
3. **Command:** Define a `record` for the operation.
4. **DTO:** If the return type is complex, create a `record` in `api.dto`.
5. **Handler:** Implement the logic in a new `CommandHandler`.
6. **Controller:** Add a `@PostMapping` (or relevant) method that calls the handler.

---

_Follow these rules strictly to ensure "Clean Code" and "SOLID" principles._
