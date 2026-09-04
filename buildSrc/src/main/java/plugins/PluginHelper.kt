package plugins

import Dependencies
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.addCoilDependencies() {
  // Coil
  add("implementation", Dependencies.coil)
}

fun DependencyHandlerScope.addHiltDependencies() {
  // Hilt
  add("implementation", Dependencies.hiltAndroid)
  add("ksp", Dependencies.hiltCompiler)
  add("implementation", Dependencies.hiltNavigationCompose)
}

fun DependencyHandlerScope.addComposeDependencies() {
  // Compose BOM: pins the version of every androidx.compose.* artifact below
  add("implementation", platform(Dependencies.composeBom))
  add("androidTestImplementation", platform(Dependencies.composeBom))

  add("implementation", Dependencies.androidxActivityCompose)
  add("implementation", Dependencies.androidxComposeMaterial3)
  add("implementation", Dependencies.androidxComposeUiTooling)
  add("implementation", Dependencies.androidxComposeUi)
  add("implementation", Dependencies.androidxComposeToolingPreview)
  add("implementation", Dependencies.viewModelCompose)
  add("implementation", Dependencies.runtimeLifecycleCompose)
  add("implementation", Dependencies.pagingCompose)
  add("implementation", Dependencies.pagingRuntime)
  add("implementation", Dependencies.navigationCompose)
}

fun DependencyHandlerScope.addTestDependencies() {
  // Test
  add("testImplementation", Dependencies.jUnit4)
  add("testImplementation", Dependencies.mockk)
  // flows
  add("testImplementation", Dependencies.turbine)
  add("testImplementation", Dependencies.truth)
  add("testImplementation", Dependencies.kotlinCoroutinesTest)
}

fun DependencyHandlerScope.addAndroidTestDependencies() {
  // Test
  add("androidTestImplementation", Dependencies.composeJunit4)
  add("androidTestImplementation", Dependencies.hiltAndroidTest)
  add("kspAndroidTest", Dependencies.hiltAndroidTestCompiler)
  add("debugImplementation", Dependencies.composeUiTest)
}
