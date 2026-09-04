package plugins

import AndroidConfig
import Dependencies
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class LeinaroAndroidLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    target.configureAndroidPlugins()
    target.configureAndroidDependencies()

    // Pin the JDK used to run kotlinc/kapt/ksp, independent of whatever JDK launched Gradle
    // itself -- otherwise Kotlin's jvmTarget silently follows the Gradle daemon's JDK, which
    // can drift out of sync with the Java compileOptions below and fail the build.
    target.the<KotlinAndroidProjectExtension>().jvmToolchain(17)

    target.the<LibraryExtension>().apply {
      compileSdk = AndroidConfig.compileSDK
      buildFeatures {
        compose = true
      }
      defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      }
      compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
      }
    }
  }
}

private fun Project.configureAndroidPlugins() {
  plugins.apply("com.android.library")
  plugins.apply("org.jetbrains.kotlin.android")
  plugins.apply("org.jetbrains.kotlin.plugin.compose")
  plugins.apply("com.google.devtools.ksp")
  plugins.apply("dagger.hilt.android.plugin")
  plugins.apply("jacoco")
  plugins.apply("plugins.jacoco-report")
}

private fun Project.configureAndroidDependencies() = dependencies {
  add("implementation", Dependencies.androidxCoreKtx)
  add("implementation", Dependencies.androidxAppCompat)

  // Ktx
  add("implementation", Dependencies.androidxActivityKtx)

  addComposeDependencies()
  addCoilDependencies()
  addHiltDependencies()
  addTestDependencies()
  addAndroidTestDependencies()
}
