# Kotlin Multiplatform Template

This template is designed to help set up a Kotlin Multiplatform project. It currently includes the following modules:

- **Android Module**: This module contains the Android-specific code and resources.
- **Shared Module**: This module contains the code and resources that are shared across different platforms and server.
- **Server Module**: This module contains the server-side code.

TODO Add IOS and Web module

## Key Components

### Client-Server Communication

Client-server communication is facilitated by [Ktor](https://ktor.io/), a framework for building asynchronous servers and clients. Ktor simplifies the process of handling HTTP requests and responses, making it ideal for communication between client and server in this multiplatform setup.

### Dependency Injection

Dependency injection is managed with [Hilt](https://developer.android.com/training/dependency-injection/hilt-android), an extension of the Dagger library for Android. Hilt simplifies dependency injection in Android apps by generating code and providing compile-time guarantees for dependencies.

## Dependencies

### Android UI

- **Compose UI**: Android's modern UI toolkit for building native UIs.
- **Compose Material3**: Material Design components for Jetpack Compose.
- **Activity Compose**: Allows integration of Jetpack Compose UI within Android activities.
- **Lifecycle Components**: Provides classes to manage activity and fragment lifecycles.

### Networking

- **Ktor Client**: Core library for making HTTP requests.
- **Ktor Serialization**: Adds support for serializing and deserializing data using Kotlin serialization.
- **Ktor Content Negotiation**: Provides support for content negotiation in Ktor.
- **Ktor Server**: Core library for building servers with Ktor.
- **Ktor Netty Server**: Implementation of the Ktor server using Netty.
- **Ktor CORS**: Adds Cross-Origin Resource Sharing (CORS) support to Ktor.

### Google Services

- **Google Maps**: Integration for displaying maps in Android apps.
- **Maps Compose**: Library for integrating Google Maps with Jetpack Compose UI.
- **Google Authentication**: Library for integrating Google sign-in and authentication.
- **Google Places**: Provides access to Google Places API for location-based services.

### Database

- **Exposed**: Kotlin SQL framework for working with databases.
- **PostgreSQL Driver**: JDBC driver for PostgreSQL database.
- **HikariCP**: High-performance JDBC connection pool.

### Logging

- **Logback Classic**: Logging framework for Java applications.

### Serialization

- **Kotlinx Datetime**: Library for working with date and time in Kotlin.

## Plugins

- **Android Application**: Plugin for building Android applications.
- **Android Library**: Plugin for building Android libraries.
- **Kotlin Android**: Plugin for Kotlin on Android.
- **Kotlin Multiplatform**: Plugin for Kotlin multiplatform projects.
- **Kotlin Kapt**: Kotlin annotation processor.
- **Dagger Hilt**: Plugin for integrating Hilt into Android projects.
- **Kotlin Serialization**: Plugin for Kotlin serialization.
- **Shadow Plugin**: Plugin for creating fat/uber JARs or WARs.

## Versioning

The versions of the libraries and tools used in this project are defined in the TOML file: libs.versions.toml

## Getting Started

To get started with this template, clone the repository and import the project into your Android Studio. 
