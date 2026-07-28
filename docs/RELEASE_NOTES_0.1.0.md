# Labyrinth v0.1.0-skeleton Pre-Release Notes

## Summary

This is an internal GitHub pre-release candidate for the Phase 1 native Android skeleton. It is launchable scaffolding only.

## Android And Platform Changes

- Added Kotlin-first Android app module with application ID `com.jahosi.labyrinth`.
- Set minimum SDK 26, compile SDK 36, target SDK 36.
- Started Android versioning at `versionName` `0.1.0` and `versionCode` `100`.
- Added Gradle wrapper targeting Gradle `9.5.0`.
- Added debug and release build types.
- Added one launch Activity using Jetpack Compose.
- Added native placeholder launch screen with status/navigation bar safe-area handling.
- Added temporary adaptive launcher icons marked temporary in resource comments.

## Gameplay

No gameplay is implemented in this build.

## Permissions, Privacy, And Data

- No permissions are requested.
- No analytics, ads, billing, account sign-in, network behavior, or hidden data collection are included.
- No user data is collected.

## Testing Status

Command checks run on 2026-07-28:

- Passed: `.\gradlew.bat :app:assembleDebug`
- Passed: `.\gradlew.bat test`
- Passed: `.\gradlew.bat :app:assembleRelease`
- Passed: `adb install -r app\build\outputs\apk\debug\app-debug.apk` on device `R5CY101SY7E`
- Passed: launch through `adb shell monkey -p com.jahosi.labyrinth.debug -c android.intent.category.LAUNCHER 1`
- Passed: focused launch log check found no `FATAL EXCEPTION` after startup

Device checks still required before publishing the GitHub pre-release:

- Flip orientation in portrait and landscape.
- Confirm system bars and display cutout areas are respected.

## Play Readiness

This build is not Play-ready. It is structured with Play readiness in mind, but it is only an internal skeleton milestone.
