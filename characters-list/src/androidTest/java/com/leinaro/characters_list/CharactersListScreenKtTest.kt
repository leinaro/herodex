package com.leinaro.characters_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import com.leinaro.core.theme.HeroDexTheme
import com.leinaro.characters_list.ui_state.CharactersListUiState
import com.leinaro.domain.ui_models.CharacterUiModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CharactersListScreenKtTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  lateinit var viewModel: CharactersListViewModel

  @Before
  fun init() {
    hiltRule.inject()
  }

  @Test
  fun myTest() {
    val uiState = CharactersListUiState(
      charactersPager = flow {
        emit(
          PagingData.from(
            listOf(
              CharacterUiModel(
                1,
                "hello",
                "https://api.dicebear.com/9.x/bottts/svg?seed=hello",
                "https://api.dicebear.com/9.x/bottts/svg?seed=hello",
              ), CharacterUiModel(
                1,
                "hello",
                "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80",
                "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80",
              ), CharacterUiModel(
                1, "hello", "", ""
              )
            )
          )
        )
      }
    )
    composeTestRule.setContent {
      viewModel= hiltViewModel() // Add this line

      HeroDexTheme {
        CharactersList(viewModel)
      }
    }

//     composeTestRule.onNodeWithText("Continue").performClick()
    composeTestRule.onNodeWithText("hello").assertIsDisplayed()
  }
}