# Labyrinth Development Roadmap

This roadmap defines how the native Android version of Labyrinth should move from documentation to release candidate. It is based on the existing single-file web game at `C:\GitHub\jahosi\public\labyrinth\labyrinth.html`, but the Android app must be a native offline app rather than a WebView or HTML wrapper.

The roadmap is intentionally stepwise. Each phase introduces a small set of key systems, proves them with tests or real-device checks, and leaves the app closer to a Play Store-ready product. Where final assets or features are not ready, use honest placeholders: clearly marked temporary art, local-only scoreboards, disabled menu entries with explanatory development notes, or stub screens that do not pretend to work. Do not add dummy functionality that trains the codebase or the user interface around false behavior.

## Versioning Rules

Use `0.x.y` while the app is in development:

- `x` is the current development phase.
- `y` is the bugfix, polish, or iteration number inside that phase.
- Example: `0.3.2` means Phase 3, second iteration/bugfix pass.
- Version `1.0.0` is reserved for the first initial release candidate.

Each phase should have:

- A GitHub milestone.
- A GitHub pre-release or release when exit criteria are met.
- A README development-blog entry.
- Updated known issues.
- Updated test notes.
- Updated Play readiness notes.

## Current Development Point

Current planned version: `0.0.0`

Current phase: Phase 0, repository and product groundwork.

Current status:

- Documentation exists.
- No Android app skeleton exists yet.
- No Gradle wrapper exists yet.
- No native source code has been written in this repository yet.
- The next best work is to convert this documentation into a minimal Android project skeleton without implementing gameplay.

Next best prompt:

```text
Using the README, prerequisites, decision record, roadmap, asset pipeline, and GitHub/Play readiness docs, create Phase 1 version 0.1.0 of the native Android app skeleton. Do not port gameplay yet. Set up a Kotlin/Gradle Android project with a minimal launchable Activity, Compose-ready structure, documentation comments, no unnecessary permissions, and a placeholder screen that clearly states this is the Labyrinth native Android foundation.
```

## Phase 0: Repository And Product Groundwork

Version range: `0.0.y`

Goal: prepare the project so implementation starts from documented intent rather than guesswork.

Current work:

- Define native Android, offline-first, no-WebView requirements.
- Capture web-game feature baseline.
- Define Play Store, monetization, GitHub Releases/Packages, asset pipeline, visual style, input, orientation, lifecycle, and safe-area requirements.
- Add official and academic source catalogue.
- Add VS Code Android setup runbook.
- Add a project decision record for Phase 1 defaults, release assumptions, Play readiness assumptions, and known pre-code decisions.

Proof tests:

- Documentation is internally consistent.
- Release/readiness phase names match the roadmap's `0.0.y` through `1.0.0` version model.
- Pre-code decisions needed for Phase 1 are recorded in `docs/DECISIONS.md`.
- Current web game path has been reviewed.
- Roadmap identifies current phase and next best prompt.

Exit criteria:

- README links all required planning documents.
- `docs/PREREQUISITES.md` is complete enough to begin Phase 1.
- `docs/DECISIONS.md` exists and records Phase 1 defaults.
- `docs/ASSET_PIPELINE.md` exists.
- `docs/GITHUB_RELEASES_PACKAGES_PLAYSTORE.md` exists.
- `docs/ROADMAP.md` exists.
- Phase 1 prompt is ready.

Release readiness notes:

- No app artifact exists yet.
- No Play upload is possible.
- No monetization code exists.
- No permissions exist.

Next best prompts:

```text
Review the current documentation set for contradictions, missing pre-code decisions, or Play Store readiness gaps. Do not write app code.
```

```text
Create Phase 1 version 0.1.0 from the documented Phase 1 defaults: native Android skeleton only, with no gameplay port yet.
```

## Phase 1: Native Android Skeleton

Version range: `0.1.y`

Goal: create a minimal, launchable Android app foundation that is already structured for release discipline.

Work:

- Create Kotlin-first Android project.
- Add Gradle wrapper and standard Android project files.
- Apply or deliberately revise the documented application ID, minimum SDK, target SDK, compile SDK, versioning scheme, and package structure from `docs/DECISIONS.md`.
- Add a single launch Activity.
- Add Compose setup for future menus, overlays, HUD, settings, and score screens.
- Add placeholder launch screen with native styling inspired by the web version's neon/dark labyrinth mood.
- Add no unnecessary permissions.
- Add basic app icon placeholders that are explicitly marked temporary.
- Add debug/release build types.
- Add local unit-test skeleton.
- Add `.gitignore` coverage for Android/Gradle outputs.
- Update README runbook with real build commands.

Placeholders allowed:

- Temporary launcher icon.
- Temporary title screen.
- Placeholder renderer area that says gameplay is not implemented yet.
- Placeholder style tokens for palette, spacing, and typography.

Not allowed:

- WebView wrapper.
- Copied HTML/JS gameplay inside Android assets as the app experience.
- Fake menu buttons that appear to start gameplay but do nothing.
- Unexplained permissions.

Proof tests:

- `.\gradlew.bat :app:assembleDebug` succeeds.
- `.\gradlew.bat test` succeeds.
- Debug APK installs on at least one device or emulator.
- App opens without crash.
- App respects status/navigation bars and display cutout in portrait and landscape.
- Orientation flip does not crash the placeholder screen.

Exit criteria:

- Native skeleton is launchable.
- README current status is updated to `0.1.y`.
- GitHub pre-release notes identify the build as skeleton-only.
- No gameplay claims are made.

Release readiness notes:

- This is not Play-ready.
- It should still be built as if Play readiness matters: no unsafe permissions, clean versioning, documented target SDK, and no hidden data collection.

Next best prompts:

```text
Implement Phase 1 version 0.1.0 native Android skeleton only. Use Kotlin and Compose. Add no gameplay. Keep placeholders honest and documented.
```

```text
Review the Phase 1 Android skeleton for Play readiness, window-inset safety, orientation stability, and unnecessary permissions.
```

## Phase 2: Core Game Logic Port

Version range: `0.2.y`

Goal: port the web game's deterministic rules into testable Kotlin modules without relying on Android UI or rendering.

Work:

- Define maze cell, wall, direction, coordinate, and level data models.
- Port procedural maze generation.
- Preserve solvability.
- Port extra wall openings/loops.
- Port level-size progression.
- Port shortest-path search for hints and timer calibration.
- Port mode rules: Practice, Timed Run, High Score.
- Port score formulas and cumulative continue-score estimate.
- Define deterministic seed handling for replayable bug reports.
- Add tests for maze solvability, boundaries, pathfinding, scoring, hints, timers, and mode behavior.
- Add high-level comments explaining algorithm intent.

Placeholders allowed:

- Placeholder renderer receives generated maze data but does not draw it yet.
- Placeholder score screen can display test values if clearly marked as dev-only.

Not allowed:

- Game logic embedded directly in Activity or composables.
- Random behavior that cannot be replayed for tests.
- Untested scoring changes that diverge from the web baseline without documentation.

Proof tests:

- Unit tests generate many mazes and prove every maze has a path from start to exit.
- Shortest path returns valid adjacent steps through open walls only.
- Timer formula produces positive and plausible values.
- Practice mode never scores.
- Timed and high-score modes score according to documented formulas.
- Same seed produces same maze.

Exit criteria:

- Pure Kotlin game core is testable without Android device.
- Web-game rule divergences are documented.
- Debug seed format is documented.

Release readiness notes:

- Still not Play-ready.
- This phase creates the correctness foundation for release-quality gameplay.

Next best prompts:

```text
Implement Phase 2 version 0.2.0: pure Kotlin maze generation, pathfinding, level rules, mode rules, scoring, and deterministic seed tests. Do not implement rendering yet.
```

```text
Review the Phase 2 core game logic against the original labyrinth.html feature baseline and identify rule divergences or missing tests.
```

## Phase 3: Persistence, Run State, And Lifecycle Resilience

Version range: `0.3.y`

Goal: make progress, settings, current run state, and orientation/lifecycle behavior durable before active gameplay ships.

Work:

- Add DataStore for settings and highest unlocked level.
- Decide whether local score tables begin in DataStore or Room.
- Persist local top-10 score tables.
- Persist accessibility and input preferences.
- Design recoverable active-run state: mode, level, seed, player cell/position, timer, lives, hints, score, and pause reason.
- Handle orientation flips without losing progress.
- Handle Activity recreation and process recreation as expected Android events.
- Add fair pause/resume semantics for timed mode.
- Add tests for persistence serialization and migration assumptions.

Placeholders allowed:

- Placeholder UI for settings and scores if real visual design is not ready.
- Placeholder migration version record.

Not allowed:

- Progress only in memory.
- Timer continuing silently while the app is backgrounded.
- Orientation lock used as a shortcut unless deliberately chosen and documented.

Proof tests:

- Highest unlocked level survives app restart.
- Local score table survives app restart.
- Orientation flip preserves placeholder or active run state.
- App switch and resume does not silently punish timed play.
- Stored data rejects corrupt or out-of-range values safely.

Exit criteria:

- Persistence strategy is implemented and documented.
- Run state has a recovery contract.
- Debug guide includes how to report persisted-state bugs.

Release readiness notes:

- This phase reduces future Play Store risk by treating lifecycle as normal Android behavior early.

Next best prompts:

```text
Implement Phase 3 version 0.3.0: DataStore-backed progress/settings, local score persistence, recoverable run-state model, and orientation/lifecycle resilience tests.
```

```text
Review Phase 3 for lifecycle, orientation, and process-recreation failure modes before gameplay rendering begins.
```

## Phase 4: Native Rendering Proof

Version range: `0.4.y`

Goal: render a generated maze natively with stable frame pacing on modest hardware.

Work:

- Choose first renderer: Canvas, SurfaceView, OpenGL ES, Filament, libGDX, or another documented option.
- Draw maze floors, walls, start, and exit using native rendering.
- Add camera/player position conversion between maze cells and world coordinates.
- Add basic frame loop with capped delta time.
- Add debug frame-time/FPS overlay.
- Add render-scale or quality fallback if needed.
- Add placeholder textures or flat materials following `docs/ASSET_PIPELINE.md`.
- Ensure renderer respects Android lifecycle pause/resume.

Placeholders allowed:

- Flat-color walls/floors before final textures.
- Temporary debug material names.
- Simple lighting before final atmosphere.

Not allowed:

- HTML canvas, WebView, or embedded Three.js as the Android rendering path.
- Renderer that continues burning resources while app is paused.
- Visual complexity that prevents smooth modest-device performance.

Proof tests:

- Generated maze renders on at least one physical device.
- Renderer survives orientation flip.
- Renderer pauses/resumes cleanly.
- UI/HUD placeholder avoids system bars and navigation regions.
- FPS/frame-time overlay works in debug builds.
- Low-end or emulator performance is measured and recorded.

Exit criteria:

- Native rendering proof exists.
- Performance baseline is documented.
- Rendering decision is either confirmed or explicitly marked for replacement.

Release readiness notes:

- Not Play-ready, but visual and performance foundations should already be treated seriously.

Next best prompts:

```text
Implement Phase 4 version 0.4.0: native maze rendering proof using the documented renderer choice, debug FPS overlay, lifecycle pause/resume, and safe-area-aware placeholder HUD.
```

```text
Profile Phase 4 on modest hardware and recommend renderer/texture changes before movement is added.
```

## Phase 5: Input, Movement, Camera, And Collision

Version range: `0.5.y`

Goal: make the maze playable with reliable native input and collision.

Work:

- Add player state: world position, yaw, pitch, velocity, current cell.
- Implement axis-separated movement and wall collision buffer.
- Implement touch movement and look controls.
- Implement keyboard movement and mouse look or pointer-style interaction where appropriate.
- Detect and switch between touch and external input modes.
- Add pitch limits to reduce disorientation.
- Add safe pause/menu/escape action.
- Add debug view for current cell, velocity, yaw, pitch, and collision state.

Placeholders allowed:

- Temporary joystick art.
- Temporary input settings screen.
- Temporary haptic cues.

Not allowed:

- Input switching that requires app restart.
- Wall clipping as accepted behavior.
- Controls that sit under system navigation or gesture areas.

Proof tests:

- Player cannot cross closed walls.
- Player can cross open walls.
- Diagonal movement slides along walls rather than hard-stopping.
- Touch controls work on phone.
- Keyboard controls work with external keyboard.
- Mouse input does not break touch mode.
- Orientation flip preserves player/run state.
- Controls remain clear of system UI.

Exit criteria:

- A tester can walk through a generated maze and reach the exit manually.
- Input behavior is documented.
- Collision edge cases are recorded in debug guide.

Release readiness notes:

- This is the first true playable prototype, but it should be distributed only as a pre-release.

Next best prompts:

```text
Implement Phase 5 version 0.5.0: native player movement, camera look, wall collision, touch controls, mouse/keyboard support, input-mode switching, and debug collision overlay.
```

```text
Review Phase 5 movement and collision against the original labyrinth.html behavior and identify fairness, motion sickness, and wall-clipping risks.
```

## Phase 6: Game Flow, Modes, HUD, And Minimap

Version range: `0.6.y`

Goal: connect core rules, rendering, input, and persistence into the full gameplay loop from the web version.

Work:

- Add main menu.
- Add mode selection: Timed Run, High Score, Practice.
- Add practice level selector.
- Add level preview map before timer starts.
- Add HUD: level, timer, lives, hints, score, mode.
- Add minimap and north/orientation aid.
- Add hint activation based on shortest path.
- Add level complete, timeout, game over, and score flows.
- Save progress and local score tables.
- Add pause/menu escape route.
- Ensure every overlay respects window insets and orientation changes.

Placeholders allowed:

- Placeholder score submission copy while local-only scores are used.
- Placeholder animations where final motion design is not ready.
- Placeholder iconography if marked temporary.

Not allowed:

- Any global/online scoreboard unless deliberately added as a later optional system.
- Menu buttons that imply monetized or online features before they exist.
- Timer starting during level preview.

Proof tests:

- Practice mode has no score and no countdown.
- Timed mode counts down, handles lives, and scores correctly.
- High Score mode scores by speed without countdown.
- Hints reduce correctly and show valid path segments.
- Level preview never starts timer until player starts.
- Local score table sanitizes names and trims top 10.
- Orientation flip during each overlay preserves state.
- HUD and controls avoid system UI in portrait and landscape.

Exit criteria:

- Feature parity loop exists for the core web game modes.
- Tester can start, play, finish, score, and return to menu.
- Known issues list is updated from real play sessions.

Release readiness notes:

- Candidate for internal alpha pre-release if performance is acceptable.

Next best prompts:

```text
Implement Phase 6 version 0.6.0: menus, modes, HUD, minimap, hints, level preview, scoring flows, local score table, and safe-area-aware overlays.
```

```text
QA Phase 6 against the original web game feature list and produce a missing-feature and regression checklist.
```

## Phase 7: Visual, Audio, Haptics, And Accessibility Pass

Version range: `0.7.y`

Goal: make the game feel like a polished native evolution of the HTML/JS version rather than a plain technical port.

Work:

- Create and apply style guide: palette, typography, spacing, HUD rules, animation tone, icon style, and texture mood.
- Replace placeholders with licensed or generated assets recorded in the asset manifest.
- Add wall, floor, exit, start, and hint textures or materials.
- Add UI sounds and game sounds.
- Add optional ambience if licensed and performance-safe.
- Add haptics for collision, hint, timeout, level completion, and menu feedback.
- Add accessibility settings: reduced motion, simplified textures, contrast options, sound/haptic controls, HUD scale if needed.
- Add performance quality settings.
- Prepare store-screenshot visual direction without overstating features.

Placeholders allowed:

- A limited number of clearly marked temporary assets if asset licensing is still in progress.
- Disabled monetized theme slots if explicitly labelled as future/not included.

Not allowed:

- Unlicensed assets.
- Visual effects that hide exits, timers, controls, or walls.
- Audio or haptics without user controls.
- Paid-looking UI for products that are not implemented.

Proof tests:

- Asset manifest covers all non-trivial assets.
- Game remains readable in motion.
- Reduced-motion and simplified-visual settings work.
- Sound/haptic settings work.
- Low-end performance remains acceptable.
- UI still respects insets in all major screens.

Exit criteria:

- App has a coherent futuristic native look.
- Placeholder list is short and documented.
- Accessibility and performance settings exist for known risk areas.

Release readiness notes:

- Candidate for closed alpha if stability is acceptable.

Next best prompts:

```text
Implement Phase 7 version 0.7.0: visual style guide application, licensed placeholder replacement, textures/materials, audio, haptics, accessibility settings, and performance quality controls.
```

```text
Review Phase 7 visual/audio/haptic changes for accessibility, asset licensing, app size, and modest-hardware performance.
```

## Phase 8: Robustness, Debugging, QA, And Performance

Version range: `0.8.y`

Goal: turn the playable app into a testable beta candidate with real evidence behind quality claims.

Work:

- Add structured debug report capture for version, device, Android version, input mode, level, seed, FPS, score, lives, timer, and settings.
- Add deterministic seed replay from debug report.
- Add regression test seed corpus.
- Add performance profiling on test device matrix.
- Add crash handling strategy that avoids sensitive data.
- Add app-size audit.
- Add dependency/license audit.
- Add Play policy checklist pass.
- Add GitHub release templates or generated release note workflow.
- Add known issues file or section.

Placeholders allowed:

- Manual QA checklist before full automation.
- Local debug export before polished in-app report UI.

Not allowed:

- Hidden telemetry.
- Logging player-entered names or sensitive data unnecessarily.
- Release notes that omit known major problems.

Proof tests:

- Debug report can reproduce at least one maze seed.
- Performance data exists for low-end, mid-range, and emulator/device variants.
- App survives repeated orientation flips during menu, preview, play, score, and settings.
- App survives input-device changes during play.
- App-size and asset-size reports are recorded.
- Permissions remain minimal.

Exit criteria:

- Closed beta candidate checklist exists.
- Known issues are documented.
- GitHub pre-release includes test summary and Play readiness status.

Release readiness notes:

- Candidate for beta testing if severe issues are resolved.

Next best prompts:

```text
Implement Phase 8 version 0.8.0: debug report model, seed replay, QA checklist, performance profiling notes, app-size audit, dependency/license audit, and GitHub release note template.
```

```text
Run a Phase 8 QA review and produce a go/no-go list for closed beta readiness.
```

## Phase 9: Monetization, Store Prep, And Play Compliance

Version range: `0.9.y`

Goal: prepare the app for Play distribution, with monetization either deliberately absent or deliberately implemented and tested.

Work:

- Decide version 1 monetization model: free, paid upfront, optional one-time products, or no monetization.
- If no monetization, document that explicitly.
- If monetization exists, integrate Play Billing properly and test purchase flows.
- Prepare privacy policy if required.
- Draft Play Data Safety answers.
- Draft store listing text.
- Prepare screenshots and feature graphic.
- Prepare content rating assumptions.
- Prepare signed release build process.
- Verify target SDK against current Play requirements.
- Review app permissions, accessibility, policy, billing, and asset licensing.
- Create release checklist for `1.0.0`.

Placeholders allowed:

- Draft store copy.
- Draft screenshots from debug builds if clearly marked internal.
- Disabled future purchase categories.

Not allowed:

- Fake purchase buttons.
- Non-functional restore purchase flow if billing is present.
- Ads or analytics added casually at the end.
- Store claims for features not implemented.

Proof tests:

- Release AAB can be built.
- Release build is not debuggable.
- Version code/name are correct.
- Data Safety draft matches actual app behavior.
- Billing tests pass if billing exists.
- Paid entitlements restore correctly if billing exists.
- App works offline for all base-game features.

Exit criteria:

- Play readiness checklist is complete or blockers are explicit.
- Monetization posture is unambiguous.
- Release candidate work is scoped.

Release readiness notes:

- This phase is the final pre-release phase before `1.0.0`.

Next best prompts:

```text
Implement Phase 9 version 0.9.0: Play readiness checklist, monetization decision record, privacy/data safety draft, release build process notes, store listing draft, and signed AAB preparation guidance.
```

```text
Review Phase 9 for Play Store blockers, monetization risk, privacy/data safety mismatch, and release-signing gaps.
```

## Version 1.0.0: Initial Release Candidate

Goal: produce the first release candidate that could reasonably enter Play testing or production review.

Required:

- Native Android app.
- No WebView/HTML wrapper.
- Offline base gameplay complete.
- Timed, High Score, and Practice modes complete or explicitly scoped.
- Local progress and local score persistence complete.
- Orientation, lifecycle, and input switching tested.
- Safe-area/window-inset handling tested.
- Modest-hardware performance measured.
- Accessibility settings present for known risks.
- Asset licenses and manifest complete.
- Privacy/data handling documented.
- Monetization either absent or fully implemented and tested.
- Release AAB created from traceable source.
- GitHub Release notes complete.
- Play release notes and listing drafts complete.

Exit criteria:

- No known blocker remains for Play closed testing.
- Any remaining issue is documented, triaged, and accepted for release-candidate testing.
- Version changes from `0.9.y` to `1.0.0`.

Next best prompts:

```text
Perform a full 1.0.0 release-candidate review against README, prerequisites, roadmap, Play readiness, asset pipeline, and GitHub release requirements. Return blockers first, then recommendations.
```

```text
Prepare the 1.0.0 GitHub Release notes and Play testing release notes from the current repository state and QA results.
```

## Phase Status Table

| Version range | Phase | Status | Release intent |
| --- | --- | --- | --- |
| `0.0.y` | Repository and product groundwork | Complete | Documentation only |
| `0.1.y` | Native Android skeleton | Current | Internal pre-release |
| `0.2.y` | Core game logic port | Not started | Internal pre-release |
| `0.3.y` | Persistence and lifecycle resilience | Not started | Internal pre-release |
| `0.4.y` | Native rendering proof | Not started | Internal pre-release |
| `0.5.y` | Input, movement, camera, collision | Not started | Playable prototype |
| `0.6.y` | Game flow, modes, HUD, minimap | Not started | Internal alpha |
| `0.7.y` | Visual, audio, haptics, accessibility | Not started | Closed alpha candidate |
| `0.8.y` | Robustness, debugging, QA, performance | Not started | Closed beta candidate |
| `0.9.y` | Monetization, store prep, Play compliance | Not started | Release-candidate preparation |
| `1.0.0` | Initial release candidate | Not started | Play testing/production candidate |

## Roadmap Maintenance Rules

- Update this file at the end of every phase iteration.
- Keep the current version and current phase accurate.
- Add new prompts when a phase reveals better next actions.
- Do not mark a phase complete until proof tests and exit criteria are satisfied.
- If a feature is deferred, record where it moved and why.
- If placeholder behavior remains, record whether it is acceptable for the next phase or a release blocker.
- Keep release readiness notes blunt. Optimism is not a test result.
