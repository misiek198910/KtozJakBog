# Build and Run Instructions

This repository contains a native Android application written in Java. It uses the
Android View system (XML layouts + `AppCompatActivity`), not Jetpack Compose.
Follow these steps to build and run the project locally.

## Prerequisites
- **Android Studio**: Ladybug (2024.2.1) or newer recommended.
- **Java Development Kit (JDK)**: JDK 17 or newer (the JBR bundled with Android Studio works).
- **Android SDK**: compileSdk 36, targetSdk 36, minSdk 27. Install the "Android 16 (API 36)" platform via the SDK Manager.
- **`local.properties`**: for a signed release build, provide the signing values
  (`MYAPP_RELEASE_STORE_FILE`, `MYAPP_RELEASE_STORE_PASSWORD`, `MYAPP_RELEASE_KEY_ALIAS`,
  `MYAPP_RELEASE_KEY_PASSWORD`) and the AdMob IDs (`AD_APP_ID`, `AD_BANNER_ID`, `AD_START_UNIT_ID`).
  Debug builds use Google's test ad units and need none of these.
- **`google-services.json`**: required in `app/` for Firebase (Analytics / Crashlytics).

## Setup & Build (Android Studio)
1. **Open the Project**: *File > Open...* and select the repository root.
2. **Gradle Sync**: let Android Studio sync; otherwise *File > Sync Project with Gradle Files*.
3. **Build**: *Build > Rebuild Project*. First sync needs an internet connection to
   download Gradle and the dependencies.

## Build from the command line
- Debug APK: `./gradlew :app:assembleDebug`
- Release App Bundle (for Google Play): `./gradlew :app:bundleRelease`
- Release APK: `./gradlew :app:assembleRelease`

Artifacts are written under `app/build/outputs/`.

## Run
1. Start an emulator (API 27+) or connect a physical device with USB debugging enabled.
2. Select the `app` run configuration in the toolbar.
3. Click **Run** (green play icon) or press `Shift + F10` (Windows/Linux) / `Control + R` (macOS).

## Notes
- UI layer: Android Views with XML layouts; screens are plain `AppCompatActivity` subclasses.
- Activities enable edge-to-edge (`EdgeToEdge.enable`) and apply system-bar insets manually.
- Integrations: Google Mobile Ads (banner + app-open), User Messaging Platform (consent),
  Play In-App Updates, Firebase Analytics and Crashlytics.
