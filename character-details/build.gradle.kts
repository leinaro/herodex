plugins {
  id("leinaro-android-library")
  id("org.jetbrains.kotlin.android")
}

android {
  namespace = "com.leinaro.character_details"

  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  implementation(project(":core"))
  implementation(project(":domain"))
  androidTestImplementation("androidx.test.ext:junit:1.2.1")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

extra.set(
  JacocoCoverage.coverageDataExtra,
  CoverageTaskParam(
    buildDirectory = buildDir,
    filesToExclude = listOf(
      "**/*Screen*",
      "**/ui_components/*"
    ),
  )
)