---
name: android-architecture
description: Guidance for maintaining this repository's Kotlin Multiplatform architecture, including the Android Compose client, shared repository/use case layer, server module, and Hilt wiring.
---

# KMP Template Architecture

## Instructions

When designing or refactoring code in this repository, follow the current **Kotlin Multiplatform template** structure instead of introducing a generic Android-only architecture.

### 1. Module Structure
This project currently has three active modules.

* **`androidApp`**
  * Android entrypoint module.
  * Owns Compose UI, navigation helpers, `ViewModel`s, and Android-only DI.
  * Uses Jetpack Compose, Material 3, Navigation Compose, and Hilt.

* **`shared`**
  * Kotlin Multiplatform module with `androidTarget()` and `jvm()` targets.
  * Holds cross-platform DTOs, domain models, mappers, repositories, routes, use cases, and simple platform abstractions.
  * This is the main place for reusable business and networking logic.

* **`server`**
  * JVM Ktor/Netty backend.
  * Reuses contracts from `shared`.
  * Should stay aligned with shared routes, request params, and DTOs.

### 2. Dependency Injection with Hilt
Use **Hilt** for Android dependency injection in the current style already present in the project.

* **`@HiltAndroidApp`**: `MyApplication` is the app entrypoint.
* **`@AndroidEntryPoint`**: `MainActivity` is annotated and receives Android graph dependencies.
* **`@HiltViewModel`**: Android `ViewModel`s use constructor injection.
* **Modules**:
  * Hilt modules live under `androidApp/.../di`.
  * `MainModule` currently provides:
    * a configured `HttpClient`
    * shared repositories such as `MyRepository`
    * shared use cases such as `MyUseCase`

Prefer extending the existing Hilt module pattern instead of introducing a second DI framework or ad hoc service locators.

### 3. Shared Layer Responsibilities
The `shared` module already expresses a lightweight but clear architecture. Follow these responsibilities when adding features.

* **`dto/`**
  * Serialized transport models used across client/server boundaries.

* **`domain/`**
  * App-facing models consumed by UI and use cases.
  * Keep these free of Android framework dependencies.

* **`mapper/`**
  * Converts transport DTOs into domain models.

* **`repositories/`**
  * Owns Ktor `HttpClient` usage, request construction, and response-to-domain mapping.

* **`usecases/`**
  * Wraps repository operations in app-facing flows.
  * `BaseUseCase.safeApiCallFlow` is the current error/result wrapper pattern.
  * 
* **`routes/`**
  * Shared route constants and parameter names used by both the client and server.

Do not move Ktor request code into Android `ViewModel`s or Compose UI. Keep network access in shared repositories and expose app-facing operations through shared use cases.

### 4. Android UI and ViewModel Conventions
For Android-specific work, match the existing app structure.

* Build new screens with **Jetpack Compose** and **Material 3**.
* Keep navigation logic under `androidApp/src/main/java/.../navigation`.
* `ViewModel`s should expose **`StateFlow`** and integrate with the current `BaseViewModel` loading/error pattern.
* Keep Android UI code in `androidApp`; do not place Compose or Android framework code in `shared`.

### 5. Client/Server Feature Checklist
- [ ] Add shared route constants and parameter definitions in `shared/routes`.
- [ ] Add transport DTOs in `shared/dto`.
- [ ] Add or extend domain models in `shared/domain` if the UI should consume mapped models.
- [ ] Add mapping logic in `shared/mapper`.
- [ ] Add or extend repository methods in `shared/repositories` for Ktor calls.
- [ ] Add or extend use cases in `shared/usecases` for `Flow<Result<T>>` app-facing operations.
- [ ] Inject new repositories/use cases through Hilt in `androidApp/.../di/MainModule`.
- [ ] Mirror endpoint behavior in `server` using the same shared routes and DTOs.
- [ ] Keep `Config.backendUrl` in mind when changing client/server communication.

### 6. Current Project Notes
- Shared networking currently uses **Ktor** and **kotlinx.serialization**.
- The backend is a **Ktor Netty** server and currently installs `ContentNegotiation` and `CORS`.
- This is still a template, so prefer small, incremental changes that reinforce the current structure rather than introducing a heavy new architecture.
