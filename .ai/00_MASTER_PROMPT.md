# Android Project Master Instructions

Build and maintain this Android application using:

- Kotlin
- Jetpack Compose
- MVVM
- Clean Architecture
- Repository Pattern
- Hilt
- Retrofit + OkHttp
- Coroutines + Flow
- DataStore
- Unit tests

## Architecture

Follow:

UI -> ViewModel -> UseCase -> Repository -> RepositoryImpl -> Data Source

Keep responsibilities separate.

### UI
- Compose only.
- No business logic.
- No direct Retrofit/DataStore calls.
- Collect ViewModel StateFlow using lifecycle-aware APIs.

### ViewModel
- Own screen state.
- Call use cases.
- Use viewModelScope.
- Expose immutable StateFlow.
- Do not reference Activity, Fragment, View, NavController, Retrofit, or DataStore directly.

### Domain
- Contains business logic.
- Contains models, repository interfaces, and use cases.
- Avoid Android framework dependencies.

### Data
- Contains Retrofit APIs, DTOs, DataStore access, mappers, and RepositoryImpl.
- Convert DTO/data models into domain models before returning to domain.

## General Rules

1. Prefer constructor injection.
2. Use Hilt for dependency injection.
3. Do not hardcode secrets.
4. Use HTTPS.
5. Handle loading, success, and error states.
6. Handle coroutine cancellation correctly.
7. Do not put business logic inside Composables.
8. Do not call repositories directly from UI.
9. Do not call Retrofit directly from ViewModel.
10. Do not modify unrelated files.
11. Reuse existing project code before creating new abstractions.
12. Keep implementations simple and maintainable.

Before finishing any task:
- Build the project.
- Run relevant tests.
- Fix compilation errors.
- Summarize changed files.
