# Peshy - Product Listing App

A modern Android application built with Jetpack Compose that demonstrates best practices in Android development. The app fetches and displays products from the DummyJSON API, implementing offline caching, search functionality, and a clean architecture approach.

## Features

- **Product Listing**: Displays a list of products with images, titles, and prices
- **Search Functionality**: Real-time product filtering based on search input
- **Offline Support**: Local caching using Room database
- **Error Handling**: Graceful error states and loading indicators
- **Material Design 3**: Modern UI following Material Design guidelines

## Architecture & Technical Approach

### Clean Architecture

The project follows Clean Architecture principles with clear separation of concerns:

- **Data Layer**

  - Repository pattern for data management
  - Room database for local caching
  - Retrofit for network calls. Differentiated network and retrofit to ensure separation of concern
  - Resource wrapper class for handling data states

- **Domain Layer**

  - Use cases (implicit through repository)
  - Model classes
  - Business logic

- **Presentation Layer**
  - MVVM pattern with ViewModels
  - Jetpack Compose UI
  - State management using StateFlow

### Key Technologies

- **Jetpack Compose**: Modern UI toolkit for native Android UI
- **Hilt**: Dependency injection
- **Kotlin Coroutines & Flow**: Asynchronous programming
- **Room**: Local database
- **Retrofit**: Network calls
- **Material 3**: Design system
- **Glide**: Image loading

### State Management

- Using `StateFlow` for reactive state management
- Handling UI states (Loading, Success, Error) through sealed classes
- Lifecycle-aware state collection using `collectAsStateWithLifecycle`

### Data Flow

1. UI requests data through ViewModel
2. ViewModel interacts with Repository
3. Repository manages data from local (Room) and remote (Retrofit) sources
4. Data changes are observed using Kotlin Flow
5. UI updates reactively based on state changes

## Project Structure

## Dependencies
Use of the gradle versions catalog for scalable dependency management
```gradle
libs.version.toml

```

## Future Improvements

- Improve product details screen
- Add unit tests and UI tests
- Implement pagination for large product lists
- Add product categories and filtering
- Add animations and transitions
- Implement dark theme support

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run the app

## Requirements

- Android Studio Arctic Fox or newer
- Minimum SDK: 26 (Android 8.0)
- Kotlin 2.0.21 or newer

