# Bookly 📚

**Bookly** is an Android application designed to help book lovers discover, manage, and rent books easily. It allows users to register, log in via Firebase, explore a variety of books and discover daily stories to inspire their reading journey. The app also integrates Google Maps to show available book stores near the user.

---

## Features 🚀

- **User Registration & Authentication**: Secure login and sign-up with Firebase Authentication.
- **Profile Management**: Users can manage their profiles, including their personal information and preferences.
- **Book Dashboard**: Fetch and display books along with daily stories and recommendations.
- **Search**: Easily search books by title, author, or genre.
- **Google Maps Integration**: View nearby stores that provide the books.
- **Beautiful UI**: Featuring gradients and modern UI components to enhance user experience.

---

## Architecture 📐

- **MVVM Architecture**: The app follows the Model-View-ViewModel (MVVM) design pattern, providing separation of concerns and promoting a clean architecture.
- **Navigation**: Managed via the Navigation Graph to handle seamless transitions between screens and fragments.

---

## Technologies & Libraries 🛠

This app leverages several Android libraries to provide a rich and responsive experience:

### Core Libraries:
- **Glide**: For image loading and caching.
- **Firebase**: For Authentication, Firestore, and Storage.
- **Google Maps API**: For displaying nearby book stores.
- **Paging**: Efficiently load large datasets for books with pagination.
- **Hilt**: For dependency injection to simplify class dependencies.

### Navigation & UI:
- **Material Design**: For modern and beautiful UI components.
- **AndroidX Navigation**: To handle screen transitions efficiently.
- **Core KTX**: To enhance the Android SDK with Kotlin extensions.

---
