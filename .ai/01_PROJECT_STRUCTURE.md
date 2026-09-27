# Project Structure

Use a scalable feature-based Clean Architecture structure.

```text
app/
├── core/
│   ├── network/
│   ├── datastore/
│   ├── navigation/
│   ├── common/
│   └── designsystem/
│
└── feature/
    └── <feature>/
        ├── data/
        │   ├── remote/
        │   ├── local/
        │   ├── mapper/
        │   └── repository/
        │
        ├── domain/
        │   ├── model/
        │   ├── repository/
        │   └── usecase/
        │
        └── presentation/
            ├── screen/
            ├── component/
            ├── mapper/
            └── viewmodel/
```

Example:

```text
feature/login/
├── data/
│   ├── remote/LoginApi.kt
│   ├── remote/LoginResponseDto.kt
│   ├── mapper/LoginMapper.kt
│   └── repository/LoginRepositoryImpl.kt
│
├── domain/
│   ├── model/User.kt
│   ├── repository/LoginRepository.kt
│   └── usecase/LoginUseCase.kt
│
└── presentation/
    ├── screen/LoginScreen.kt
    └── viewmodel/LoginViewModel.kt
```

## Dependency Direction

```text
Presentation -> Domain
Data -> Domain
Domain -> Nothing application-specific
```

Never import `RepositoryImpl`, Retrofit DTOs, or DataStore classes into the domain layer.
