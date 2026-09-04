package com.leinaro.core.components.topappbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.leinaro.core.R

@Composable fun BackIconButton(
  onClick: () -> Unit = {}
) {
  IconButton(onClick = { onClick() }) {
    Icon(
      imageVector = Icons.AutoMirrored.Filled.ArrowBack,
      contentDescription = "Volver"
    )
  }
}

@Composable
fun InfoIconButton(
  onClick: () -> Unit = {}
) {
  IconButton(onClick = { onClick() }) {
    Icon(Icons.Default.Info, contentDescription = stringResource(R.string.information))
  }
}

@Composable
fun SearchIconButon(
  onClick: () -> Unit = {}
) {
  IconButton(onClick = { onClick() }) {
    Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search))
  }
}

@Composable
fun SearchTextField(
  searchText: String = "",
  placeholderText: String = "",
  onSearchTextChanged: (String) -> Unit = {},
  onClearClick: () -> Unit = {},
) {
  var showClearButton by remember { mutableStateOf(false) }
  val keyboardController = LocalSoftwareKeyboardController.current
  val focusRequester = remember { FocusRequester() }

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
    trailingIcon = {
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
    },
    maxLines = 1,
    singleLine = true,
    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    keyboardActions = KeyboardActions(onDone = {
      keyboardController?.hide()
    }),
  )
}
