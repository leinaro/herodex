package com.leinaro.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>(defaultValue: T) : ViewModel() {

  var uiState by mutableStateOf(defaultValue)
    private set

  fun setValue(value: T) {
    viewModelScope.launch {
      uiState = value
    }
  }
}
