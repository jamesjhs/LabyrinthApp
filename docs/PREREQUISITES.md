# Prerequisites

This file lists the tools, knowledge, assets, and decisions needed before the Android implementation begins.

## Required Development Tools

- Windows development machine with Git installed.
- Android Studio current stable release.
- Android SDK Platform matching current Google Play target API requirements.
- Android SDK Build-Tools and Platform-Tools.
- Java Development Kit compatible with the Android Gradle Plugin selected for the project.
- Gradle through the project wrapper once the Android project is created.
- Kotlin support through Android Studio and Gradle.
- At least one Android emulator and at least one physical Android test device.

Current compliance note: as of 2026-07-28, Google Play says that from 2026-08-31 new apps and updates must target Android 16 / API level 36 or higher, except for listed form-factor categories. Check the official page before creating or releasing builds: https://developer.android.com/google/play/requirements/target-sdk

## Recommended Learning Topics

Before implementation, study or review:

- Kotlin fundamentals: data classes, sealed classes, null safety, collections, extension functions, and coroutines.
- Android lifecycle: activity lifecycle, pause/resume, configuration changes, saved state, and background limits.
- Jetpack Compose fundamentals for menus, HUD, overlays, settings, and score screens.
- Android rendering choices: Canvas, Compose Canvas, SurfaceView, TextureView, OpenGL ES, Vulkan, Filament, and game engines.
- Game-loop design: fixed timestep, variable timestep, capped delta time, interpolation, frame pacing, and pause handling.
- Maze algorithms: recursive backtracking, randomized depth-first search, graph traversal, breadth-first search, pathfinding, and solvability tests.
- Collision basics: world coordinates, grid coordinates, bounding volumes, wall buffers, and axis-separated movement.
- Local persistence: DataStore for settings/progress and Room for structured records.
- Android testing: local JVM tests, instrumented tests, Compose UI tests, benchmark tests, and device testing.
- Android privacy, permissions, accessibility, and Play Store review requirements.

## Initial Architecture Decisions To Make

These decisions should be documented before Phase 1 code begins:

- Package name and app ID.
- Minimum SDK and target SDK.
- Screen orientation policy.
- Whether the first renderer is Canvas, SurfaceView, OpenGL ES, Filament, or libGDX.
- Whether menus and HUD are pure Compose or a mix of Compose and renderer-drawn UI.
- Whether local scores need Room immediately or can begin in DataStore.
- Whether global/online scores are excluded entirely for version 1 or deferred as a later optional feature.
- How deterministic maze seeds are represented and stored.
- What accessibility settings are part of the first release.
- What assets are placeholder-only and what assets are release-ready.

## Baseline Dependencies To Consider First

Use dependencies sparingly at the start. A good first pass is:

- Kotlin standard library.
- AndroidX Core.
- AndroidX Activity Compose.
- Jetpack Compose BOM and Material 3.
- Kotlin coroutines.
- Jetpack DataStore.
- JUnit and Kotlin test tooling.
- AndroidX Test.
- Compose UI testing.

Add later only when needed:

- Room for structured score/debug history.
- Macrobenchmark and Benchmark libraries for performance work.
- Media3 for music or long-form audio.
- Play Core libraries for Play Store features such as in-app review or asset delivery.
- Play Integrity for optional online score verification, if online features are ever introduced.
- A rendering/game library such as Filament or libGDX if native Canvas/OpenGL work becomes too costly.

## Asset Prerequisites

Track every asset with source, license, author, and intended use.

Needed asset categories:

- Adaptive launcher icon and foreground/background layers.
- Wall, floor, ceiling, exit, hint, and start-area textures.
- UI sounds: button, menu open/close, score save, invalid action.
- Game sounds: footstep, wall bump, hint activation, exit reached, level complete, timeout, game over.
- Optional ambience or music loops.
- Haptic design notes for touch feedback.
- Store listing graphics and screenshots.

Do not include unlicensed assets in the repository.

Read `docs/ASSET_PIPELINE.md` before importing any visual asset. It defines the expected Q&A, naming rules, source-file policy, compression approach, licensing records, and test checks for game textures and graphics.

## Test Device Matrix

The first useful matrix should include:

- Low-end Android phone.
- Mid-range Android phone.
- Tablet or foldable.
- Device with high refresh rate display.
- Device with Android version near the minimum SDK.
- Device with current Android version.
- Emulator for quick regression checks.

Each test pass should record:

- Device model.
- Android version.
- Refresh rate if known.
- Input method.
- App version and commit.
- Average FPS or frame time.
- Battery or thermal observations if relevant.
- Bugs found and seeds needed to reproduce them.

## Pre-Code Checklist

- README initialized.
- Prerequisites documented.
- Web source feature list reviewed.
- Initial renderer decision made.
- Package name chosen.
- Minimum and target SDK chosen.
- Offline-only score and progress behavior defined.
- First release scope agreed.
- Asset pipeline reviewed before adding textures, sprites, icons, or sound.
- Documentation update habit established.
