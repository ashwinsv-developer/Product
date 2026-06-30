# Product App

A modern Android application demonstrating product listing and detail views using the latest Jetpack libraries and Clean Architecture principles.

## 🚀 Features

- **Product Listing**: View a list of products with thumbnails and basic info.
- **Product Details**: Detailed view of products including an image gallery.
- **Image Sanitization**: Automatic mapping of legacy CDN URLs to current working paths.
- **Modern UI**: Built entirely with Jetpack Compose and Material 3.

## 🛠 Tech Stack & Libraries

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Architecture**: MVVM (Model-View-ViewModel) with Clean Architecture patterns.
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) & [Landscapist](https://github.com/skydoves/landscapist)
- **JSON Serialization**: [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- **Local Storage**: [Room](https://developer.android.com/training/data-storage/room)
- **Pagination**: [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data)
- **Asynchronous Flow**: Kotlin Coroutines & Flow

## 🏗 Project Structure

```text
com.product
├── data            # Data layer (Remote API, Repository implementation, Models)
├── di              # Hilt Modules for Dependency Injection
├── navigation      # Compose Navigation setup (Screens & NavHost)
├── ui              # Presentation layer
│   ├── components  # Reusable UI components
│   ├── detail      # Product Detail screen
│   └── home        # Product Listing screen
└── ProductApp.kt   # Application class
```

## ⚙️ Setup Instructions

1. **Prerequisites**:
   - Android Studio Ladybug (or newer)
   - JDK 17
   - Android SDK 35

2. **Installation**:
   - Clone this repository.
   - Open the project in Android Studio.
   - Wait for Gradle sync to complete.

3. **Running the App**:
   - Select the `app` configuration.
   - Choose an emulator or a physical device (API 24+).
   - Click **Run**.

## 🔧 Troubleshooting: Image Loading (404 Errors)

The app interacts with `api.freeapi.app`, which sometimes returns legacy image URLs from `cdn.dummyjson.com/product-images/`. These URLs are known to return HTTP 404.

The project includes a sanitization layer in `ProductRepositoryImpl.kt` that maps these legacy paths to the modern structure. If images fail to load:
1. Verify the mapping logic in `fixUrl()`.
2. Check Logcat for network diagnostics (using `Coil` network logs).

## 🧪 Testing

The project includes unit and instrumentation tests:
- **Unit Tests**: MockK, Coroutines Test, and Turbine for Flow testing.
- **UI Tests**: Compose UI Test and Espresso.

Run tests via:
```bash
./gradlew test
./gradlew connectedAndroidTest
```
