# ✈️ TrippyTrips

## 🗺️ Overview

**TrippyTrips** is an Android application designed to help users discover, search for, and save travel destinations and points of interest. The app features secure user authentication, location discovery through the Google Places API, and a polished user interface for managing favorite locations.

---

## ✨ Features

- 🔐 **Secure User Authentication**
  - User signup/login with passwords hashed using PBKDF2WithHmacSha1.
- 📍 **Location Discovery**
  - Search places by typing destinations.
  - Category-based filtering (Hotels, Restaurants, Activities).
  - One-tap quick searches like "Beaches nearby" or "Landmarks nearby".
- 💾 **Saved Locations**
  - Save favorite places to your list.
  - View/manage saved locations in "Added Items".
  - Remove saved places as needed.
- ⚙️ **Account Management**
  - Change password, log out, delete account.
  - Option to uninstall app from Settings.
- 📱 **User Interface**
  - Bottom navigation bar for intuitive access to key sections.
  - Cards to visually display locations and saved items.
- 🗄️ **Local Data Storage**
  - SQLite for storing credentials and saved places.
  - `SharedPreferences` for session and state data.
  - Content Providers for abstracted DB interaction.

---

## 🖼️ Screenshots
### 1. WelcomePage
Shows short description of the app.

<img src="https://github.com/FortuneDayZ/TrippyTrips/blob/main/Screenshots/Welcome.jpg?raw=true" width="250" />

### 2. Login & Sign Up Screen
Users can log in or create an account using a simple and intuitive form layout.

![Image Alt](https://github.com/FortuneDayZ/TrippyTrips/blob/main/Screenshots/Login.jpg?raw=true)

### 3. Category Search and Location Search
Main navigation screen where users can search and browse by category such as Hotels, Dining, and Activities.

![Image Alt](https://github.com/FortuneDayZ/TrippyTrips/blob/main/Screenshots/Search.jpg?raw=true)

### 4. Search Results Screen
Displays a list of relevant places based on user queries, with ratings and add-to-saved buttons.

![Image Alt](https://github.com/FortuneDayZ/TrippyTrips/blob/main/Screenshots/SearchResults.jpg?raw=true)

### 5. Saved Items Screen
Shows the list of user-saved places with key details like address and ratings.

![Image Alt](https://github.com/FortuneDayZ/TrippyTrips/blob/main/Screenshots/SavedResults.jpg?raw=true)

### 6. Settings Screen
Allows users to change their password, uninstall the app, or delete their account securely.

![Image Alt](https://github.com/FortuneDayZ/TrippyTrips/blob/main/Screenshots/Settings.jpg?raw=true)

---

## 🛠️ Technologies

- **Language**: Java
- **Platform**: Android SDK
- **Database**: SQLite via `AppDB.java`
- **Storage Layer**: `AuthenticateProvider`, `LocationProvider`
- **Session**: `SharedPreferences`
- **UI**: XML layouts, Material Design Components
- **API**: Google Places API

---

## 🚀 Installation

### 1. Clone the Project

```bash
git clone https://github.com/FortuneDayZ/TrippyTrips.git
cd TrippyTrips
```

### 2. Open in Android Studio

- Select the project directory to open in Android Studio.

### 3. Set Up Google Places API Key

- In your project root, create (or update) a `local.properties` file:
  ```properties
  GROUP_PROJECT_GOOGLE_API_KEY=YOUR_GOOGLE_PLACES_API_KEY_HERE
  ```
- Ensure `build.gradle` is configured to pass this key to `BuildConfig`.

### 4. Build & Run

- Let Android Studio sync the project and dependencies.
- Choose an emulator or real device and click **Run**.

---

## 🗂️ Project Structure

```
TrippyTrips/
├── MainActivity.java
├── fragments/
│   ├── WelcomePage.java
│   ├── AuthenticateFragment.java
│   ├── HomeFragment.java
│   ├── SearchResultsFragment.java
│   ├── AddedItemsFragment.java
│   └── SettingsFragment.java
├── db/
│   └── AppDB.java
├── providers/
│   ├── AuthenticateProvider.java
│   └── LocationProvider.java
├── res/layout/
├── res/navigation/
└── build.gradle
```

---

## ✅ Usage Guide

1. **Start App** → Welcome Page.
2. **Sign Up/Login** → AuthenticateFragment.
3. **Home** → Choose a category + enter destination or use quick searches.
4. **Search Results** → Tap ➕ to save a location.
5. **Added Items** → View/remove saved places.
6. **Settings** → Manage account: change password, logout, delete, uninstall.

---

## 🔒 Data & Security

- User credentials are salted and hashed.
- `SharedPreferences` store session data.
- SQLite holds user and location info.
- `AuthenticateProvider`/`LocationProvider` abstract DB interactions.

---

## 🛠️ Troubleshooting

- **No Results**: Check internet, API key, and location permissions.
- **Build Fails**: Ensure valid API key and Gradle sync.
- **Crashes**: Use Logcat to trace and fix.
- **Storage Issues**: Check ContentProvider and SQLite access.

---

## 📄 License

This project is licensed under the MIT License — see `LICENSE.md` for details.
