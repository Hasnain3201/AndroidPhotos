# Android Photos

<p align="center">
  <strong>An Android photo album manager built with Java, AndroidX, Material Components, and persistent local storage.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android">
  <img src="https://img.shields.io/badge/Language-Java-007396?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Build-Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle">
  <img src="https://img.shields.io/badge/API-34-1f6feb?style=for-the-badge" alt="Android API 34">
</p>

## Preview

Screenshots have not been added yet because this workspace does not have an Android SDK or emulator configured. Once the app is launched in Android Studio, capture the album list and album detail screens and place them in `screenshots/` using names such as `screenshots/albums.png` and `screenshots/album-detail.png`.

## Overview

Android Photos is a mobile photo organization app inspired by a desktop JavaFX photo manager. It lets users create albums, rename or delete albums, import photos from the device gallery, and view photo thumbnails inside each album. Album data is saved locally so the library persists across app launches.

The repository now contains the Android Studio project directly at the root, so it can be opened and built without unpacking an archive.

## Key Features

- Create, rename, open, and delete photo albums.
- Import photos from the Android media gallery.
- Display album contents in a RecyclerView with image thumbnails.
- Persist album data with SharedPreferences and Gson serialization.
- Use Parcelable model objects for Android screen navigation.
- Material floating action buttons for common album and photo actions.

## Tech Stack

| Area | Technology |
| --- | --- |
| Language | Java |
| Platform | Android |
| UI | Android XML layouts, RecyclerView, Material Components |
| Storage | SharedPreferences, Gson |
| Build | Gradle wrapper, Android Gradle Plugin |

## Project Structure

```text
.
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/androidphotos03/
│       └── res/
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew
```

## Getting Started

### Prerequisites

- Android Studio
- JDK 17 or newer
- Android SDK Platform 34
- Android emulator or physical device running API 34+

### Installation

```bash
git clone https://github.com/Hasnain3201/AndroidPhotos.git
cd AndroidPhotos
```

Open the folder in Android Studio. Android Studio should create a local `local.properties` file pointing to your SDK installation. Do not commit that file.

### Run

From Android Studio:

1. Sync the Gradle project.
2. Select an API 34+ emulator or device.
3. Run the `app` configuration.

From the command line:

```bash
./gradlew :app:assembleDebug
```

The generated debug APK will be created under `app/build/outputs/apk/debug/` when the Android SDK is configured.

## Demo Flow

1. Launch the app to view the album list.
2. Tap the add button to create a new album.
3. Open an album and upload a photo from the device gallery.
4. Remove photos or manage albums using the action buttons.
5. Close and reopen the app to confirm album data persists.

## Troubleshooting

| Issue | Fix |
| --- | --- |
| `SDK location not found` | Open the project in Android Studio or create `local.properties` with `sdk.dir=/path/to/android/sdk`. |
| Build cannot find Android SDK 34 | Install Android SDK Platform 34 through Android Studio's SDK Manager. |
| Photo picker permission prompt appears | Grant image/media access when Android asks for permission. |

## Future Improvements

- Add polished screenshots after running on an emulator.
- Complete the tag/search screens already represented in the project layouts.
- Add instrumentation tests for album creation and photo import flows.

## Author

**Hasnain Shahzad**

- GitHub: [Hasnain3201](https://github.com/Hasnain3201)
- LinkedIn: [hasnain-shahzad-cs3201](https://www.linkedin.com/in/hasnain-shahzad-cs3201/)
