# DataStore + Hilt + ViewModel Instructions

## DataStore

Use Preferences DataStore for lightweight local values such as:

- login state
- user preferences
- small configuration
- feature flags

Keep DataStore access inside a dedicated class such as:

```kotlin
class AppDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
)
```

Expose typed methods/Flows rather than spreading DataStore access throughout the app.

Do not use DataStore as a replacement for a relational database.

Do not store passwords or sensitive credentials as plain text.

## Hilt

Prefer constructor injection:

```kotlin
class LoginRepositoryImpl @Inject constructor(
    private val api: LoginApi,
    private val dataStore: AppDataStore
) : LoginRepository
```

Use modules for third-party classes and interface bindings.

Avoid making every dependency a singleton unnecessarily.

## ViewModel

Use:

```kotlin
private val _uiState = MutableStateFlow(LoginUiState())
val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
```

Use:

```kotlin
viewModelScope.launch {
    try {
        // call use case
    } catch (e: Exception) {
        // update error state
    }
}
```

Do not catch exceptions outside `launch` expecting to catch child coroutine failures.

Do not swallow `CancellationException`.

ViewModel responsibilities:

- screen state
- UI events
- calling use cases
- transforming domain data to UI state

ViewModel must not:

- access Views
- hold Activity/Fragment references
- use NavController directly
- call Retrofit directly
- contain Compose rendering code
