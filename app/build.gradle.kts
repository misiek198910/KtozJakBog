import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

android {
    namespace = "mivs.ktozjakbog"
    compileSdk = 35

    signingConfigs {
        create("release") {
            storeFile = file(localProperties.getProperty("MYAPP_RELEASE_STORE_FILE", "brak-sciezki"))
            storePassword = localProperties.getProperty("MYAPP_RELEASE_STORE_PASSWORD", "")
            keyAlias = localProperties.getProperty("MYAPP_RELEASE_KEY_ALIAS", "")
            keyPassword = localProperties.getProperty("MYAPP_RELEASE_KEY_PASSWORD", "")
        }
    }

    tasks.withType<org.gradle.api.tasks.compile.JavaCompile>().configureEach {
        options.compilerArgs.add("-Xlint:deprecation")
    }

    defaultConfig {
        applicationId = "mivs.ktozjakbog"
        minSdk = 27
        targetSdk = 35
        versionCode = 7
        versionName = "1.7"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders += mapOf()
        manifestPlaceholders["adMobAppId"] = ""
    }

    buildTypes {
        getByName("debug") {
            manifestPlaceholders["adMobAppId"] = "ca-app-pub-3940256099942544~3347511713"

            buildConfigField("String", "AD_BANNER_ID", "\"ca-app-pub-3940256099942544/6300978111\"")
            buildConfigField("String", "AD_START_UNIT_ID", "\"ca-app-pub-3940256099942544/9257395921\"")
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")

            val bannerId = localProperties.getProperty("AD_BANNER_ID", "")
            val adStartId = localProperties.getProperty("AD_START_UNIT_ID", "")
            val adMobAppId = localProperties.getProperty("AD_APP_ID", "")

            manifestPlaceholders["adMobAppId"] = adMobAppId

            buildConfigField("String", "AD_BANNER_ID", "\"$bannerId\"")
            buildConfigField("String", "AD_START_UNIT_ID", "\"$adStartId\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.play.services.ads)
    implementation(libs.user.messaging.platform)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}