package com.leinaro.herodex

import androidx.compose.ui.test.junit4.createComposeRule
import com.leinaro.core.theme.HeroDexTheme
import org.junit.Rule
import org.junit.Test

class AppNavHostKtTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun myTest() {
    // Start the app
    composeTestRule.setContent {
      HeroDexTheme {
        // AppNavHost(navController, scaffoldState)
        //   CharactersListScreen(uiState = fakeUiState, /*...*/)
      }
    }

    // composeTestRule.onNodeWithText("Continue").performClick()

    //  composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
  }
}