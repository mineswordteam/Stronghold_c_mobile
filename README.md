# Stronghold Crusader HD & Extreme HD - Android Edition

**Developed by MineswordTeam**

Welcome to the official Android porting and compatibility-runtime project for **Stronghold Crusader HD** and **Stronghold Crusader Extreme HD**.

---

## 🏰 Project Overview

This project provides a complete native Android application and compatibility runtime layer (`libstronghold_runtime.so`) designed to run the original Windows executables (`Stronghold Crusader.exe` and `Stronghold_Crusader_Extreme.exe`) on modern Android smartphones and tablets.

### Key Features
- **MineswordTeam Launcher:** A medieval-inspired UI launcher with dynamic game executable auto-detection and game file status checks.
- **Touch Camera Panning:** Single-finger drag gestures translate to smooth edge-scrolling and map panning.
- **Multitouch Pinch Zoom:** Two-finger pinch gestures translate directly to Win32 mouse wheel zoom events.
- **Context HUD Overlay:** Floating, non-intrusive action buttons (`SELECT`, `ACTION`, `DRAG`) for unit commands, building placement, and box selections.
- **Smart Back Button Handling:** Intercepts the hardware Android Back button to open an in-game pause overlay dialog without exiting or losing game progress.
- **Virtual Storage Mapping:** Redirects save games to `/data/data/com.mineswordteam.strongholdcrusader/files/saves/` to ensure persistent save files across app updates.

---

## 📱 Supported Game Versions
1. **Stronghold Crusader HD** (`Stronghold Crusader.exe`)
2. **Stronghold Crusader Extreme HD** (`Stronghold_Crusader_Extreme.exe`)

---

## 🛠 Architecture & Technical Stack
- **Language:** Kotlin 1.9, C++17
- **Target Android SDK:** 34 (Android 14) / Min SDK 24 (Android 7.0)
- **Rendering:** OpenGL ES 2.0 via JNI & Android `GLSurfaceView`
- **Native Build:** CMake 3.22.1 & Android NDK 25.1
- **CI/CD:** GitHub Actions with JDK 21 and automated GitHub Release publishing.

---

## 🚀 Installation & Setup Guide

1. Download and install the latest `Stronghold Crusader Android v1.0.0` APK from the [GitHub Releases](../../releases) section.
2. Launch the **Stronghold Crusader HD** app.
3. Place your original game files (`Stronghold Crusader.exe`, `Stronghold_Crusader_Extreme.exe`, and folders `fx/`, `gm/`, `bink/`, `music/`) into the app's internal game storage folder:
   ```
   /data/data/com.mineswordteam.strongholdcrusader/files/game/
   ```
4. Re-open the launcher and select **PLAY HD** or **PLAY EXTREME**.

---

## 💻 Building from Source

To build the project locally on Linux/macOS:
```bash
./gradlew assembleRelease
```
The generated APK will be located at:
`android/app/build/outputs/apk/release/app-release-unsigned.apk`

To run unit tests:
```bash
./gradlew test
```

---

## 🤝 Credits & Licensing
- **Original Game Content & IP:** Firefly Studios
- **Android Port & Runtime Compatibility Layer:** MineswordTeam
