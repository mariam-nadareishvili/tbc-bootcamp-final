
# Bookli 📚

**Bookli** is a modern Android app built with Jetpack Compose and Clean Architecture. It allows users to discover, read, organize, and review books. With a fully modularized structure, reactive UI, and robust architecture, Bookli is built for scalability and clarity.

---

## 🏠 Features

- ✨ Onboarding experience with interactive slides
- 🔑 Secure login & registration
- 📖 Book discovery and browsing by category
- 🔍 Search functionality with real-time results
- 🎓 Bookshelf to organize read/wishlist books
- ⭐ Book detail & review screens
- 👤 User profile with preferences
- ⏳ Custom loader animations with Lottie
- 🌐 Language & dark mode settings

---

## 🚀 Modular Architecture

```bash
:app
:core:common           // Shared constants, helpers, UiText, resource files  
:core:data             // Repositories, data sources, DTOs, API setup  
:core:domain           // Use cases, domain models, repository interfaces  
:core:di               // Hilt dependency injection modules  
:core:ui               // Shared Composables, theme, UI models & mappers  

:feature:intro  
:feature:login  
:feature:register  
:feature:home  
:feature:bookshelf  
:feature:bookshelf_details  
:feature:details  
:feature:search  
:feature:review  
:feature:read  
:feature:profile  
:feature:splash
```

---

## 📊 Tech Stack

### ✨ UI
- [Jetpack Compose](https://developer.android.com/jetpack/compose) – Declarative UI Toolkit
- [Material3](https://m3.material.io/) – Modern material design components
- [Lottie Compose](https://github.com/airbnb/lottie-android) – For animations
- [Accompanist](https://google.github.io/accompanist/) – Pager, permissions, system UI
- [YCharts](https://github.com/yml-org/ycharts) – For beautiful charts

### ⚙️ Architecture & DI
- MVI Architecture – Unidirectional state & event flow
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) – Dependency injection
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) – Preferences & config

### 🔐 Networking & Backend
- [Retrofit](https://square.github.io/retrofit/) + Kotlinx Serialization
- [OkHttp](https://square.github.io/okhttp/) + Logging Interceptor
- [Firebase Auth](https://firebase.google.com/)

### 🤖 Async + Data
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- [Kotlinx Datetime](https://github.com/Kotlin/kotlinx-datetime)

---

## 🛠️ Setup & Build

1. Clone the repo
2. Open in Android Studio Hedgehog or newer
3. Sync Gradle (uses version catalog)
4. Run the app on API 24+

> No special setup required. Firebase works out of the box.

---

## 🎨 Design & Patterns

- Clean Architecture: **domain > data > presentation > UI**
- **MVI pattern** for single-direction state & action flow
- Modularization for feature isolation and scalability
- `UiText` system for localization and translation
- Lottie animations for polished user experience

---

> Built with passion by Mariam Nadareishvili ❤️

---

_“A reader lives a thousand lives before he dies . . . The man who never reads lives only one.”_
