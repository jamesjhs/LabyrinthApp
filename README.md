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

The third aim is educational. This repo should teach while it grows. Code should be readable, carefully named, and commented at a high level so a learner can understand what each file is for, what important values represent, and how major functions cooperate. Comments should explain intention and tradeoffs, not restate every line.

## Non-Negotiable Foundations

- Native Android only. No WebView game wrapper, HTML shell, browser cache logic, or server-rendered gameplay.
- Offline-first and offline-complete. The app must remain playable without network access.
- No gameplay-critical server dependency. The web version's API-backed version checking and global score validation must be redesigned as local native systems unless an optional online feature is deliberately introduced later.
- Play Store compliance from the beginning, not as a late cleanup stage.
- Modern Android architecture, Kotlin-first implementation, lifecycle-aware components, testable game logic, and measurable performance.
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

Detailed asset and texture notes live in `docs/ASSET_PIPELINE.md`. That document is the working Q&A for how brick textures, wall surfaces, sprites, UI graphics, icons, sounds, and future 3D materials should be created, named, stored, licensed, compressed, tested, and documented.

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

### Phase 0: Documentation And Source Study

Status: started.

Goals:

- Document the current web game's features and architecture.
- Identify which web-only systems must become native Android systems.
- Establish educational documentation standards.
- Choose the initial Android architecture.
- Create prerequisites and setup notes before code begins.

### Phase 1: Native Project Skeleton

Goals:

- Create the Android project with Kotlin, Gradle, and a minimal runnable native app.
- Add a clean module/package structure.
- Add static analysis and test infrastructure.
- Add a placeholder menu and empty game surface.
- Document every new file's role as it is introduced.

### Phase 2: Core Game Logic

Goals:

- Port maze generation, cell/wall representation, shortest-path hints, level sizing, timers, scoring, and mode rules into pure Kotlin.
- Write unit tests for maze solvability, pathfinding, scoring, level progression, and edge cases.
- Keep the core logic independent from Android UI so it can be tested quickly.

### Phase 3: Playable Native Prototype

Goals:

- Implement first-person movement, camera state, touch controls, collision, and level transitions.
- Render the maze using the initial native rendering path.
- Add HUD, minimap, level preview, game over, practice mode, high-score mode, and timed mode.
- Save local progress and local scores.

### Phase 4: Android Enhancement Pass

Goals:

- Add richer textures, native sound effects, haptics, improved input tuning, accessibility options, and performance settings.
- Add debug overlays for FPS, frame time, current cell, seed, level size, path length, and collision state.
- Add deterministic seed replay for bug reports.

### Phase 5: Robustness And Compliance

Goals:

- Run full test suite and device testing.
- Profile performance, memory, startup time, thermal behavior, and battery impact.
- Verify Play Store policy requirements, target SDK, privacy posture, app signing, store listing assets, accessibility, and release build behavior.

### Phase 6: Release Candidate

Goals:

- Freeze gameplay behavior for release.
- Complete technical reference sections.
- Produce release notes.
- Verify all documentation matches actual implementation.

## Development Blog

This README doubles as the project development blog. Every meaningful update should add a short entry here.

| Date | Entry |
| --- | --- |
| 2026-07-28 | Initialized project documentation. Captured the native Android goal, offline-only constraint, web-game feature baseline, proposed dependencies, Play Store compliance baseline, timeline, debug guide, and documentation standards. |
| 2026-07-28 | Added asset and texture pipeline documentation for Android resources, game textures, file placement, licensing, compression, density handling, and Q&A around creating brick and maze-surface assets. |

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
- Update the debug guide when new diagnostics are added.
- Update the bugfix guide when a bug reveals a new class of failure.
- Keep comments educational, high-level, and useful. Explain why systems exist, what important variables represent, and how functions fit into the game loop.

## Current Status

Documentation has been initialized. No Android source code has been written in this repository yet.
