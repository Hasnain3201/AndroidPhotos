# Android Photos

<p align="center">
  <strong>A native Android photo-album manager for creating albums, importing images, and keeping a lightweight local photo library.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Android-API%2034-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android API 34">
  <img src="https://img.shields.io/badge/Java-AndroidX-007396?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java AndroidX">
  <img src="https://img.shields.io/badge/UI-Material%20Components-6750A4?style=for-the-badge" alt="Material Components">
  <img src="https://img.shields.io/badge/Build-Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle">
</p>

## Preview

Runtime screenshots will be added after an Android SDK and emulator/device are available. This workspace does not include an Android SDK, so the app cannot be rendered or screen-captured here without fabricating images.

Planned capture set:

| Screen | What it shows |
| --- | --- |
| Album Library | Saved albums, create action, and album management controls |
| Album Detail | Imported photo thumbnails and photo-level actions |
| Photo Import | Android gallery picker flow on a real emulator/device |

## Overview

Android Photos is the Android version of a course photo-management project. It preserves the project’s original album-focused workflow while moving the app into a clean Android Studio layout at the repository root. Users can create and manage albums, import photos from device storage, and persist album data locally between launches.

## Highlights

- Album creation, renaming, opening, and deletion.
- Photo import from the Android media gallery.
- RecyclerView-based album and photo lists.
- Local persistence with `SharedPreferences` and Gson.
- Parcelable model objects for passing album/photo data between screens.
- Material floating action buttons for primary actions.

## Tech Stack

| Layer | Tools |
| --- | --- |
| Platform | Android API 34 |
| Language | Java |
| UI | XML layouts, RecyclerView, Material Components |
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
- JDK 17+
- Android SDK Platform 34
- Android emulator or physical device running API 34+

### Run

```bash
git clone https://github.com/Hasnain3201/AndroidPhotos.git
cd AndroidPhotos
./gradlew :app:assembleDebug
```

For the full app experience, open the project in Android Studio, sync Gradle, select an emulator/device, and run the `app` configuration.

## Demo Flow

1. Launch the app to the album library.
2. Create a new album.
3. Open the album and import a photo from the gallery.
4. Remove photos or manage albums from the action controls.
5. Restart the app and confirm saved album data is restored.

## Troubleshooting

| Issue | Fix |
| --- | --- |
| `SDK location not found` | Open the project in Android Studio or create `local.properties` with `sdk.dir=/path/to/android/sdk`. |
| SDK 34 missing | Install Android SDK Platform 34 from Android Studio's SDK Manager. |
| Media permission denied | Grant image/media access when Android prompts. |

## Roadmap

- Capture emulator screenshots once Android runtime setup is available.
- Complete the tag/search screens represented by the current layouts.
- Add focused instrumentation tests for album creation and photo import.

## Author

**Hasnain Shahzad**

- GitHub: [Hasnain3201](https://github.com/Hasnain3201)
- LinkedIn: [hasnain-shahzad-cs3201](https://www.linkedin.com/in/hasnain-shahzad-cs3201/)
