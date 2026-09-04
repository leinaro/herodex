plugins {
  id("leinaro-kotlin-library")
  id("org.jetbrains.kotlin.android")
}

android {
  namespace = "com.leinaro.apis"

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

  // Retrofit
  implementation("com.squareup.retrofit2:retrofit:2.11.0")
  implementation("com.squareup.retrofit2:converter-gson:2.11.0")
  implementation("com.squareup.okhttp3:okhttp:4.12.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

  androidTestImplementation("androidx.test.ext:junit:1.2.1")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

// No Jacoco coverage gate here: this module is entirely DTOs (data/), Hilt wiring (di/)
// and a Retrofit interface (services/) with no method bodies -- there's no measurable
// logic to unit test, and excluding all three would leave checkCoverage evaluating 0/0
// (NaN, vacuously "passing"), which is more confusing than just not gating this module.
