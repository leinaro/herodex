package plugins

import AndroidConfig
import Dependencies
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

class LeinaroAndroidCommonPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    target.configureAndroidPlugins()
    target.configureAndroidDependencies()

    target.the<BaseExtension>().apply {
      compileSdkVersion(AndroidConfig.compileSDK)

      buildFeatures.compose = true
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
  plugins.apply("com.android.application")
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
