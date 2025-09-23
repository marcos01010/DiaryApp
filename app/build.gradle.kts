plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //id("io.realm.kotlin") version "3.0.0"
    id("com.google.devtools.ksp") version "2.0.21-1.0.26"
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
}

android {
    namespace = "com.example.diaryapp"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = "com.example.diaryapp"
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ProjectConfig.extensionVersion
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // Firebase
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.storage.ktx)

    // Room components
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    // Runtime Compose
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Splash API
    implementation(libs.androidx.core.splashscreen)

    // Mongo DB Realm
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.library.sync)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Coil
    implementation(libs.coil.compose)

    // Pager - Accompanist [DEPRECATED]
//    implementation "com.google.accompanist:accompanist-pager:0.27.0"

    // Date-Time Picker
    implementation(libs.core)

    // CALENDAR
    implementation(libs.calendar)

    // CLOCK
    implementation(libs.clock)

    // Message Bar Compose
    implementation(libs.messagebarcompose)

    // One-Tap Compose
    implementation(libs.onetapcompose)

    // Desugar JDK
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}