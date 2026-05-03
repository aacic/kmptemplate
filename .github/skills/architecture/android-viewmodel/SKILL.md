---
name: android-viewmodel
description: Guidance for ViewModels in this project, including the BaseViewModel pattern, use case integration, and StateFlow-based UI state management.
---

# Android ViewModel & State Management (Project-Specific)

## Instructions

ViewModels in this project extend `BaseViewModel` to inherit loading and error state management, then delegate business logic to shared use cases.

### 1. Extending BaseViewModel
All Android `ViewModel`s should extend `BaseViewModel` from `androidApp/src/main/java/.../viewmodel/BaseViewModel.kt`.

* `BaseViewModel` exposes two `StateFlow`s:
  * `isLoading: StateFlow<Boolean>` — auto-managed during use case invocation.
  * `error: StateFlow<String?>` — captures error messages from failed operations.

* `BaseViewModel.invokeUseCase()` handles:
  * Setting `isLoading = true` before execution.
  * Collecting the `Flow<Result<T>>` from a shared use case.
  * Emitting results to a provided `FlowCollector<T?>`.
  * Setting `isLoading = false` and calling `postError()` on failure.

### 2. Defining ViewModel State
Each `ViewModel` should define **domain-specific** `StateFlow`s for the screen's data.

```kotlin
@HiltViewModel
class MyViewModel @Inject constructor(
    private val myUseCase: MyUseCase
) : BaseViewModel() {

    private val myFlow = MutableStateFlow<MyDomainObject?>(null)
    val myDomainObject: StateFlow<MyDomainObject?> = myFlow

    fun invokeMyUseCase() {
        this.invokeUseCase(myFlow, "My error") {
            myUseCase.invoke("myParamName")
        }
    }
}
```

* Expose **only private `MutableStateFlow`s to public read-only `StateFlow`s**.
* Each operation should have corresponding state (e.g., `myFlow` for data, `isLoading` and `error` for metadata).

### 3. Collecting in Compose
Use `collectAsState()` (or `collectAsStateWithLifecycle()` for lifecycle-safety) in your Composables:

```kotlin
@Composable
fun HomeScreen(myViewModel: MyViewModel) {
    val myDomainObject by myViewModel.myDomainObject.collectAsState()
    val isInProgress by myViewModel.isLoading.collectAsState()
    val error by myViewModel.error.collectAsState()

    if (error != null) {
        // Show error snackbar or UI
        myViewModel.clearError()
    }

    // Render UI based on state
}
```

### 4. Use Case Integration
* Inject shared use cases (from `shared/src/commonMain/kotlin/.../usecases`) into the `ViewModel` constructor.
* Use the `@HiltViewModel` annotation and Hilt constructor injection.
* Call `invokeUseCase()` with:
  * The target `StateFlow<T?>` to collect results into.
  * An error message string.
  * A lambda that returns `Flow<Result<T>>` from the shared use case.

Do not construct `HttpClient` or access repositories directly inside a `ViewModel`. Delegate all network logic to shared use cases.

### 5. Scope and Lifecycle
* Use `viewModelScope` for all coroutines (handled by `BaseViewModel.invokeUseCase`).
* `BaseViewModel` automatically manages the `isLoading` and `error` lifecycle during use case calls.
* Call `clearError()` to reset error state after displaying it to the user.
