plugins {
  id("leinaro-android-common")
  id("jacoco")
  id("plugins.jacoco-report")
}

android {
  namespace = "com.leinaro.herodex"

  defaultConfig {
    applicationId = "com.leinaro.herodex"
    versionCode = 1
    versionName = "1.0"
  }

  buildTypes {
    release {
      // This is a portfolio/demo app, not published to any store: release builds
      // are signed with the debug key so `assembleRelease` works out of the box.
      signingConfig = signingConfigs.getByName("debug")
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
      isDebuggable = false
    }
    debug {
      isMinifyEnabled = false
      isDebuggable = true
    }
  }
}

dependencies {
  implementation(project(":core"))
  implementation(project(":characters-list"))
  implementation(project(":character-search"))
  implementation(project(":character-details"))

  androidTestImplementation("androidx.test.ext:junit:1.2.1")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

tasks.withType<Test> {
  configure<JacocoTaskExtension> {
    isIncludeNoLocationClasses = true
    excludes = listOf("jdk.internal.*", "**/*ScreenKt")
  }
}

extra.set(
  JacocoCoverage.coverageDataExtra,
  CoverageTaskParam(buildDirectory = buildDir)
)
