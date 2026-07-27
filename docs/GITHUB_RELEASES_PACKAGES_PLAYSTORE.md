# GitHub Releases, Packages, Monetization, And Play Readiness

This document explains how Labyrinth should use GitHub's project facilities while keeping monetization and Google Play readiness at the front of development decisions.

The goal is not to publish too early. The goal is to make every build, dependency, asset, and feature easier to audit when the project becomes a Play Store product.

## Official References

- GitHub Releases overview: https://docs.github.com/en/repositories/releasing-projects-on-github/about-releases
- Managing GitHub Releases: https://docs.github.com/en/repositories/releasing-projects-on-github/managing-releases-in-a-repository
- Automatically generated release notes: https://docs.github.com/en/repositories/releasing-projects-on-github/automatically-generated-release-notes
- GitHub Packages introduction: https://docs.github.com/en/packages/learn-github-packages/introduction-to-github-packages
- Viewing repository packages: https://docs.github.com/en/packages/learn-github-packages/viewing-packages
- GitHub Packages billing: https://docs.github.com/en/billing/concepts/product-billing/github-packages
- Google Play Billing overview: https://developer.android.com/google/play/billing
- Google Play Billing integration: https://developer.android.com/google/play/billing/integrate
- Google Play Billing testing: https://developer.android.com/google/play/billing/test
- Play Console one-time products: https://support.google.com/googleplay/android-developer/answer/16430488
- Play Console subscriptions: https://support.google.com/googleplay/android-developer/answer/140504

## Q&A

### Why document release and monetization before the app exists?

Because release mistakes are easiest to avoid before they become habits. A game that may later accept payments, ship asset packs, or appear on Google Play needs disciplined versioning, asset records, license tracking, dependency hygiene, privacy restraint, and reproducible builds from the beginning.

Even learning prototypes should answer:

- What build is this?
- What changed?
- What assets are inside it?
- What dependencies are inside it?
- Is it free, paid, or preparing for optional purchases?
- Is it safe to distribute to testers?
- What would block Play Store submission today?

### How should this project use GitHub Releases?

GitHub describes releases as deployable software iterations based on Git tags. For Labyrinth, every meaningful build milestone should become either an internal pre-release or a formal release.

Recommended release stages:

- `v0.0.y-docs`: documentation, prerequisites, roadmap, and decision records.
- `v0.1.y-skeleton`: first native Android project that opens on a device.
- `v0.2.y-core`: maze generation, scoring, mode rules, and pathfinding logic with tests.
- `v0.3.y-persistence`: local progress, scores, settings, run-state recovery, and lifecycle resilience.
- `v0.4.y-rendering`: native rendering proof with measured performance.
- `v0.5.y-playable`: input, movement, camera, and collision prototype.
- `v0.6.y-alpha`: core gameplay loop, modes, HUD, minimap, hints, and local score flow.
- `v0.7.y-polish`: visual, audio, haptics, accessibility, and licensed asset pass.
- `v0.8.y-beta`: robustness, debugging, QA, performance, app-size, and license evidence.
- `v0.9.y-store-prep`: monetization decision, Play readiness, signing, listing, and release process.
- `v1.0.0`: first release candidate for Play testing or production review.

Use pre-releases for builds that are not intended for general players. Use full releases only when the notes, artifacts, licenses, privacy posture, and test status are coherent.

### What should be attached to a GitHub Release?

Before Play Store distribution, GitHub releases can attach tester-friendly artifacts and audit documents.

Candidate release assets:

- Debug APK for trusted internal testers only.
- Release-signed APK or AAB only if sharing is safe and intentional.
- Changelog or generated release notes.
- Test summary.
- Known issues.
- Asset manifest snapshot.
- Dependency/license report.
- Screenshots or short gameplay capture.
- Performance notes for tested devices.

Do not attach signing keys, keystores, Play Console credentials, service account files, private upload keys, or paid/licensed source assets that should not be redistributed separately.

### What release notes should include?

Every release note should be useful to a tester, future maintainer, and Play Store reviewer.

Suggested structure:

- Summary.
- New gameplay features.
- Android/platform changes.
- Asset changes.
- Monetization changes.
- Privacy/data changes.
- Accessibility changes.
- Performance changes.
- Bug fixes.
- Known issues.
- Tested devices.
- Play readiness status.

If a release changes purchases, ads, data collection, permissions, age targeting, save data, or network behavior, call that out explicitly.

### How should tags and versions work?

Use semantic versioning once code begins:

- Major version: incompatible save-data, monetization, or gameplay contract changes.
- Minor version: new features, modes, assets, renderer upgrades, or Play-track milestones.
- Patch version: bug fixes, tuning, small asset corrections, documentation-only release fixes.

Android versioning will eventually need:

- Human-readable `versionName`, such as `0.4.0-alpha.2`.
- Monotonic integer `versionCode`, required for Android updates.

The Git tag, Android `versionName`, release title, changelog, and Play Console release notes should agree.

### How should this project use GitHub Packages?

GitHub Packages can host software packages privately or publicly and can be used with GitHub Actions workflows.

For Labyrinth, Packages should be considered for:

- Internal shared Kotlin/Gradle modules if the project later splits reusable libraries out of the app.
- Container images for repeatable build tooling, asset-processing tools, or documentation tooling.
- Private package experiments that should not be released as app artifacts.

Do not use GitHub Packages as the normal delivery path for Android player builds. Players should receive production builds through Google Play when the app is ready. GitHub Packages is for developer artifacts and dependencies, not the store channel.

### What should not go into GitHub Packages?

Avoid publishing:

- Android signing material.
- Paid asset source files.
- Private commercial texture or audio packs.
- Secrets, API keys, Play Console service accounts, or keystores.
- Player data, telemetry exports, or purchase records.
- App builds that should instead be release assets or Play Console uploads.

Also remember that GitHub Packages has billing and storage implications. Check usage and visibility before publishing large packages.

### How do GitHub Releases and Packages relate to Play Store readiness?

GitHub is the engineering record. Google Play is the customer distribution channel.

The GitHub release should answer whether the build is ready to move to a Play testing track:

- Does the release have a matching tag?
- Was it built from clean source?
- Were tests run?
- Are assets licensed?
- Are dependencies known?
- Are permissions justified?
- Are monetization changes documented?
- Are privacy/data changes documented?
- Is the build signed using the correct release process?
- Are Play listing notes ready?

If the answer is no, the release can still exist as a pre-release, but it should not be promoted as Play-ready.

## Monetization-First Product Rules

Monetization should be planned early, even if no paid features are added in version 1.

### What monetization models are possible?

Options to consider:

- Free game with no monetization.
- Paid upfront app.
- Free game with optional one-time purchases.
- Free game with subscriptions.
- Free game with cosmetic expansions.
- Free game with paid level/theme packs.
- Ads-supported game.

Initial recommendation: keep version 1 simple and offline-friendly. Avoid ads and subscriptions until the core game is strong, accessible, stable, and clearly worth extending. If monetization is added, prefer transparent optional purchases that do not damage the learning, offline, or accessibility goals.

### What can be monetized without harming the game?

Good candidates:

- Cosmetic maze themes.
- Optional texture packs.
- Optional sound packs.
- Level collections.
- Challenge packs.
- Supporter upgrade with no gameplay advantage.

Risky candidates:

- Paid hints that affect scoring fairness.
- Paid lives or time boosts.
- Purchases that make leaderboards unfair.
- Required online checks for an otherwise offline game.
- Dark-pattern subscriptions.
- Ads that interrupt active play.

### What does Google Play Billing imply?

If the app sells digital products or subscriptions through Google Play, it must use Google Play's billing system and the Play Billing Library. Product setup also happens in Play Console, such as one-time products and subscriptions.

Design implications:

- Purchase state must be handled carefully and recoverably.
- The game must restore entitlements.
- Offline play must behave predictably when purchase state cannot be refreshed.
- Paid content must not disappear because a network request failed.
- UI must clearly describe what the player is buying.
- Tests must cover successful purchase, cancellation, pending purchase, refund/revocation, restore, and unavailable billing service.

Do not add Billing dependencies until a monetization model is deliberately chosen. But do design asset packs, level packs, and save data so that optional entitlements can be added cleanly later.

### What monetization records should be kept?

For every paid product idea:

- Product ID.
- Product type: paid app, one-time product, subscription, or non-monetized feature.
- Player-facing name.
- Player-facing description.
- Entitlement granted.
- Whether it affects gameplay, score, or progression.
- Whether it works offline.
- Restore behavior.
- Refund/revocation behavior.
- Test cases.
- Play Console setup status.
- Legal/privacy notes.

### How should monetization affect asset work?

Every asset should be categorized as:

- Base game asset.
- Free unlockable asset.
- Paid cosmetic asset.
- Paid level/theme asset.
- Store listing asset.
- Marketing-only asset.

Paid assets need extra discipline:

- Clear license for commercial redistribution.
- Entitlement mapping.
- No hidden dependency on server availability.
- No accidental inclusion in free-only builds if product separation is required.
- Clear release notes when changed.

## Play Store Readiness Gates

Each phase should ask Play readiness questions, not wait until the end. These gates follow the authoritative `0.0.y` through `0.9.y` roadmap in `docs/ROADMAP.md`.

### Phase 0 / `0.0.y`: Documentation And Decisions

Required:

- Offline-first goal documented.
- Monetization assumptions documented.
- GitHub release/package workflow documented.
- Asset licensing policy documented.
- Play policy links recorded.
- `docs/DECISIONS.md` records Phase 1 defaults for app ID, SDK levels, renderer, orientation, privacy, signing, monetization, Data Safety assumptions, target audience, and asset manifest location.

### Phase 1 / `0.1.y`: Android Skeleton

Required:

- Application ID chosen.
- Minimum SDK, target SDK, and compile SDK chosen against current Play requirements.
- Version code/name strategy created.
- Release signing strategy documented but secrets kept outside Git.
- No unnecessary permissions.
- Privacy assumptions recorded.
- Placeholder app icon and launch screen clearly marked as temporary.
- Initial window-inset and orientation behavior tested.

### Phase 2 / `0.2.y`: Core Logic

Required:

- Pure logic tests added.
- Save-data compatibility assumptions written down.
- Score fairness rules documented if scores might ever be compared.
- Debug seeds available for bug reports.
- Any divergence from the original web-game rules documented.

### Phase 3 / `0.3.y`: Persistence And Lifecycle

Required:

- Local progress/settings storage implemented.
- Local score storage implemented or explicitly deferred with a replacement plan.
- Recoverable run-state contract documented.
- Orientation, Activity recreation, pause/resume, and process recreation behavior tested.
- Data Safety assumptions rechecked against actual local storage behavior.

### Phase 4 / `0.4.y`: Native Rendering Proof

Required:

- Renderer decision documented with rationale and replacement criteria.
- Renderer pauses and resumes without burning background resources.
- Asset manifest started before non-trivial textures, icons, sounds, or materials are added.
- Initial app-size and texture-size budgets recorded.
- Native-library, ABI, and 64-bit implications reviewed if any renderer dependency introduces native code.

### Phase 5 / `0.5.y`: Playable Movement Prototype

Required:

- Internal release tag and GitHub pre-release.
- Tested on at least one physical device.
- Known issues listed.
- Input-mode switching risks noted.
- Motion-sickness, collision fairness, and control safe-area risks noted.
- No online scoreboard, ads, analytics, or monetization code unless intentionally scoped.

### Phase 6 / `0.6.y`: Core Gameplay Alpha

Required:

- Timed, High Score, and Practice mode behavior documented.
- Local-only score behavior matches privacy and fairness assumptions.
- HUD, minimap, menu, and score overlays tested against window insets.
- Store claims remain internal-only and do not imply unreleased features.
- Known issues and missing web-feature parity items listed.

### Phase 7 / `0.7.y`: Visual, Audio, Haptics, And Accessibility

Required:

- Asset size budget checked.
- Texture compression plan checked.
- Audio licensing checked.
- Haptics and motion settings reviewed.
- Optional purchase boundaries considered for any theme/pack system.
- Accessibility settings implemented for known risks or blockers recorded.
- Store-screenshot direction reviewed for honest feature representation.

### Phase 8 / `0.8.y`: Robustness, Debugging, QA, And Performance

Required:

- Current Google Play target API requirement checked.
- Debug report and seed replay avoid sensitive data.
- Test matrix results recorded.
- App-size and asset-size reports recorded.
- Dependency and license audit completed.
- Privacy policy need assessed.
- Data safety answers drafted if required.
- Play policy checklist pass completed.
- GitHub pre-release includes test summary and Play readiness status.

### Phase 9 / `0.9.y`: Monetization, Store Prep, And Play Compliance

Required:

- Full release build created from clean source.
- Release build is not debuggable.
- Signed AAB process documented.
- Version code/name verified.
- Current Google Play target API requirement checked again.
- App permissions reviewed.
- Privacy policy need assessed and policy drafted if required.
- Data Safety answers match actual app behavior.
- Store listing text and screenshots drafted.
- Billing tests completed if monetization is present.
- Target audience and content rating assumptions documented from actual implementation.
- Dependency privacy, SDK, native-library, asset-license, and app-size reviews complete.

### Version `1.0.0`: Release Candidate

Required:

- GitHub release notes complete.
- Play release notes complete.
- Signed AAB ready for Play Console.
- Test summary attached or linked.
- Known issues accepted.
- Monetization, privacy, permissions, and age/content assumptions rechecked.
- No known blocker remains for Play closed testing.

## Release Checklist

Before any GitHub release:

- Version updated.
- Changelog updated.
- README development blog updated.
- Asset pipeline and manifest updated.
- Tests run or skipped with reason.
- Known issues updated.
- Build artifact produced intentionally.
- Release notes include Play readiness status.

Before any Play testing upload:

- Release built from a tagged commit or traceable CI run.
- Release signing handled safely.
- Target SDK checked against current Play requirement.
- App permissions reviewed.
- Privacy/data behavior reviewed.
- Monetization behavior reviewed.
- Billing integration tested if present.
- Store listing assets checked.
- Accessibility basics checked.
- Device compatibility checked.

Before any monetized release:

- Play Console payment and product setup complete.
- Product IDs stable.
- Purchase UI is clear.
- Restore behavior works.
- Refund/revocation behavior works.
- Offline behavior is predictable.
- Paid assets and entitlements are documented.
- No pay-to-win behavior affects competitive scores unless separately isolated.

## Documentation Habit

When release, package, monetization, or Play-readiness behavior changes, update:

- `README.md` development blog.
- This document.
- `docs/PREREQUISITES.md` if the change affects setup.
- `docs/ASSET_PIPELINE.md` if the change affects paid/free assets.
- Future changelog/release notes.
