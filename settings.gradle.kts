pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
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
