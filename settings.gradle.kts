pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
}

plugins {
  // Lets Gradle auto-download a JDK 17 toolchain if the machine doesn't already have one,
  // instead of failing the build (see buildSrc's jvmToolchain(17) convention plugin config).
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

include(":app")
include(":core")

// presentation layers
include(":characters-list")
include(":character-search")
include(":character-details")

// Domain layers
include(":domain")
include(":apis")

// Data layer
include(":data")

rootProject.name = "HeroDex"
