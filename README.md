# Labyrinth

Labyrinth is planned as a fully native Android game based on the existing single-file web game currently hosted as `Maze Runner` / `Life Runner` at:

`C:\GitHub\jahosi\public\labyrinth\labyrinth.html`

The current web version is a self-contained browser game with procedural maze generation, first-person movement, touch controls, keyboard and mouse controls, hints, level progression, countdown and high-score modes, local progress, minimaps, score tables, and a Three.js renderer with a 2D canvas fallback. This repository will become the Android app version of that idea.

The Android app must not be an HTML wrapper, WebView shell, or server-dependent port. It should be a real offline Android game, written for the Android runtime and Play Store distribution, with native access to rendering, audio, storage, input, haptics, lifecycle, accessibility, and device performance controls.

## Project Aims

The first aim is feature parity with the current labyrinth webpage:

- Procedural, solvable maze generation with increasing level size and difficulty.
- First-person movement through a generated labyrinth.
- Collision handling against maze walls and world edges.
- Level preview before play begins.
- Timed mode with lives, countdowns, score bonuses, and progression.
- High-score mode with speed-based scoring.
- Practice mode with level selection and no score pressure.
- Hint system based on shortest route calculation.
- Minimap and north/orientation aid.
- Local progress and local top-10 score tables.
- Touch-first controls, with support for keyboard and external controllers where appropriate.
- Game-over, level-complete, timeout, score, and menu flows.

The second aim is to use Android to improve the game beyond the webpage:

- Richer native textures, sprites, lighting, and animation.
- Sound effects, ambient audio, and responsive audio mixing.
- Haptics for collisions, level completion, danger, and UI feedback.
- Better mobile interaction models, including gestures, virtual controls, and controller/gamepad support.
- Fine-grained level generation tools, presets, difficulty tuning, and reproducible seeds.
- Better persistence for progress, settings, accessibility preferences, and local score history.
- Robust performance management for battery life, thermal pressure, frame pacing, and low-end devices.

The third aim is visual craft. The end product should feel futuristic, slick, comfortable, and visually appealing in both gameplay and interface. The Android app should preserve the modern neon labyrinth mood of the existing HTML/JS version while improving it through native rendering, responsive animation, strong typography, tactile controls, carefully tuned color, and accessible contrast. Styling is not a final coat of paint; it is part of the product requirement.

The fourth aim is educational. This repo should teach while it grows. Code should be readable, carefully named, and commented at a high level so a learner can understand what each file is for, what important values represent, and how major functions cooperate. Comments should explain intention and tradeoffs, not restate every line.

The fifth aim is device respect. The app must run smoothly on modest hardware, survive orientation changes without losing progress, adapt between touch controls and external mouse/keyboard input, and keep all UI clear of fixed Android system areas such as the status bar, display cutouts, gesture regions, and navigation buttons.

## Non-Negotiable Foundations

- Native Android only. No WebView game wrapper, HTML shell, browser cache logic, or server-rendered gameplay.
- Offline-first and offline-complete. The app must remain playable without network access.
- No gameplay-critical server dependency. The web version's API-backed version checking and global score validation must be redesigned as local native systems unless an optional online feature is deliberately introduced later.
- Play Store compliance from the beginning, not as a late cleanup stage.
- Modern Android architecture, Kotlin-first implementation, lifecycle-aware components, testable game logic, and measurable performance.
- Android configuration changes, orientation flips, lifecycle pauses, and process recreation must be treated as normal events. Progress, current run state, settings, and input mode should not be lost casually.
- UI must respect Android window insets, status bars, display cutouts, gesture navigation areas, and navigation buttons.
- Industry-standard safety practices: minimal permissions, explicit data handling, safe persistence, no hidden collection, no unnecessary identifiers, and no unsafe dynamic code loading.

## Proposed Technical Direction

The recommended starting stack is:

- Language: Kotlin.
- Build system: Gradle with the Android Gradle Plugin.
- UI: Jetpack Compose for menus, overlays, settings, score screens, and educational/debug panels.
- Game surface: a native Android rendering surface, likely `SurfaceView` or `TextureView` for the first implementation.
- Game loop: a deterministic loop with capped delta time, separated from rendering so maze generation, scoring, and collision logic can be tested without a device.
- Rendering path: begin with Android Canvas or a lightweight OpenGL ES layer, then move to a richer renderer when the gameplay model is stable.
- Persistence: Jetpack DataStore for simple settings and progress; Room if score history, generated-level records, or debugging traces become relational.
- Audio: SoundPool for short effects; Media3 or native audio tooling only if music, streaming, or complex playback is needed.
- Testing: JUnit for pure game logic, Android instrumented tests for lifecycle and persistence, Compose UI tests for menus, and Macrobenchmark/Perfetto for performance.

This direction intentionally separates the game into teachable layers:

- `core`: maze generation, pathfinding, scoring, level rules, deterministic random seeds.
- `runtime`: player state, input interpretation, collision, timing, game-state transitions.
- `rendering`: scene drawing, textures, sprites, lighting, camera projection.
- `audio`: sound cues, music, haptics, and accessibility-friendly feedback.
- `storage`: progress, settings, score tables, migrations.
- `ui`: menus, HUD, overlays, score screens, settings, debug views.

## Dependencies And Resources To Evaluate

The project should not add every library immediately. Dependencies should be introduced when they solve a real problem and are easy to test.

Recommended core dependencies:

- Android SDK, Android Studio, Gradle, and Kotlin.
- Jetpack Compose for native UI. Android describes Compose as the recommended modern toolkit for native Android UI: https://developer.android.com/compose
- Kotlin coroutines and Flow for structured asynchronous work, storage updates, and lifecycle-aware state streams: https://developer.android.com/kotlin/coroutines and https://developer.android.com/kotlin/flow
- Jetpack DataStore for small persistent values such as highest unlocked level, settings, control preferences, and accessibility preferences: https://developer.android.com/topic/libraries/architecture/datastore
- Room for structured local score history or debug records if simple key-value storage becomes too limited: https://developer.android.com/training/data-storage/room
- AndroidX Test, JUnit, Compose UI testing, and Macrobenchmark for correctness and performance validation.
- Android Game Development Kit resources for game-specific performance, input, frame pacing, and native game guidance: https://developer.android.com/games/agdk/overview

Optional dependencies to consider later:

- OpenGL ES, Vulkan, Filament, or libGDX for richer 3D rendering after the core gameplay is stable.
- SoundPool for short native sound effects.
- Media3 for longer music or ambient tracks.
- Play Integrity only if optional online score sharing or anti-tamper features are introduced. The offline game should not require it.
- Play Asset Delivery only if texture, audio, or level packs become large enough to need modular delivery.

External resources needed:

- Android app icon and adaptive icon artwork.
- Texture set for walls, floors, exits, hints, portals, and level themes.
- Sprite or mesh assets for future collectibles, hazards, or interactive objects.
- Sound effects for movement, collision, UI, hints, level completion, timeout, and game over.
- Music or ambience loops, with licensing documented.
- Accessibility reference notes for motion, contrast, font scaling, touch targets, captions/subtitles where applicable, and haptic alternatives.
- A test device matrix covering low-end phone, mid-range phone, tablet/foldable, and at least one current Android release.

Detailed asset and texture notes live in `docs/ASSET_PIPELINE.md`. That document is the working Q&A for how brick textures, wall surfaces, sprites, UI graphics, icons, sounds, and future 3D materials should be created, named, stored, licensed, compressed, tested, and documented. Asset records begin in `docs/ASSET_MANIFEST.md`.

GitHub release, package, monetization, and Play Store readiness notes live in `docs/GITHUB_RELEASES_PACKAGES_PLAYSTORE.md`. That document defines how this repository should use GitHub Releases, GitHub Packages, release artifacts, version notes, monetization records, and Play readiness gates as the project moves from learning prototype to releasable Android product.

Phase 1 implementation defaults and pre-code decisions live in `docs/DECISIONS.md`. That document records the initial application ID, SDK targets, renderer path, orientation policy, storage posture, offline score posture, monetization decision, signing assumptions, Data Safety assumptions, target audience assumption, asset manifest location, and accessibility scope.

The active multi-phase roadmap lives in `docs/ROADMAP.md`. It defines the `0.x.y` development versioning scheme, current phase, proof tests, exit criteria, release-readiness notes, and next best prompts for each phase.

## Play Store And Android Compliance Baseline

This section must be revisited before every release because requirements change.

As of the documentation pass on 2026-07-28:

- Google Play target API requirements state that, starting 2026-08-31, new apps and updates must target Android 16 / API level 36 or higher, with category exceptions listed by Google: https://developer.android.com/google/play/requirements/target-sdk
- Android core app quality guidance expects apps to target and compile against the latest Android SDK needed for Google Play requirements: https://developer.android.com/docs/quality-guidelines/archive/core/core-app-quality-2026-03-20
- Google Play policy updates must be checked before release and before adding any online, account, child-directed, ads, analytics, or social features: https://developer.android.com/distribute/play-policies
- Android privacy guidance should be treated as a design input from day one: https://developer.android.com/privacy-and-security/about
- Android architecture recommendations should guide maintainable, testable, lifecycle-aware code: https://developer.android.com/topic/architecture/recommendations

Initial compliance posture:

- Request no dangerous permissions unless a future feature absolutely requires one.
- Store data locally by default.
- Do not collect personal data for the offline version.
- Avoid advertising SDKs, tracking SDKs, analytics SDKs, or account sign-in unless explicitly justified later.
- Provide a clear privacy policy if the Play Store listing requires one or if any data collection is added.
- Respect Android lifecycle events: pause audio and rendering correctly, save progress safely, handle configuration changes, and avoid battery drain in background.
- Support different screens and input styles, including phones, tablets, foldables, landscape, portrait, touch, keyboard, and controller where practical.

## Development Timeline

The short timeline below is only a summary. The authoritative roadmap is `docs/ROADMAP.md`.

| Version range | Phase | Primary outcome |
| --- | --- | --- |
| `0.0.y` | Repository and product groundwork | Documentation, source review, prerequisites, roadmap |
| `0.1.y` | Native Android skeleton | Launchable Kotlin/Android foundation with honest placeholders |
| `0.2.y` | Core game logic port | Testable maze, pathfinding, scoring, timer, and mode rules |
| `0.3.y` | Persistence and lifecycle resilience | Progress, scores, settings, run-state recovery, orientation safety |
| `0.4.y` | Native rendering proof | Generated maze renders natively with measured performance |
| `0.5.y` | Input, movement, camera, collision | Playable movement with touch and external input support |
| `0.6.y` | Game flow, modes, HUD, minimap | Web-game feature loop exists natively |
| `0.7.y` | Visual, audio, haptics, accessibility | Polished futuristic style, licensed assets, settings, feedback |
| `0.8.y` | Robustness, debugging, QA, performance | Beta-quality diagnostics, seed replay, test matrix evidence |
| `0.9.y` | Monetization, store prep, Play compliance | Play readiness, release build process, monetization decision |
| `1.0.0` | Initial release candidate | First Play testing/production candidate |

## Development Blog

This README doubles as the project development blog. Every meaningful update should add a short entry here.

| Date | Entry |
| --- | --- |
| 2026-07-28 | Initialized project documentation. Captured the native Android goal, offline-only constraint, web-game feature baseline, proposed dependencies, Play Store compliance baseline, timeline, debug guide, and documentation standards. |
| 2026-07-28 | Added asset and texture pipeline documentation for Android resources, game textures, file placement, licensing, compression, density handling, and Q&A around creating brick and maze-surface assets. |
| 2026-07-28 | Added GitHub Releases, GitHub Packages, monetization, and Play Store readiness documentation so release discipline and commercial constraints are considered from the beginning. |
| 2026-07-28 | Added source-reliability rules, a VS Code Android setup runbook, and an annotated academic/industrial reference catalogue to reduce guesswork during implementation. |
| 2026-07-28 | Added visual and UX quality as a project prerequisite: the app should be futuristic, slick, comfortable, and aesthetically continuous with the existing HTML/JS game. |
| 2026-07-28 | Added device-respect requirements for modest hardware performance, orientation-change resilience, adaptive touch/mouse/keyboard input, and Android system-bar/window-inset safety. |
| 2026-07-28 | Added detailed `0.x.y` multi-phase roadmap with proof tests, release-readiness notes, honest-placeholder rules, and next best prompts for each development phase. |
| 2026-07-28 | Reviewed documentation for contradictions and Play readiness gaps. Added `docs/DECISIONS.md`, aligned release/readiness phases with the roadmap, and recorded Phase 1 defaults for SDKs, app ID, renderer, storage, monetization, privacy, signing, target audience, and asset manifest location. |

## Debug Guide

Every bug should be recorded with enough information to replay the problem.

Minimum bug report fields:

- App version and git commit.
- Device model, Android version, screen size, and input method.
- Game mode, level number, maze seed, maze size, score, lives, timer, and hints remaining.
- What the player did.
- What was expected.
- What happened instead.
- Whether the issue reproduces after restarting the app.
- Logs, screenshots, screen recordings, and performance traces where useful.

Debug tools to build into the app during development:

- FPS and frame-time overlay.
- Current level, seed, cell coordinate, yaw, pitch, and movement velocity display.
- Collision boundary visualizer.
- Minimap path overlay.
- Last generated maze export in a human-readable debug format.
- Safe reset controls for progress, settings, and score data.

## Bugfix Guide

When fixing a bug:

- Reproduce it first, or document why it cannot currently be reproduced.
- Add or update a test when the bug belongs to deterministic game logic.
- Keep the fix close to the failing behavior.
- Record the seed, level, and mode if generation is involved.
- Check nearby systems for related breakage, especially collision, pathfinding, scoring, persistence, and lifecycle.
- Update this README when the bug changes project knowledge, troubleshooting steps, or design direction.

## Technical Reference

The current web game provides the first reference model. Important systems to preserve or replace natively include:

- Configuration values for level size, movement speed, collision buffer, timer factors, hint duration, minimap rate, and scoring.
- Maze generation using a solvable grid of cells with north/east/south/west walls.
- Extra wall openings to create loops and less predictable routes.
- Shortest-path search for hints and timer calibration.
- Player position represented in world coordinates and converted to maze cell coordinates.
- Axis-separated movement so the player can slide along walls.
- Collision buffer to stop camera clipping through walls.
- Level preview map before the timer starts.
- Local progress and score persistence.
- Score sanitization and top-10 trimming.
- Mode-specific rules for practice, timed, and high-score play.

Native replacements required:

- Browser `localStorage` becomes DataStore or Room.
- DOM overlays become Compose screens or composables.
- HTML canvas/minimap becomes native Canvas, Compose Canvas, or renderer-drawn UI.
- Three.js rendering becomes native Android rendering.
- Browser pointer lock, keyboard, mouse, and touch events become Android input handling.
- Server score APIs become local offline score systems unless an optional online mode is designed later.
- Browser cache/version refresh logic is removed and replaced by normal app versioning and Play Store updates.

## Documentation Standards

Each implementation update should keep documentation current:

- Add a short development blog entry.
- Update setup instructions if dependencies, SDK versions, Gradle plugins, or tools change.
- Update the technical reference when game rules, data models, rendering, persistence, or input behavior changes.
- Update `docs/ASSET_PIPELINE.md` when texture formats, asset folders, naming rules, compression decisions, licensing requirements, or rendering assumptions change.
- Update `docs/GITHUB_RELEASES_PACKAGES_PLAYSTORE.md` when release packaging, GitHub automation, monetization plans, billing requirements, or Play Store readiness gates change.
- Update `docs/ROADMAP.md` when phase status, version numbers, exit criteria, proof tests, deferred features, or next best prompts change.
- Update `docs/DECISIONS.md` when application identity, SDK levels, renderer, storage, orientation, monetization, privacy, signing, target audience, accessibility scope, or asset-manifest assumptions change.
- Update the debug guide when new diagnostics are added.
- Update the bugfix guide when a bug reveals a new class of failure.
- Keep comments educational, high-level, and useful. Explain why systems exist, what important variables represent, and how functions fit into the game loop.

## Source Reliability Rules

This project should be built from traceable, reliable sources rather than from memory, unverified snippets, or convenient guesses.

Rules for future implementation:

- Prefer official Android, Google Play, Kotlin, Gradle, GitHub, Khronos, and library documentation for platform behavior, build behavior, publishing, billing, graphics APIs, resource handling, and compliance requirements.
- Prefer peer-reviewed academic papers, standards documents, established textbooks, or mature engine/library documentation for algorithms, rendering, pathfinding, texture compression, frame pacing, and game architecture.
- Record the source whenever a technical decision materially affects architecture, performance, security, privacy, monetization, Play compliance, save data, or asset licensing.
- Treat blog posts, tutorials, forum answers, and AI-generated suggestions as secondary leads. They can help discovery, but they should not become project law unless verified against primary sources or measured locally.
- When sources disagree, document the disagreement and choose the path that is safer for Android compatibility, Play Store review, user privacy, offline behavior, and maintainability.
- Re-check time-sensitive requirements before release. Target API levels, Play Billing versions, Play policy, SDK behavior, and package requirements change over time.
- Any code generated or copied from outside the project must have its license checked before inclusion.

## VS Code Android Development Runbook

This section is deliberately beginner-friendly and explicit. It explains how to prepare a Windows machine to build and run the future native Android app from VS Code and terminal commands.

Current repository status:

- This repository is documentation-only right now.
- No Android project, Gradle wrapper, app module, or source code has been created yet.
- The commands that refer to `gradlew.bat`, `app`, `assembleDebug`, or `installDebug` will become active after Phase 1 creates the Android project skeleton.

### 1. Install Git

1. Download Git for Windows from https://git-scm.com/download/win.
2. Install it with the default options unless you already know you need a custom setup.
3. Open PowerShell.
4. Check it works:

```powershell
git --version
```

5. Clone or open this repository at:

```powershell
cd C:\GitHub\LabyrinthApp
```

### 2. Install Visual Studio Code

1. Download VS Code from https://code.visualstudio.com/.
2. Install it.
3. Open VS Code.
4. Choose `File > Open Folder`.
5. Select:

```text
C:\GitHub\LabyrinthApp
```

6. Open a terminal inside VS Code with `Terminal > New Terminal`.

### 3. Install VS Code Extensions

Install these from the VS Code Extensions panel:

- `Extension Pack for Java` by Microsoft.
- `Kotlin` language support extension.
- `Gradle for Java` by Microsoft.
- `GitHub Pull Requests` by GitHub.
- `GitLens` if you want richer Git history.

These extensions do not replace Android Studio's SDK tools. They make VS Code better at editing, navigating, building, and reviewing the project.

### 4. Install Android Studio And The Android SDK

The easiest reliable path is to install Android Studio even if day-to-day editing happens in VS Code.

1. Download Android Studio from https://developer.android.com/studio.
2. Install Android Studio.
3. Launch it once so it can install the Android SDK.
4. Open `More Actions > SDK Manager`.
5. Install:
   - Android SDK Platform matching the current Play target requirement.
   - Android SDK Build-Tools.
   - Android SDK Platform-Tools.
   - Android SDK Command-line Tools.
   - Android Emulator, if you will use an emulator.
6. Note the Android SDK location. Common Windows path:

```text
C:\Users\<your-user>\AppData\Local\Android\Sdk
```

Official Android docs explain that Android SDK packages can be managed through Android Studio's SDK Manager or with `sdkmanager`: https://developer.android.com/tools

### 5. Install A JDK

Android Gradle Plugin versions require compatible Java versions. Use the JDK recommended by the Android Gradle Plugin selected during Phase 1.

Beginner-friendly option:

1. Let Android Studio install its bundled runtime.
2. If a separate JDK is needed, install a current long-term-support JDK from a trusted distribution such as Eclipse Temurin or Microsoft Build of OpenJDK.
3. In PowerShell, check Java:

```powershell
java -version
```

Do not guess the Java version permanently. When Phase 1 chooses Android Gradle Plugin and Gradle versions, update this section with the exact required JDK.

### 6. Set Environment Variables

Open Windows search and type `Edit the system environment variables`, then open `Environment Variables`.

Create or check:

```text
ANDROID_HOME=C:\Users\<your-user>\AppData\Local\Android\Sdk
ANDROID_SDK_ROOT=C:\Users\<your-user>\AppData\Local\Android\Sdk
```

Add these to `Path`:

```text
%ANDROID_HOME%\platform-tools
%ANDROID_HOME%\cmdline-tools\latest\bin
```

Restart VS Code after changing environment variables.

Check from a new VS Code terminal:

```powershell
adb version
sdkmanager --version
```

Android's ADB documentation describes `adb` as the command-line bridge for communicating with devices, installing apps, and debugging: https://developer.android.com/tools/adb

### 7. Prepare A Physical Android Device

Always test on a real device before release.

1. On the phone, open `Settings > About phone`.
2. Tap `Build number` seven times to unlock Developer Options.
3. Go back to `Settings`.
4. Open `Developer options`.
5. Enable `USB debugging`.
6. Connect the phone to the PC with a USB cable.
7. Accept the RSA debugging prompt on the phone.
8. In VS Code terminal:

```powershell
adb devices
```

9. You should see a device listed as `device`, not `unauthorized`.

Official hardware-device setup guidance: https://developer.android.com/studio/run/device

### 8. Prepare An Emulator

If you do not have a physical device ready:

1. Open Android Studio.
2. Open `Device Manager`.
3. Create a virtual device.
4. Choose a common phone profile.
5. Install a system image matching a recent Android version.
6. Start the emulator.
7. In VS Code terminal:

```powershell
adb devices
```

8. The emulator should appear in the device list.

Physical devices remain mandatory before Play Store release because emulator performance and input behavior do not fully represent real phones.

### 9. Open The Project In VS Code

1. Open VS Code.
2. Open folder:

```text
C:\GitHub\LabyrinthApp
```

3. Open the integrated terminal.
4. Check the repo:

```powershell
git status
```

At the current documentation-only stage, there is no Android build command yet.

### 10. Build The App After Phase 1 Creates The Android Project

Once the Android skeleton exists, use the Gradle wrapper from the repository root. Android's command-line build docs recommend using the wrapper that lives in each Android project.

Build a debug APK:

```powershell
.\gradlew.bat :app:assembleDebug
```

Run tests:

```powershell
.\gradlew.bat test
```

Build a release App Bundle when the release configuration exists:

```powershell
.\gradlew.bat :app:bundleRelease
```

Official Android command-line build guidance: https://developer.android.com/build/building-cmdline

### 11. Install The Debug App On A Device

Once an app module exists and a device is connected:

```powershell
.\gradlew.bat :app:installDebug
```

Or install a built APK directly:

```powershell
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

Then launch the app from the phone's launcher.

### 12. Read Logs

To view device logs:

```powershell
adb logcat
```

After the Android package name exists, this README should add a filtered command for Labyrinth-specific logs.

### 13. Common Beginner Problems

If `adb` is not recognized:

- Restart VS Code after editing `Path`.
- Confirm `%ANDROID_HOME%\platform-tools` exists.
- Run `where adb`.

If the phone says `unauthorized`:

- Unlock the phone.
- Disconnect and reconnect USB.
- Revoke USB debugging authorizations in Developer Options.
- Accept the prompt again.

If Gradle cannot find the SDK:

- Check `ANDROID_HOME`.
- Create `local.properties` only after the Android project exists.
- It will usually contain a line like:

```properties
sdk.dir=C\:\\Users\\<your-user>\\AppData\\Local\\Android\\Sdk
```

If Java errors appear:

- Check `java -version`.
- Check the Android Gradle Plugin's required Java version.
- Update `JAVA_HOME` only after the project chooses a specific JDK.

If the emulator is slow:

- Use a physical device.
- Enable virtualization in BIOS/UEFI if needed.
- Close heavy apps.
- Use a smaller emulator profile.

### 14. Release And Play Store Warning

Debug APKs are for development only. They are not Play Store upload candidates.

Before any Play upload:

- Build a release Android App Bundle.
- Use proper signing.
- Verify `versionCode` and `versionName`.
- Check current Google Play target API requirements.
- Review permissions and data safety.
- Review monetization and Billing behavior if any paid feature exists.
- Update GitHub release notes and Play release notes.

## Current Status

Current version: `0.0.0`.

Current phase: Phase 0, repository and product groundwork.

Documentation has been initialized and the pre-code decision record exists. No Android source code has been written in this repository yet. The next best implementation step is Phase 1: create a launchable native Android skeleton from `docs/DECISIONS.md` with honest placeholders and no gameplay port.

## References And Source Catalogue

This list is intentionally broad. It is the project shelf: official platform references for facts that must be current, and academic or industrial references for algorithms and design concepts that should not be improvised.

### Android Platform, Build, And Tooling

Android Developers. `Download Android Studio and app tools`. https://developer.android.com/studio

Abstract: Official entry point for installing Android Studio, the Android SDK, emulator tooling, and related app-development tools. Use this as the first source for supported Android development environment setup.

Android Developers. `Command-line tools`. https://developer.android.com/tools

Abstract: Explains Android SDK command-line tooling and SDK package management. Relevant for VS Code workflows because VS Code can edit the project while Android SDK tools provide `adb`, `sdkmanager`, platform tools, and build support.

Android Developers. `sdkmanager`. https://developer.android.com/tools/sdkmanager

Abstract: Official reference for viewing, installing, updating, and uninstalling Android SDK packages from the command line. Use this when documenting repeatable SDK setup.

Android Developers. `Build your app from the command line`. https://developer.android.com/build/building-cmdline

Abstract: Explains use of the Gradle wrapper to run Android build tasks from a terminal. This is the core source for VS Code-first build commands such as `assembleDebug`, `installDebug`, and `bundleRelease`.

Android Developers. `Configure your build`. https://developer.android.com/build

Abstract: Official Android build-system overview covering Gradle and the Android Gradle Plugin. Use it for build types, product flavors, signing, dependency configuration, and release variants.

Android Developers. `Android Debug Bridge`. https://developer.android.com/tools/adb

Abstract: Official ADB reference for communicating with devices and emulators. Relevant for installing debug builds, reading logs, checking devices, and debugging from VS Code terminals.

Android Developers. `Run apps on a hardware device`. https://developer.android.com/studio/run/device

Abstract: Official guide for preparing a physical Android device for testing over ADB. This supports the project rule that real-device testing is mandatory before release.

Gradle. `Gradle User Manual`. https://docs.gradle.org/current/userguide/userguide.html

Abstract: Official Gradle documentation for builds, tasks, dependency management, wrappers, and project structure. Use it when Android build behavior depends on Gradle rather than Android-specific APIs.

Kotlin. `Kotlin documentation`. https://kotlinlang.org/docs/home.html

Abstract: Official Kotlin language reference. Use it for language behavior, coroutines basics, coding idioms, and Kotlin/JVM constraints.

Visual Studio Code. `Java in Visual Studio Code`. https://code.visualstudio.com/docs/languages/java

Abstract: Official VS Code documentation for Java language support, project navigation, debugging, and extension setup. Relevant because Android projects use JVM tooling even when the app is Kotlin-first.

### Android Architecture, UI, Persistence, And Testing

Android Developers. `Recommendations for Android architecture`. https://developer.android.com/topic/architecture/recommendations

Abstract: Official guidance for maintainable Android architecture, separation of concerns, UI state, data layers, and testability. Use this to keep game systems modular rather than activity-bound.

Android Developers. `Data layer`. https://developer.android.com/topic/architecture/data-layer

Abstract: Explains repositories, data sources, lifecycle expectations, and main-safe APIs. Relevant to local progress, settings, score tables, asset manifests, and future entitlement storage.

Android Developers. `Jetpack Compose`. https://developer.android.com/compose

Abstract: Official entry point for Compose, Android's recommended modern native UI toolkit. Relevant for menus, HUD overlays, settings, score screens, accessibility surfaces, and debug panels.

Android Developers. `Kotlin coroutines on Android`. https://developer.android.com/kotlin/coroutines

Abstract: Official Android guidance for structured asynchronous work. Relevant for loading assets, persistence, release checks, audio loading, and any non-frame-critical background task.

Android Developers. `Kotlin flows on Android`. https://developer.android.com/kotlin/flow

Abstract: Official guide to Flow as a stream of values over time. Useful for settings, progress, score state, input state, and UI state observation.

Android Developers. `DataStore`. https://developer.android.com/topic/libraries/architecture/datastore

Abstract: Official guide to Jetpack DataStore for asynchronous, consistent key-value or typed data storage. Candidate for settings, highest unlocked level, accessibility preferences, and local flags.

Android Developers. `Room`. https://developer.android.com/training/data-storage/room

Abstract: Official Room persistence guide. Candidate for structured local score history, debug sessions, deterministic seed records, and later asset/entitlement records if DataStore is too simple.

Android Developers. `Test apps on Android`. https://developer.android.com/training/testing

Abstract: Official Android testing entry point. Use it to build the project's testing strategy across JVM logic tests, instrumented tests, UI tests, and release validation.

Android Developers. `Benchmark your app`. https://developer.android.com/topic/performance/benchmarking/overview

Abstract: Official benchmarking overview. Relevant for measuring frame-time-sensitive systems, startup, persistence, and release performance rather than relying on subjective smoothness.

### Android Games, Graphics, Assets, And Performance

Android Developers. `Android Game Development Kit overview`. https://developer.android.com/games/agdk/overview

Abstract: Official overview of Android's game development tools and libraries. Relevant for frame pacing, performance tuning, native integration, input, and game-specific Android behavior.

Android Developers. `Game development basics`. https://developer.android.com/games/guides/basics

Abstract: Introduces Android game development decisions around engines, IDEs, and graphics APIs. Use it when choosing between Canvas, SurfaceView, OpenGL ES, Vulkan, Filament, libGDX, or another path.

Android Developers. `GameActivity`. https://developer.android.com/games/agdk/game-activity

Abstract: Official guide to GameActivity, a Jetpack library for native games that process lifecycle and input events. Relevant if Labyrinth later moves toward a native C/C++ renderer or game loop.

Android Developers. `Textures`. https://developer.android.com/games/optimize/textures

Abstract: Official game texture optimization guide. It explains why compressed textures reduce memory and bandwidth and frames ASTC as the modern primary option with ETC2 as fallback.

Android Developers. `Target texture compression formats in Android App Bundles`. https://developer.android.com/guide/playcore/asset-delivery/texture-compression

Abstract: Explains texture compression format targeting for App Bundles and Play Asset Delivery. Relevant if Labyrinth ships multiple ASTC/ETC2 texture sets or paid cosmetic packs.

Android Developers. `App resources overview`. https://developer.android.com/guide/topics/resources/providing-resources

Abstract: Official Android resource-system guide. Use it to decide between `res/drawable`, `res/mipmap`, `res/raw`, alternative resources, and asset placement.

Android Developers. `Drawable resources`. https://developer.android.com/guide/topics/resources/drawable-resource

Abstract: Official reference for drawable types. Relevant for UI graphics, static images, vector drawables, and non-renderer visual resources.

Android Developers. `Support different pixel densities`. https://developer.android.com/training/multiscreen/screendensities

Abstract: Official density guide. Relevant for UI icons and interface graphics, and for understanding why renderer-owned textures should not be confused with density-scaled UI assets.

Android Developers. `Reduce your app size`. https://developer.android.com/topic/performance/reduce-apk-size

Abstract: Official app-size optimization guide. Relevant to texture budgets, vector drawables, WebP usage, asset pruning, and release audits.

Android Developers. `Analyze texture memory bandwidth usage`. https://developer.android.com/agi/sys-trace/texture-memory-bw

Abstract: Official performance-analysis material explaining how large or uncompressed textures affect package size, cache efficiency, and bandwidth. Relevant for validating texture choices on real hardware.

Khronos Group. `OpenGL ES Registry`. https://registry.khronos.org/OpenGL/index_es.php

Abstract: Official registry for OpenGL ES specifications and extensions. Relevant if the renderer uses OpenGL ES directly or depends on texture-compression extension behavior.

Khronos Group. `Vulkan Documentation`. https://docs.vulkan.org/

Abstract: Official Vulkan documentation. Relevant only if the project later chooses Vulkan or a Vulkan-backed renderer.

Google Filament. `Filament documentation`. https://google.github.io/filament/

Abstract: Official documentation for Google's real-time physically based rendering engine. Relevant if Labyrinth chooses Filament for richer 3D materials and lighting.

libGDX. `libGDX documentation`. https://libgdx.com/wiki/

Abstract: Mature cross-platform game framework documentation. Relevant as an engine candidate if hand-rolling Android rendering becomes too costly.

### Google Play, Monetization, Privacy, And Release

Android Developers. `Meet Google Play's target API level requirement`. https://developer.android.com/google/play/requirements/target-sdk

Abstract: Official, time-sensitive target SDK policy. Must be checked before every Play upload because target API requirements change.

Android Developers. `Google Play policies`. https://developer.android.com/distribute/play-policies

Abstract: Official policy hub for Play distribution. Use it for permissions, privacy, monetization, child safety, deceptive behavior, ads, and listing integrity.

Android Developers. `Privacy checklist`. https://developer.android.com/privacy-and-security/about

Abstract: Official privacy and security checklist. Relevant for minimizing permissions, documenting data handling, and preventing accidental collection in diagnostics or monetized features.

Android Developers. `Google Play Billing`. https://developer.android.com/google/play/billing

Abstract: Official overview of Play's billing system. Required if Labyrinth sells digital content, cosmetic packs, subscriptions, or other in-app products.

Android Developers. `Integrate the Google Play Billing Library`. https://developer.android.com/google/play/billing/integrate

Abstract: Official implementation guide for Play Billing Library setup, BillingClient, product display, purchase flows, and purchase handling.

Android Developers. `Test your Google Play Billing Library integration`. https://developer.android.com/google/play/billing/test

Abstract: Official billing test guide. Relevant for successful purchases, cancellations, pending transactions, restore behavior, refunds, and release confidence.

Google Play Console Help. `Create a one-time product`. https://support.google.com/googleplay/android-developer/answer/16430488

Abstract: Official Play Console help for creating one-time products. Relevant for optional texture packs, level packs, supporter upgrades, or other non-subscription purchases.

Google Play Console Help. `Create and manage subscriptions`. https://support.google.com/googleplay/android-developer/answer/140504

Abstract: Official Play Console help for subscriptions. Relevant only if Labyrinth later chooses recurring paid content; version 1 should avoid subscriptions unless clearly justified.

GitHub Docs. `About releases`. https://docs.github.com/en/repositories/releasing-projects-on-github/about-releases

Abstract: Official GitHub explanation of releases as deployable software iterations based on Git tags. Relevant for milestone builds, tester artifacts, and audit trails.

GitHub Docs. `Managing releases in a repository`. https://docs.github.com/en/repositories/releasing-projects-on-github/managing-releases-in-a-repository

Abstract: Official guide to creating, editing, publishing, and deleting GitHub Releases. Relevant for project release discipline.

GitHub Docs. `Automatically generated release notes`. https://docs.github.com/en/repositories/releasing-projects-on-github/automatically-generated-release-notes

Abstract: Official guide to generating release notes from merged pull requests and tags. Relevant once development uses branches, PRs, and tagged builds.

GitHub Docs. `Introduction to GitHub Packages`. https://docs.github.com/en/packages/learn-github-packages/introduction-to-github-packages

Abstract: Official GitHub Packages overview. Relevant for developer packages, shared modules, build-tool containers, and internal dependencies.

GitHub Docs. `GitHub Packages billing`. https://docs.github.com/en/billing/concepts/product-billing/github-packages

Abstract: Official billing and usage information for GitHub Packages. Relevant before storing large developer artifacts or containers.

### Academic And Industrial References For Game Logic, Design, And Rendering

Hart, P.E., Nilsson, N.J. and Raphael, B. (1968). `A Formal Basis for the Heuristic Determination of Minimum Cost Paths`. IEEE Transactions on Systems Science and Cybernetics, 4(2), pp. 100-107. https://doi.org/10.1109/TSSC.1968.300136

Abstract: Foundational A* search paper describing how heuristic information can guide optimal pathfinding. Relevant to shortest-path hints, route validation, and future AI navigation.

Hunicke, R., LeBlanc, M. and Zubek, R. (2004). `MDA: A Formal Approach to Game Design and Game Research`. Proceedings of the AAAI Workshop on Challenges in Game AI. https://aaai.org/papers/ws04-04-001-mda-a-formal-approach-to-game-design-and-game-research/

Abstract: Defines the Mechanics-Dynamics-Aesthetics framework for connecting game rules to player experience. Relevant to balancing timed mode, practice mode, hints, scoring, pressure, and player learning.

Nystad, J. et al. (2012). `Adaptive Scalable Texture Compression`. High Performance Graphics. https://dl.acm.org/doi/10.5555/2383795.2383812

Abstract: Introduces ASTC, a flexible lossy texture compression system for a wide range of bitrates and texture types. Relevant to Android texture memory, package size, and visual quality.

Sutton, R.S. and Barto, A.G. (2018). `Reinforcement Learning: An Introduction`, 2nd edition. MIT Press. https://mitpress.mit.edu/9780262039246/reinforcement-learning/

Abstract: Standard reinforcement-learning textbook. Not required for version 1, but relevant if future Labyrinth agents, adaptive difficulty, or procedural tuning systems learn from player behavior.

Gregory, J. (2018). `Game Engine Architecture`, 3rd edition. CRC Press. https://www.gameenginebook.com/

Abstract: Industrial game-engine reference covering engine layers, runtime architecture, resources, rendering, animation, collision, and tools. Relevant to keeping Labyrinth modular as it grows.

Nystrom, R. (2014). `Game Programming Patterns`. https://gameprogrammingpatterns.com/

Abstract: Practical software architecture patterns for games, including update loops, state, commands, object pools, and components. Relevant to readable educational code and avoiding overcomplicated architecture.

Pharr, M., Jakob, W. and Humphreys, G. (2023). `Physically Based Rendering: From Theory to Implementation`, 4th edition. https://pbr-book.org/

Abstract: Authoritative rendering reference. Relevant if Labyrinth adopts physically based materials, lighting, texture maps, or more advanced rendering.

Akenine-Moller, T., Haines, E., Hoffman, N., Pesce, A., Iwanicki, M. and Hillaire, S. (2018). `Real-Time Rendering`, 4th edition. https://www.realtimerendering.com/

Abstract: Industrial reference for real-time graphics, GPU pipelines, shading, texturing, antialiasing, lighting, and performance. Relevant to renderer selection and visual quality.

Ericson, C. (2005). `Real-Time Collision Detection`. CRC Press. https://realtimecollisiondetection.net/

Abstract: Standard collision-detection reference for bounding volumes, spatial partitioning, numerical robustness, and intersection tests. Relevant to wall collision, player body handling, and future game objects.

Eberly, D.H. (2020). `Geometric Tools for Computer Graphics and Game Development`. https://www.geometrictools.com/

Abstract: Industrial mathematics and geometry reference with robust algorithms for vectors, matrices, intersections, and spatial queries. Relevant to camera math, grid/world conversion, and collision.

LaValle, S.M. (2006). `Planning Algorithms`. Cambridge University Press. http://planning.cs.uiuc.edu/

Abstract: Comprehensive planning and pathfinding text. Relevant to maze search, navigation graphs, procedural level analysis, and future non-player movement.

Buck, J. (2015). `Mazes for Programmers`. Pragmatic Bookshelf. https://pragprog.com/titles/jbmaze/mazes-for-programmers/

Abstract: Practical book on maze-generation algorithms and their visual/structural properties. Relevant to teaching procedural labyrinth generation clearly.

McGuire, M. et al. `The Graphics Codex`. https://graphicscodex.com/

Abstract: Compact graphics reference covering rendering math, color, sampling, texture filtering, and GPU concepts. Useful as a developer reference for rendering decisions and educational comments.

Fiedler, G. (2004). `Fix Your Timestep!`. https://gafferongames.com/post/fix_your_timestep/

Abstract: Widely cited industry article explaining fixed and semi-fixed timestep game loops. Relevant to stable movement, collision, timer behavior, and pause handling.

Google. `Filament: Physically Based Rendering in Filament`. https://google.github.io/filament/Filament.html

Abstract: Industrial rendering document explaining Filament's physically based material and lighting model. Relevant if Labyrinth uses Filament for Android 3D rendering.
