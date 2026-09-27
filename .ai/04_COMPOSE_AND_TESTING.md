# Compose + Testing Instructions

## Compose

Keep Composables stateless where practical.

Preferred:

```kotlin
@Composable
fun LoginScreen(
    state: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit
)
```

Collect state at the screen/ViewModel boundary:

```kotlin
val state by viewModel.uiState.collectAsStateWithLifecycle()
```

Avoid business logic inside Composables.

Use:
- `remember` for local UI state
- `rememberSaveable` when state should survive recreation
- `LaunchedEffect` for appropriate side effects
- state hoisting for reusable components

Do not put repositories or use cases directly inside UI components.

## Navigation

Keep navigation at the navigation/UI layer.

Do not inject NavController into ViewModel or domain classes.

Pass navigation events/callbacks from screens.

Never place passwords, tokens, or sensitive information into navigation arguments.

## Testing

Add unit tests for:

- UseCases
- ViewModels
- Repository behavior
- Mappers
- important business rules

Test at least:

```text
Initial state
     ↓
Loading
     ↓
Success
```

and:

```text
Initial state
     ↓
Loading
     ↓
Error
```

Prefer behavior-based tests instead of testing private implementation details.

Before completing a feature:

1. Compile.
2. Run relevant unit tests.
3. Run UI tests for important user journeys where appropriate.
4. Fix failures.
