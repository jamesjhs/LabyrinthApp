# Project Decisions

This file records decisions that must be settled before Android app code begins. Decisions can be revised later, but changes should be deliberate, dated, and reflected in the roadmap, release notes, and relevant setup docs.

## Phase 1 Defaults

These defaults unblock the native Android skeleton. They are intentionally conservative and offline-friendly.

| Area | Decision | Notes |
| --- | --- | --- |
| Application ID | `com.jahosi.labyrinth` | Use for the Android package unless a Play Console namespace conflict is discovered. |
| Kotlin package root | `com.jahosi.labyrinth` | Keep app, feature, and test packages aligned with the application ID. |
| Minimum SDK | API 26, Android 8.0 | Broad enough for older devices while avoiding very old platform behavior. Revisit if real test devices require lower support. |
| Target SDK | API 36 or newer | Current documentation notes that Google Play requires API 36+ for new apps and updates starting 2026-08-31. Re-check before every Play upload. |
| Compile SDK | Match the selected target SDK | Use the SDK required by current Android Gradle Plugin and Play policy. |
| Versioning | `versionName` follows roadmap versions; `versionCode` is monotonic | Start Phase 1 as `0.1.0`. Suggested first `versionCode`: `100`. |
| Orientation policy | Support portrait and landscape; do not lock orientation by default | Orientation changes must preserve placeholder state in Phase 1 and active run state before timed gameplay ships. |
| First renderer path | Use a `SurfaceView`-backed Android Canvas proof first | This is the initial proof path for native rendering. OpenGL ES, Filament, or libGDX remain later options if profiling shows Canvas is insufficient. |
| Menus and overlays | Jetpack Compose | HUD may begin in Compose; renderer-drawn HUD can be introduced later only if measurement or visual requirements justify it. |
| Window insets | Consume insets in Compose and renderer layout boundaries | No HUD, controls, or debug panels should sit under status bars, cutouts, gesture areas, or navigation bars. |
| Local settings/progress | Jetpack DataStore first | Highest unlocked level, settings, accessibility options, input preferences, and simple local score tables can start here. |
| Structured score/debug history | Defer Room until needed | Add Room if score history, debug reports, seed records, or migrations become relational enough to justify it. |
| Global scores | Excluded from version 1 | The base game remains offline-complete. Online score sharing requires a later decision record, privacy review, and fairness design. |
| Monetization | Version 1 target is free, no ads, no in-app purchases | Optional paid cosmetic or level packs may be reconsidered after the core game is stable. Do not add Billing dependencies before a deliberate monetization decision. |
| Signing | Use Play App Signing for Play releases | Keep upload keys, keystores, passwords, service accounts, and signing configs outside Git. Debug signing is development-only. |
| Privacy posture | No hidden collection and no analytics SDK by default | User-entered local score names stay local. Debug exports must be explicit and user-controlled. |
| Data Safety draft | Offline base game collects no personal data | Re-check when score names, diagnostics, billing, accounts, ads, analytics, or online features are added. |
| Target audience | General audience, not child-directed by default | Reassess before Play listing and content rating. Avoid claims or UX that imply a children-directed app unless deliberately designed for that policy burden. |
| Content rating assumption | Maze arcade game with no realistic violence | Complete the Play Console questionnaire in Phase 9 using actual implemented content. |
| Native libraries | Avoid native libraries in the skeleton | If OpenGL, Filament, libGDX, or another dependency adds native code, audit ABI coverage, 64-bit support, size, and licenses. |
| Asset manifest location | `docs/ASSET_MANIFEST.md` | Start as Markdown; move to JSON/YAML/TOML only when tooling needs structured input. |
| Placeholder assets | Allowed only when clearly marked temporary | Temporary launcher icons, title art, and renderer materials are not release-ready assets. |

## Deterministic Seed Format

Use a stable, human-readable seed string once maze generation exists:

```text
LAB1-<mode>-L<level>-A<algorithmVersion>-<base36Seed>
```

Example:

```text
LAB1-TIMED-L12-A1-3W5E9Q2
```

The seed should capture enough information to reproduce a generated maze and explain future algorithm migrations. Store the original seed string in bug reports, debug exports, and local score/debug records where relevant.

## First-Release Accessibility Scope

Version 1 should include these accessibility controls unless a later decision record explicitly changes scope:

- Reduced motion.
- Simplified visual effects or low-visual-noise mode.
- High contrast or increased readability mode.
- Sound effects volume control.
- Haptics toggle or intensity control.
- HUD scale or readable HUD sizing where needed.
- Controls that remain outside system gesture and navigation regions.

## Pre-Code Assessment

The documentation review on 2026-07-28 found three project-planning gaps that this file is meant to close:

- Release and Play-readiness phases must use the same `0.0.y` through `0.9.y` model as `docs/ROADMAP.md`.
- Phase 1 must not start while basic Android identity, SDK, renderer, storage, release, monetization, privacy, and Play assumptions are undecided.
- Play Store readiness must be designed throughout development, including signing, Data Safety, target audience, content rating, dependency privacy, asset licensing, and native-library implications.
