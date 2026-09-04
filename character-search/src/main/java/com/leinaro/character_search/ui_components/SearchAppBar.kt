package com.leinaro.character_search.ui_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leinaro.core.theme.HeroDexTheme

@Composable
fun SearchBarUI(
  searchText: String,
  placeholderText: String = "",
  onSearchTextChanged: (String) -> Unit = {},
  onClearClick: () -> Unit = {},
  onNavigateBack: () -> Unit = {},
  results: @Composable () -> Unit = {}
) {

  Box {
    Column(
      modifier = Modifier.fillMaxSize()
    ) {

      SearchBar(
        searchText,
        placeholderText,
        onSearchTextChanged,
        onClearClick,
        onNavigateBack
      )
      results()
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
  searchText: String,
  placeholderText: String = "",
  onSearchTextChanged: (String) -> Unit = {},
  onClearClick: () -> Unit = {},
  onNavigateBack: () -> Unit = {}
) {
  var showClearButton by remember { mutableStateOf(false) }
  val keyboardController = LocalSoftwareKeyboardController.current
  val focusRequester = remember { FocusRequester() }

  TopAppBar(
    // The text field goes in the `title` slot, not `actions`: TopAppBarLayout measures
    // `title` with its maxWidth already reduced by navigationIcon/actions, so fillMaxWidth()
    // here only fills the space between them. Putting it in `actions` (as before) let it
    // expand over the whole bar, covering the back button and eating its taps.
    title = {
      OutlinedTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 2.dp)
          .onFocusChanged { focusState ->
            showClearButton = (focusState.isFocused)
          }
          .focusRequester(focusRequester),
        value = searchText,
        onValueChange = onSearchTextChanged,
        placeholder = {
          Text(text = placeholderText)
        },
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = Color.Transparent,
          unfocusedBorderColor = Color.Transparent,
          focusedContainerColor = Color.Transparent,
          unfocusedContainerColor = Color.Transparent,
          cursorColor = MaterialTheme.colorScheme.onSurface,
        ),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
          keyboardController?.hide()
        }),
      )
    },
    navigationIcon = {
      IconButton(onClick = { onNavigateBack() }) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          modifier = Modifier,
          contentDescription = "Volver"
        )
      }
    },
    actions = {
      AnimatedVisibility(
        visible = showClearButton,
        enter = fadeIn(),
        exit = fadeOut()
      ) {
        IconButton(onClick = { onClearClick() }) {
          Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Cerrar"
          )
        }
      }
    })


  LaunchedEffect(Unit) {
    focusRequester.requestFocus()
  }
}

@Composable
fun NoSearchResults() {
  Column(
    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
    horizontalAlignment = CenterHorizontally
  ) {
    Text("No hay resultados")
  }
}

@Preview(showBackground = true)
@Composable
private fun SearchBarUIPreview() {
  HeroDexTheme {
    SearchBarUI("")
  }
}
