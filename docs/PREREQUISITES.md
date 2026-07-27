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
- Android window insets, display cutouts, status bars, gesture navigation, and navigation bar handling.
- Android rendering choices: Canvas, Compose Canvas, SurfaceView, TextureView, OpenGL ES, Vulkan, Filament, and game engines.
- Game-loop design: fixed timestep, variable timestep, capped delta time, interpolation, frame pacing, and pause handling.
- Android input handling for touch, mouse, keyboard, controller/gamepad, focus, hover, and pointer capture where appropriate.
- Maze algorithms: recursive backtracking, randomized depth-first search, graph traversal, breadth-first search, pathfinding, and solvability tests.
- Collision basics: world coordinates, grid coordinates, bounding volumes, wall buffers, and axis-separated movement.
- Local persistence: DataStore for settings/progress and Room for structured records.
- Android testing: local JVM tests, instrumented tests, Compose UI tests, benchmark tests, and device testing.
- Android privacy, permissions, accessibility, and Play Store review requirements.
- Visual design fundamentals: color contrast, typography, spacing, motion, iconography, responsive layout, and game HUD readability.

## Initial Architecture Decisions To Make

These decisions should be documented before Phase 1 code begins:

- Package name and app ID.
- Minimum SDK and target SDK.
- Screen orientation policy.
- How orientation changes, Activity recreation, pause/resume, and process recreation preserve game progress and current run state.
- How the app detects and switches between touch controls and external mouse/keyboard input.
- How the UI will consume Android window insets so HUD, controls, buttons, and overlays do not collide with the status bar, display cutout, gesture area, or navigation buttons.
- Whether the first renderer is Canvas, SurfaceView, OpenGL ES, Filament, or libGDX.
- Whether menus and HUD are pure Compose or a mix of Compose and renderer-drawn UI.
- Whether local scores need Room immediately or can begin in DataStore.
- Whether global/online scores are excluded entirely for version 1 or deferred as a later optional feature.
- Whether version 1 is free, paid upfront, ad-supported, or monetized through optional one-time products/subscriptions.
- What GitHub release/versioning workflow will be used before the first public build.
- How deterministic maze seeds are represented and stored.
- What accessibility settings are part of the first release.
- What assets are placeholder-only and what assets are release-ready.
- What visual style guide will carry the existing HTML/JS game's modern neon labyrinth feel into the native Android app.

## Visual And UX Prerequisites

The end product must be futuristic, slick, visually appealing, and comfortable to use. This applies to gameplay surfaces, menus, HUD, overlays, score screens, settings, icons, transitions, feedback, and store-facing screenshots.

The existing HTML/JS game is the foundation for the first art direction. The Android app should preserve its modern neon labyrinth mood, dark atmospheric background, bright exit/hint language, compact HUD, touch-friendly controls, and arcade-like clarity while making the experience feel native, smoother, richer, and more deliberate.

Visual quality requirements:

- Treat styling and aesthetics as functional requirements from Phase 1 onward.
- Create a small style guide before building production UI: palette, typography, spacing, icon style, HUD rules, animation tone, contrast rules, and texture mood.
- Keep the game readable during motion. Futuristic visuals must never obscure walls, exits, hints, timers, lives, score, or controls.
- Use native Android UI conventions where they improve comfort, accessibility, and Play Store polish.
- Avoid placeholder UI becoming permanent. Any placeholder screen should be labelled as temporary in development notes.
- Test UI on phone, tablet/foldable, emulator, light/dark system settings where relevant, and high font-scale accessibility settings.
- Make store screenshots visually strong enough to sell the product honestly without exaggerating features that do not exist.
- Ensure monetized assets, if ever added, meet at least the same visual quality bar as the base game.

## Device Ergonomics And Resilience Prerequisites

The app must behave like a considerate Android citizen on real devices, not just in a perfect emulator window.

Performance requirements:

- The game must run smoothly on modest hardware, including low-end and mid-range Android phones.
- The first playable renderer should favor stable frame pacing, readable visuals, and battery awareness over maximum visual complexity.
- Texture size, shader complexity, particle counts, lighting, minimap update rate, and animation density must be budgeted and tested.
- Low-performance fallback settings should be planned early, such as reduced effects, lower render scale, simplified textures, or capped visual extras.

Orientation and lifecycle requirements:

- Orientation flips must not erase progress, current level, score, timer state, lives, hints, selected mode, settings, or generated maze seed.
- Pause/resume, app switching, screen lock, and Activity recreation must be treated as normal flows.
- If the app cannot safely continue an active timed run after a lifecycle event, it must resume into a fair paused state rather than silently punish the player.
- Persistent progress and recoverable run state must be designed before timed gameplay ships.

Input requirements:

- Touch controls are the primary phone experience.
- If a mouse and/or keyboard is connected, the app should expose appropriate pointer and keyboard controls without requiring a restart.
- The UI should adapt visible controls to the active input mode where appropriate: touch joystick controls for touch play, hover/focus-friendly controls for mouse, and key hints or focus handling for keyboard.
- Input switching must not break gameplay state. A player should be able to connect or disconnect input devices mid-session.

System UI and safe-area requirements:

- HUD, menus, touch controls, score overlays, buttons, and debug panels must respect Android window insets.
- UI must not sit under or collide with the status bar, display cutouts, gesture navigation regions, or navigation buttons.
- Full-screen or immersive choices must be deliberate, tested, reversible where appropriate, and compliant with Android behavior.
- Safe-area behavior must be tested on gesture-navigation phones, three-button-navigation phones, notched/cutout devices, tablets, and landscape orientation.

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

Read `docs/GITHUB_RELEASES_PACKAGES_PLAYSTORE.md` before creating the Android project skeleton. It defines the expected GitHub Releases and Packages usage, release artifact rules, monetization-first design checks, and Play Store readiness gates.

Read `docs/ROADMAP.md` before starting implementation. It defines the current version, current phase, proof tests, exit criteria, release-readiness notes, and next best prompts.

## Test Device Matrix

The first useful matrix should include:

- Low-end Android phone.
- Mid-range Android phone.
- Tablet or foldable.
- Device with display cutout or rounded corners.
- Device using gesture navigation.
- Device using three-button navigation.
- Device tested in portrait and landscape orientation.
- Device tested with external mouse and keyboard, if available.
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
- Roadmap reviewed and current phase confirmed.
- Web source feature list reviewed.
- Initial renderer decision made.
- Package name chosen.
- Minimum and target SDK chosen.
- Offline-only score and progress behavior defined.
- First release scope agreed.
- Asset pipeline reviewed before adding textures, sprites, icons, or sound.
- Visual style guide drafted before production UI or texture work begins.
- Orientation, lifecycle, input-switching, performance, and window-inset strategy drafted before production gameplay begins.
- GitHub release/package workflow reviewed.
- Monetization model and Play Store readiness assumptions recorded.
- Documentation update habit established.
