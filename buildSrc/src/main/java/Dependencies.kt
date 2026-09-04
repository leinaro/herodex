/**
 * To define dependencies
 */
object Dependencies {
  val androidxCoreKtx by lazy { "androidx.core:core-ktx:${Versions.androidxCoreKtx}" }
  val androidxAppCompat by lazy { "androidx.appcompat:appcompat:${Versions.androidxAppCompat}" }

  // region Compose (versions come from the BOM, see addComposeDependencies)
  val composeBom by lazy { "androidx.compose:compose-bom:${Versions.composeBom}" }
  const val androidxComposeUi = "androidx.compose.ui:ui"
  const val androidxComposeUiTooling = "androidx.compose.ui:ui-tooling"
  const val androidxComposeToolingPreview = "androidx.compose.ui:ui-tooling-preview"
  const val androidxComposeMaterial3 = "androidx.compose.material3:material3"

  val viewModelCompose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModelCompose}" }
  val runtimeLifecycleCompose by lazy { "androidx.lifecycle:lifecycle-runtime-compose:${Versions.viewModelCompose}" }

  val androidxActivityCompose by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }
  val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navigationCompose}" }

  val pagingRuntime by lazy { "androidx.paging:paging-runtime:${Versions.pagingCompose}" }
  val pagingCompose by lazy { "androidx.paging:paging-compose:${Versions.pagingCompose}" }
  // endregion

  // region Ktx
  val androidxActivityKtx by lazy { "androidx.activity:activity-ktx:${Versions.androidxActivityKtx}" }
  //endregion

  // region Hilt
  val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
  val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }
  val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltCompose}" }
  // endregion

  // region images
  val coil by lazy { "io.coil-kt:coil-compose:${Versions.coil}" }
  // endregion

  val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }

  // Tests
  val jUnit4 by lazy { "junit:junit:${Versions.jUnit4}" }
  val mockk by lazy { "io.mockk:mockk:${Versions.mockk}" }
  val turbine by lazy { "app.cash.turbine:turbine:${Versions.turbine}" }
  val truth by lazy { "com.google.truth:truth:${Versions.truth}" }
  val kotlinCoroutinesTest by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}" }

  // AndroidTests
  const val composeJunit4 = "androidx.compose.ui:ui-test-junit4"
  val hiltAndroidTest by lazy { "com.google.dagger:hilt-android-testing:${Versions.hilt}" }
  val hiltAndroidTestCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }
  const val composeUiTest = "androidx.compose.ui:ui-test-manifest"
}
