repositories {
  mavenCentral()
  gradlePluginPortal()
  google()
}
plugins {
  `kotlin-dsl`
}

// NOTE: these versions can't reference buildSrc's own Versions.kt (chicken-and-egg:
// this script configures how Versions.kt gets compiled), so they're duplicated here.
// Keep in sync with buildSrc/src/main/java/Versions.kt.
private object BuildSrcVersions {
  const val gradlePlugin = "8.7.3"
  const val kotlin = "2.0.21"
  const val ksp = "2.0.21-1.0.28"
  const val hilt = "2.52"
}

dependencies {
  implementation(kotlin("gradle-plugin", BuildSrcVersions.kotlin))
  implementation(
    "org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:${BuildSrcVersions.kotlin}"
  )
  implementation("com.android.tools.build:gradle:${BuildSrcVersions.gradlePlugin}")
  implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:${BuildSrcVersions.ksp}")
  implementation("com.google.dagger:hilt-android-gradle-plugin:${BuildSrcVersions.hilt}")
  implementation(gradleApi())
  implementation(localGroovy())
}
