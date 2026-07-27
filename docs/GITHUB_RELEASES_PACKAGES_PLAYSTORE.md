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

- `v0.1.0-docs`: documentation baseline.
- `v0.2.0-skeleton`: first native Android project that opens on a device.
- `v0.3.0-core`: maze generation, scoring, and pathfinding logic with tests.
- `v0.4.0-prototype`: first playable native build.
- `v0.5.0-alpha`: feature-complete internal alpha.
- `v0.8.0-beta`: Play-ready beta candidate.
- `v1.0.0`: first production release candidate or production release.

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

Each phase should ask Play readiness questions, not wait until the end.

### Phase 0: Documentation

Required:

- Offline-first goal documented.
- Monetization assumptions documented.
- GitHub release/package workflow documented.
- Asset licensing policy documented.
- Play policy links recorded.

### Phase 1: Android Skeleton

Required:

- Application ID chosen.
- Minimum SDK and target SDK chosen against current Play requirements.
- Version code/name strategy created.
- Release signing strategy documented but secrets kept outside Git.
- No unnecessary permissions.
- Privacy assumptions recorded.

### Phase 2: Core Logic

Required:

- Pure logic tests added.
- Save-data compatibility assumptions written down.
- Score fairness rules documented if scores might ever be compared.
- Debug seeds available for bug reports.

### Phase 3: Playable Prototype

Required:

- Internal release tag and GitHub pre-release.
- Tested on at least one physical device.
- Known issues listed.
- Asset manifest started.
- Accessibility risks noted.
- No monetization code unless intentionally scoped.

### Phase 4: Enhancement Pass

Required:

- Asset size budget checked.
- Texture compression plan checked.
- Audio licensing checked.
- Haptics and motion settings reviewed.
- Optional purchase boundaries considered for any theme/pack system.

### Phase 5: Robustness And Compliance

Required:

- Current Google Play target API requirement checked.
- Full release build created from clean source.
- Dependency and license audit completed.
- Privacy policy need assessed.
- Data safety answers drafted if required.
- Store listing text and screenshots drafted.
- Billing tests completed if monetization is present.

### Phase 6: Release Candidate

Required:

- GitHub release notes complete.
- Play release notes complete.
- Signed AAB ready for Play Console.
- Test summary attached or linked.
- Known issues accepted.
- Monetization, privacy, permissions, and age/content assumptions rechecked.

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

