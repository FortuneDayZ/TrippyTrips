# ✈️ TrippyTrips

## 🗺️ Overview

**TrippyTrips** is an Android application designed to help users discover, search for, and save travel destinations and points of interest. The app features secure user authentication, allows users to search for various categories of places using the Google Places API, and stores their favorite spots locally using an SQLite database. Navigation is facilitated through a bottom navigation bar, providing easy access to different sections of the app.

---

## ✨ Features

* 🔐 **Secure User Authentication**:
    * Signup and login functionality.
    * Passwords are salted and hashed using PBKDF2WithHmacSha1 for security.
* 📍 **Location Discovery**:
    * Search for locations (hotels, restaurants, activities) by typing a destination.
    * Utilizes the Google Places API to fetch location data.
    * Category-based searching (e.g., "Hotels in [city]", "Restaurants in [city]", "Activities in [city]").
    * Quick search options for "Beaches nearby," "Landmarks nearby," and "Activities nearby".
* 💾 **Saved Locations**:
    * Users can add interesting places from search results to a personal list.
    * View and manage all saved locations in the "Added Items" section.
    * Remove individual locations from the saved list.
* ⚙️ **Account Management**:
    * Change user password.
    * Log out from the application.
    * Delete user account (which also removes associated saved locations).
    * Option to uninstall the application directly from settings.
* 📱 **User Interface & Navigation**:
    * Intuitive Bottom Navigation Bar for easy access to Home, Search Results, Added Items, and Settings screens.
    * Dynamically generated cards to display search results and saved items.
* 🗄️ **Local Data Storage**:
    * SQLite database to store user credentials and saved location details.
    * Content Providers (`AuthenticateProvider`, `LocationProvider`) to abstract database interactions.
    * `SharedPreferences` for session management (e.g., storing current username) and temporary search queries.

---

## 🛠️ Core Components & Technologies

* **Programming Language**: Java
* **Platform**: Android SDK
* **Architecture**: Android Jetpack Components (Fragments, Navigation Component)
* **Database**: SQLite for local data storage.
* **Data Access**: Content Providers (`AuthenticateProvider`, `LocationProvider`).
* **APIs**: Google Places API for location searching and details.
* **UI**:
    * XML for layouts.
    * Material Design Components (e.g., `BottomNavigationView`, `MaterialButton`).
* **Session Management**: `SharedPreferences`.

---

## 🚀 Installation & Setup

### 1. Clone the Repository
   ```bash
   git clone <your-repository-url>
   cd TrippyTrips
2. Open in Android Studio
Open the project in Android Studio by selecting the cloned project's root directory.

3. Configure Google Places API Key
You need a Google Places API key to fetch location data.
The project attempts to load the API key from BuildConfig.GROUP_PROJECT_GOOGLE_API_KEY. This typically means you'll need to store your API key in a local.properties file in your project's root directory (this file should not be committed to version control).
Add the following line to your local.properties file:
Properties

GROUP_PROJECT_GOOGLE_API_KEY="YOUR_GOOGLE_PLACES_API_KEY"
Ensure your build.gradle file is set up to expose this key through BuildConfig.
4. Build and Run
Let Android Studio sync the project and download dependencies.
Select an emulator or connect a physical Android device.
Click the "Run" button in Android Studio.
📝 TODO
Change Google API Key: Update the API key inside local.properties:
Properties

GROUP_PROJECT_GOOGLE_API_KEY=YOUR_ACTUAL_GOOGLE_PLACES_API_KEY_HERE
🗂️ Project Structure (Key Files/Packages)
TrippyTrips/
├── app/src/main/java/edu/sjsu/android/group4trippytrips/
│   ├── MainActivity.java                # Main Activity hosting fragments
│   ├── fragments/
│   │   ├── WelcomePage.java             # Initial welcome screen
│   │   ├── AuthenticateFragment.java    # Handles user login and signup
│   │   ├── HomeFragment.java            # Main screen for searching locations
│   │   ├── SearchResultsFragment.java   # Displays results from Google Places API
│   │   ├── AddedItemsFragment.java      # Shows user's saved/added locations
│   │   └── SettingsFragment.java        # User account settings and actions
│   ├── db/
│   │   └── AppDB.java                   # SQLiteOpenHelper for database creation and management
│   └── providers/
│       ├── AuthenticateProvider.java    # ContentProvider for user authentication data
│       └── LocationProvider.java        # ContentProvider for saved location data
├── app/src/main/res/
│   ├── layout/                          # XML layout files for UI
│   │   ├── fragment_home.xml
│   │   ├── item_added_card.xml
│   │   └── ... (other layouts)
│   └── navigation/                      # Navigation graph for app flow
└── build.gradle                         # Project and module level Gradle scripts

## ✅ Usage

1.  **Welcome**: The app starts with a `WelcomePage`. Tap "Get Started".
2.  **Authentication**:
    * **Sign Up**: If you're a new user, enter a desired username and password in the sign-up section of the `AuthenticateFragment` and tap "Sign Up".
    * **Log In**: If you have an account, enter your credentials in the login section and tap "Log In".
3.  **Home Screen**: After logging in, you'll be directed to the `HomeFragment`.
    * Select a category (Hotels, Restaurants, Activities).
    * Enter a destination in the search bar and tap "Submit".
    * Alternatively, use quick search options like "Beaches nearby" by tapping on the respective cards.
4.  **Search Results**: The `SearchResultsFragment` will display a list of places based on your query from the Google Places API.
    * Each result card shows the name, rating, and location.
    * Tap the plus icon on a card to add that location to your saved list.
5.  **Added Items**: Navigate to the "Added Items" tab using the bottom navigation bar to view all your saved locations.
    * Tap the checkmark icon on a card to remove it from your list.
6.  **Settings**: Navigate to the "Settings" tab.
    * Change your password.
    * Log out of your account.
    * Delete your account (this will also remove all your saved locations).
    * Uninstall the app.

---

## 🔒💾 Authentication & Data Storage

* **Authentication**:
    * User credentials (username, hashed password, salt) are stored in the local SQLite `login` table, managed by `AppDB.java`.
    * The `AuthenticateProvider.java` handles interactions with this table, including password hashing (PBKDF2WithHmacSha1) and salt generation during signup and verification during login.
    * The logged-in user's session (username) is stored in `SharedPreferences`.
* **Data Storage**:
    * Saved locations (name, address, rating, associated username) are stored in the SQLite `locations` table, also managed by `AppDB.java`.
    * The `LocationProvider.java` facilitates adding, retrieving, and deleting these saved locations for the logged-in user.
    * `SharedPreferences` are also used to temporarily hold search queries between `HomeFragment` and `SearchResultsFragment` and the current username.

---

## 🛠️ Troubleshooting

* **Build Issues**:
    * Ensure Android Studio is updated, and Gradle sync completes successfully.
    * Verify that the Google Places API key is correctly added to `local.properties` and accessible via `BuildConfig`.
* **No Search Results**:
    * Check if the device/emulator has an active internet connection.
    * Ensure the Google Places API key is valid and the API is enabled in your Google Cloud Console project.
    * Verify location permissions if searching for "nearby" places (the app prompts to enable location services if disabled for such searches).
* **Data Not Saving/Deleting**:
    * Check for any errors in the Logcat related to SQLite operations or ContentProvider access.
    * Ensure the `ContentResolver` calls in fragments (`AddedItemsFragment`, `SearchResultsFragment`, `SettingsFragment`) are correctly interacting with the providers.
* **App Crashes**:
    * Examine the Logcat in Android Studio for crash reports and stack traces to identify the source of the error.
📄 License
(You can add your chosen license here, e.g., MIT License)

This project is licensed under the MIT License - see the LICENSE.md file for details (if applicable).
