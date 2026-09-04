// Top-level build file where you can add configuration options common to all sub-projects/modules.
//
// AGP, the Kotlin Gradle plugin, KSP and Hilt are NOT declared here: they're on buildSrc's own
// classpath (see buildSrc/build.gradle.kts) and applied imperatively by the leinaro-* convention
// plugins (buildSrc/src/main/java/plugins). Declaring them again via `plugins { ... }` here would
// conflict with that classpath (Gradle can't reconcile "already present" with "resolve via portal").

plugins {
  id("jacoco")
}

plugins.apply("plugins.jacoco-global-report")
