package com.leinaro.character_search

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.leinaro.core.components.BasicPagingList
import com.leinaro.core.components.SimpleItem
import com.leinaro.core.rememberFlowWithLifecycle
import com.leinaro.character_search.ui_components.NoSearchResults
import com.leinaro.character_search.ui_components.SearchBarUI
import com.leinaro.character_search.ui_state.CharactersSearchUiState

@Composable
fun CharacterSearchScreen(
  navHostController: NavHostController,
  viewModel: CharacterSearchViewModel = viewModel(),
) {
  val uiState: CharactersSearchUiState = viewModel.uiState

  val characterSearchModelState: CharactersSearchUiState.SearchCharactersUiState by rememberFlowWithLifecycle(
    viewModel.userSearchModelState
  )
    .collectAsState(
      initial = CharactersSearchUiState.SearchCharactersUiState(
        "",
        emptyList(),
        false
      )
    )

  SearchBarUI(
    searchText = characterSearchModelState.searchText,
    placeholderText = "",
    onSearchTextChanged = { viewModel.onSearchTextChanged(it) },
    onClearClick = { viewModel.onClearClick() },
    onNavigateBack = { navHostController.popBackStack() },
  ) {
    when (uiState) {
      is CharactersSearchUiState.ShowCharactersListUiState -> {
        uiState.charactersPager?.let { pagingItems ->
          val lazyPagingItems = pagingItems.collectAsLazyPagingItems()
          if (lazyPagingItems.itemCount == 0) {
            NoSearchResults()
            return@SearchBarUI
          }
          BasicPagingList(
            lazyPagingItems = lazyPagingItems,
            { characterUiModel ->
              SimpleItem(
                name = characterUiModel.name,
                thumbnailUrl = characterUiModel.thumbnailUrl,
                item = characterUiModel,
                onItemClick = { item ->
                  navHostController.navigate(route = "character_detail_view?id=${item.id}")
                }
              )
            },
            { characterUiModel ->
              characterUiModel.id
            },
          )
        }
      }
      else -> {
        Log.e("CharacterSearchScreen", "unknown ui state $uiState")
      }
    }
  }
}