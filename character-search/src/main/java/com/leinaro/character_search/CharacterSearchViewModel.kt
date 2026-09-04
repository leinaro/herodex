package com.leinaro.character_search

import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.leinaro.core.BaseViewModel
import com.leinaro.core.di.DefaultDispatcher
import com.leinaro.character_search.ui_state.CharactersSearchUiState
import com.leinaro.domain.ui_models.CharacterUiModel
import com.leinaro.domain.usecases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterSearchViewModel @Inject constructor(
  @DefaultDispatcher private val dispatchers: CoroutineDispatcher,
  private val getCharactersUseCase: GetCharactersUseCase,
) : BaseViewModel<CharactersSearchUiState>(CharactersSearchUiState.DefaultState) {

  private val searchText: MutableStateFlow<String> = MutableStateFlow("")
  private var showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
  private var matchedCharacters: MutableStateFlow<List<CharacterUiModel>> =
    MutableStateFlow(arrayListOf())

  val userSearchModelState = combine(
    searchText,
    matchedCharacters,
    showProgressBar
  ) { searchText, matchedCharacters, showProgress ->

    CharactersSearchUiState.SearchCharactersUiState(
      searchText = searchText,
      matchedCharacters = matchedCharacters,
      showProgress = showProgress
    )
  }

  fun getCharacters(nameStartsWith: String) {
    viewModelScope.launch(dispatchers) {
      val pager = getCharactersUseCase.execute(nameStartsWith).flow.map { pagingData ->
        pagingData.map { character ->
          character
        }
      }
      setValue(CharactersSearchUiState.ShowCharactersListUiState(pager))
    }
  }

  fun onSearchTextChanged(changedSearchText: String) {
    searchText.value = changedSearchText
    if (changedSearchText.isEmpty()) {
      return
    }
    getCharacters(changedSearchText)
  }

  fun onClearClick() {
    searchText.value = ""
  }
}