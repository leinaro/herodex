package com.leinaro.characters_list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.leinaro.characters_list.ui_state.CharactersListUiState
import com.leinaro.core.components.BasicPagingList
import com.leinaro.core.components.SimpleItem

@Composable
fun CharactersListScreen(
  viewModel: CharactersListViewModel = viewModel(),
  navigateTo: (route: String) -> Unit,
) {
  CharactersList(
    viewModel,
    navigateTo,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersList(
  viewModel: CharactersListViewModel,
  navigateTo: (route: String) -> Unit = {},
  onRefresh: () -> Unit = {},
) {
  val uiState: CharactersListUiState = viewModel.uiState

  PullToRefreshBox(
    isRefreshing = uiState.loading,
    onRefresh = onRefresh,
  ) {
    uiState.charactersPager?.let { pagingItems ->
      val lazyPagingItems = pagingItems.collectAsLazyPagingItems()
      BasicPagingList(
        lazyPagingItems = lazyPagingItems,
        { characterUiModel ->
          SimpleItem(
            name = characterUiModel.name,
            thumbnailUrl = characterUiModel.thumbnailUrl,
            item = characterUiModel,
            onItemClick = { item -> navigateTo("character_detail_view?id=${item.id}") }
          )
        },
        { characterUiModel ->
          characterUiModel.id
        },
      )
    }
  }
}
