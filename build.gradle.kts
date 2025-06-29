// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    dependencies{
        classpath(libs.realm.kotlin.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.26" apply false

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.google.services) apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
//    id 'io.realm.kotlin' version '1.16.0' apply false
}

