# AGENTS Guidelines for This Repository

This repository is a **Kotlin Multiplatform template** with three active modules:

- `androidApp`: Android client built with Jetpack Compose
- `shared`: Kotlin Multiplatform shared code for Android and JVM/server usage
- `server`: JVM backend built with Ktor/Netty

Use the guidance below to keep generated changes aligned with the current project structure and conventions.

## 1. Current Project Specifications

- **Language:** Kotlin
- **Kotlin version:** `2.2.10`
- **Build system:** Gradle with Kotlin DSL
- **Android Gradle Plugin:** `8.7.3`
- **Android compile SDK:** `34`
- **Android target SDK:** `34`
- **Android min SDK:** `26`
- **JVM toolchain:** `17` for shared/server modules
- **Android bytecode target:** JVM `1.8`

Dependency and plugin versions are centralized in `gradle/libs.versions.toml`.

## 2. Module Overview

### `androidApp`
- Android entrypoint module
- Uses **Jetpack Compose** with **Material 3**
- Uses **Navigation Compose** for screen navigation
- Uses **Hilt** for dependency injection
- Contains `ViewModel` classes that expose `StateFlow`

### `shared`
- Kotlin Multiplatform module
- Current targets: **`androidTarget()`** and **`jvm()`**
- Contains shared:
  - DTOs
  - domain models
  - repositories
  - use cases
  - route definitions
  - simple platform abstractions
- Uses **Ktor client**, **kotlinx.serialization**, and **Kermit**

### `server`
- JVM application module
- Runs a **Ktor Netty** server
- Reuses types from `shared`
- Includes server-side dependencies for:
  - Ktor server
  - JWT/auth/session support
  - Exposed
  - PostgreSQL
  - HikariCP
  - Logback

## 3. Architecture and Code Organization

The codebase is currently a lightweight template rather than a fully layered production app, but these conventions are already present and should be followed:

- Keep cross-platform business logic in `shared/src/commonMain`
- Keep Android UI and Android-only DI in `androidApp`
- Keep backend HTTP endpoints in `server`
- Prefer putting reusable request/response models, routes, mappers, repositories, and domain models in `shared`
- Use repositories in `shared` for Ktor/data-access work and DTO-to-domain mapping
- Use small **UseCase** classes in `shared` for orchestration and `Flow<Result<T>>` handling via the existing `BaseUseCase` pattern

### Shared layer patterns in use
- `dto/` for serialized transport models
- `domain/` for app-facing domain models
- `mapper/` for DTO → domain conversion
- `repositories/` for shared data access and HTTP calls via Ktor client
- `routes/` for shared route constants and parameter names
- `usecases/` for wrapping repository calls in app-facing operations and error/loading flow patterns

When adding new features, extend those folders instead of introducing unrelated patterns unless a deliberate refactor is part of the task.

## 4. Android App Conventions

- Build new Android UI with **Jetpack Compose**
- Use **Material 3** components unless there is a reason not to
- Keep navigation in the existing navigation helpers/files under `androidApp/src/main/java/.../navigation`
- Prefer **ViewModel + StateFlow** for screen state
- Surface loading and error state through the existing `BaseViewModel` pattern unless refactoring it intentionally
- Register Android-wide dependencies with **Hilt** modules

### DI
- `MyApplication` is annotated with `@HiltAndroidApp`
- Hilt modules live under `androidApp/.../di`
- Hilt currently provides both shared repositories and use cases from `MainModule`
- Android `ViewModel`s use constructor injection with `@HiltViewModel`

### Package naming
- The main package namespace is `com.kmp.template.*`
- Do not rename packages casually; only normalize package names when the task explicitly includes cleanup/refactoring

## 5. Networking and Serialization

- Prefer **Ktor** for both client and server work
- Shared networking code currently uses `HttpClient` from Ktor inside repositories
- JSON serialization uses **kotlinx.serialization**
- Shared route definitions live in `shared/src/commonMain/kotlin/com/kmp/template/routes`
- Backend base URL is currently defined in `shared/src/commonMain/kotlin/com/kmp/template/Config.kt`

When implementing new endpoints:
- Add route constants/parameter names in `shared`
- Add DTOs in `shared/dto`
- Add mapping logic in `shared/mapper`
- Add or extend repository methods in `shared/repositories`
- Add or extend use cases in `shared/usecases`
- Keep Ktor request construction and response mapping in repositories when following the current template pattern
- Mirror the endpoint implementation in `server`

## 6. Concurrency Guidelines

- Use **Kotlin Coroutines** for async work
- Prefer `Flow` when following existing shared/use case patterns
- Avoid introducing RxJava
- Keep coroutine usage consistent with existing `ViewModel` and use case code

If you introduce dispatchers or more advanced concurrency control, prefer abstractions that keep code testable and multiplatform-friendly.

## 7. Server Conventions

- The backend entrypoint is `com.kmp.template.server.ServerKt`
- The server runs on port `5000` by default, or `PORT` from the environment
- Ktor server setup currently installs:
  - `ContentNegotiation`
  - `CORS`
- `anyHost()` is currently enabled for template convenience; treat it as development-friendly default, not a production-ready CORS policy

Keep the server simple and template-oriented unless the task requires expanding it.

## 8. Testing Guidance

Current testing setup is minimal:

- `shared` includes `kotlin-test`
- `server` is configured to run tests on **JUnit Platform**

When adding tests:
- Put shared logic tests in `shared/src/*Test`
- Prefer testing shared use cases, mappers, and route behavior first
- Add Android UI or ViewModel tests only when relevant to the change
- Reuse existing Gradle module boundaries instead of creating ad hoc test locations

## 9. Build and Tooling Notes

- Prefer updating versions through `gradle/libs.versions.toml`
- Keep module build scripts in Kotlin DSL
- Avoid adding unnecessary new plugins or modules for small features
- Preserve the current multiplatform setup unless the task explicitly requires new targets like iOS or Web

## 10. External References

When unsure, prefer official documentation for the technologies already used here:

- [Kotlin Multiplatform documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Android developer documentation](https://developer.android.com)
- [Jetpack Compose documentation](https://developer.android.com/jetpack/compose)
- [Ktor documentation](https://ktor.io/docs/)
- [Hilt documentation](https://developer.android.com/training/dependency-injection/hilt-android)

---

## 11. Useful Agent Skills Recap

| Skill Folder          | Purpose                                            |
| --------------------- | -------------------------------------------------- |
| `architecture/`       | Clean architecture, ViewModels, and Data Layer.    |


If this template evolves significantly—such as adding iOS/Web targets, replacing Hilt, or introducing a stricter architecture—update this file so future agent work stays aligned with the repository.

