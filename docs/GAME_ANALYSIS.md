# Stronghold Crusader HD & Extreme HD - Game Package & Technical Analysis Report

**Author / Team:** MineswordTeam Porting & Compatibility Division
**Project:** Stronghold Crusader Android Port (`Stronghold_c_mobile`)
**Date:** September 2026

---

## 1. Executive Summary

This report documents the technical analysis of the Stronghold Crusader HD and Stronghold Crusader Extreme HD game packages for Android porting and compatibility layer design.

Stronghold Crusader (originally released by Firefly Studios in 2002) and its HD re-releases (2012) use a custom 2D isometric rendering engine written in C++ targeting Win32 (x86 32-bit), DirectDraw (DirectX 7/8 2D surface interface), DirectSound, and Win32 GDI/Message loop APIs.

---

## 2. Executable & Architecture Identification

### 2.1 Game Executables
| Game Edition | Primary Executable Filename | Architecture | Subsystem | Target API |
| :--- | :--- | :--- | :--- | :--- |
| **Stronghold Crusader HD** | `Stronghold Crusader.exe` | x86 (32-bit PE32) | Win32 GUI | DirectDraw / GDI / DirectSound |
| **Stronghold Crusader Extreme HD** | `Stronghold_Crusader_Extreme.exe` | x86 (32-bit PE32) | Win32 GUI | DirectDraw / GDI / DirectSound |

### 2.2 Shared Core Dependencies & DLLs
- `binkw32.dll` - RAD Game Tools Bink Video decoder (intro videos & cutscenes).
- `mss32.dll` - Miles Sound System 32-bit audio decoder (background music & sound FX).
- `ddraw.dll` - Microsoft DirectDraw 7 library for 2D surface blitting, palette management, and resolution switching.
- `dsound.dll` - Microsoft DirectSound for multi-channel digital audio playback.
- `dinput.dll` - DirectInput / Win32 mouse & keyboard messaging for mouse cursor locking, right-click, and hotkeys.
- `wsock32.dll` / `ws2_32.dll` - DirectPlay / Win32 Sockets for LAN / IPX multiplayer lobby management.

---

## 3. Game Resource Structure & Required Folders

The game installation directory contains the following critical resource folders and data containers:
- `fx/` - Sound effects (.wav / .raw audio samples).
- `gm/` - Game map files (.map) and campaign mission definitions.
- `bink/` - Video cutscenes (.bik format).
- `music/` - Background soundtrack (.mp3 / .wav / Miles Sound System streams).
- `crusader.tgx` / `crusader.v00` - TGX/V00 custom Firefly graphic tile and sprite archives.
- `land/` - Terrain textures and isometric grid height maps.

---

## 4. Game Configuration, Save System & Registry Requirements

### 4.1 Save Game Location
- **Original Windows Default:** `%USERPROFILE%\Documents\Stronghold Crusader\Saves\` or `C:\Program Files (x86)\Firefly Studios\Stronghold Crusader\Saves\`
- **Android Target Location:** Internal storage path `/data/data/com.mineswordteam.strongholdcrusader/files/saves/` mapped via JNI Virtual File System (VFS) to prevent data loss on application updates.

### 4.2 Resolution & Display Engine
- Original resolutions: `800x600`, `1024x768` (4:3 aspect ratio).
- HD Edition resolutions: Supports arbitrary display resolutions including `1280x720`, `1920x1080`, `2560x1440` by expanding the visible isometric map viewport while keeping 2D sprite sizes constant.
- Android Strategy: SDL2 surface scaling with aspect ratio preservation (letterboxing / pillarboxing or immersive widescreen scaling) to ensure phone and tablet compatibility.

### 4.3 Win32 Registry Dependencies
- Modern HD standalone versions check `HKLM\Software\Firefly Studios\Stronghold Crusader\` for `InstallPath` and `Language` strings, but gracefully fall back to `GetModuleFileName()` working directory if registry keys are absent.

---

## 5. Android Compatibility & Runtime Strategy

### 5.1 Architecture Decision
To achieve maximum performance, compatibility, and low touch latency without rewriting Firefly's complex isometric C++ simulation engine from scratch:
1. **Native Compatibility Layer (JNI/C++/SDL2):** An SDL2-based Win32 / DirectDraw emulation wrapper (`libstronghold_runtime.so`) compiled for `armeabi-v7a` and `arm64-v8a` (with x86/x86_64 support via Box86 / Wine runtime bridges).
2. **Android UI & Launcher:** Kotlin-based `LauncherActivity` and context-sensitive HUD (`OverlayControlsView`) providing smooth medieval-themed menu interaction, auto-detection of game executables, dynamic camera panning touch controls, and pinch-to-zoom mouse wheel mapping.

---

## 6. Summary of Discovered Executables & Compatibility Features Matrix
- **Stronghold Crusader HD Supported:** YES (`Stronghold Crusader.exe`)
- **Stronghold Crusader Extreme HD Supported:** YES (`Stronghold_Crusader_Extreme.exe`)
- **Touch Camera Edge Panning:** Implemented via touch drag gestures.
- **Pinch Zoom:** Implemented via two-finger multitouch scaling mapped to Win32 mouse wheel.
- **Pause & Back Button:** Handled intelligently via custom Android pause overlay dialog.
