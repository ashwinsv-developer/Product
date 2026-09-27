# Network + Repository Instructions

Use Retrofit and OkHttp for API communication.

## Retrofit

Keep API interfaces limited to HTTP definitions.

Example:

```kotlin
interface UserApi {
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}
```

## Repository

The RepositoryImpl is responsible for:

- calling APIs
- reading/writing local data
- mapping DTOs
- handling data-layer errors
- coordinating remote/local sources

Example:

```kotlin
interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
}
```

```kotlin
class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
) : UserRepository {

    override suspend fun getUsers(): Result<List<User>> {
        return runCatching {
            api.getUsers().map { it.toDomain() }
        }
    }
}
```

## Rules

- ViewModel must never call Retrofit directly.
- Do not expose DTOs to UI.
- Do not put business logic in Retrofit interfaces.
- Handle network failures explicitly.
- Do not blindly retry every request.
- Never log access tokens, passwords, OTPs, or sensitive payment data.
- Never disable TLS/certificate validation.
