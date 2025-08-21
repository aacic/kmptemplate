@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(17))
    }

    jvm()

    androidTarget {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_1_8)
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.json)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kermit)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

//TODO this is a quick fix, find other ways to solve this
tasks.register("testClasses") {
    dependsOn(":shared:jvmTestClasses")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
    }
}

android {
    namespace = "com.kmp.template.android"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}