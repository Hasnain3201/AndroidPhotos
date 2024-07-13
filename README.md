# Android Photos Application

## Overview

- This project is an Android application that allows the storage and management of photos in one or more albums.
- The application is a port of a JavaFX-based Photos project to Android, built using Android Studio with Java.
- The app provides a user-friendly interface for organizing and viewing photos on a smartphone.

## Features

- **Home Screen**: Displays a list of albums and persists album data across multiple app launches.
- **Album Management**: Allows users to open, create, delete, and rename albums.
- **Photo Management**: Users can add, remove, and display photos within albums. Includes a slideshow feature for viewing photos.

## Getting Started

### Prerequisites

- Android Studio installed on your system.
- Nexus 4 (4.7 inch, 768x1280, xhdpi) device emulator set up in Android Studio.

### Installation

- Clone the repository:
  ```sh
  git clone https://github.com/your-username/android-photos-app.git
  cd android-photos-app

- Open the project in Android Studio.

- Ensure the project configuration matches the following:
  - **compileSdkVersion**: 34
  - **minSdkVersion**: 34
  - **targetSdkVersion**: 34

- Build and run the app on the Nexus 4 emulator.

## Application Usage

- **Home Screen**: On launching the app, the home screen displays a list of albums.

- **Album Management**:
  - **Open Album**: Tap on an album to view its contents.
  - **Create Album**: Use the create button to add a new album.
  - **Delete Album**: Long press on an album to delete it.
  - **Rename Album**: Long press on an album and select rename.

- **Photo Management**:
  - **Add Photo**: Open an album and use the add button to import a photo from the device.
  - **Remove Photo**: Long press on a photo to remove it from the album.
  - **Display Photo**: Tap on a photo to view it in full screen.
  - **Slideshow**: Use the slideshow feature to navigate through photos in an album.

## Development Details

- **Packages**: The project is organized into packages for the model, view, and controller.
- **Main Class**: `MainActivity` with the main method to launch the application.
- **Data Storage**: Uses Android's storage mechanisms to persist album and photo data.
- **UI Design**: The GUI is designed using Android XML layouts.

## Authors

- Hasnain Shahzad
- Peter Sestito
