package com.leinaro.core.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.leinaro.core.components.topappbar.BackIconButton
import com.leinaro.core.components.topappbar.InfoIconButton
import com.leinaro.core.components.topappbar.SearchIconButon
import com.leinaro.core.components.topappbar.SearchTextField
import com.leinaro.core.theme.HeroDexTheme

sealed class AppTopBarData(var title: String = "HeroDex") {
  object None : AppTopBarData()
  object Default : AppTopBarData()
  data class CanGoBack(
    val onBackClick: () -> Unit,
  ) : AppTopBarData()

  data class SearchBar(
    val onSearchTextChanged: (String) -> Unit = {},
    val onBackClick: () -> Unit = {},
  ) : AppTopBarData("")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
  data: AppTopBarData = AppTopBarData.Default,
  onSearchClick: () -> Unit = {},
) {
  var showInfoDialog by remember { mutableStateOf(false) }
  if (data != AppTopBarData.None) {
    TopAppBar(
      title = { Text(data.title) },
      navigationIcon = { NavigationIconByType(data) },
      actions = {
        ActionsByType(
          data = data,
          onInfoClick = { showInfoDialog = !showInfoDialog },
          onSearchClick = { onSearchClick() })
      }
    )
  }

  if (showInfoDialog) {
    InfoDialog(
      title = "HeroDex",
      message = "HeroDex is a portfolio app by Leinaro. All characters and comics shown are " +
        "original, fictional demo data served from a self-hosted mock API.",
      onDismissRequest = { showInfoDialog = !showInfoDialog }
    )
  }
}

@Composable
fun ActionsByType(
  data: AppTopBarData,
  onSearchClick: () -> Unit = {},
  onInfoClick: () -> Unit = {},
  searchText: String = "",
  placeholderText: String = "",
  onSearchTextChanged: (String) -> Unit = {},
  onClearClick: () -> Unit = {},
) {
  when (data) {
    is AppTopBarData.None -> {}
    is AppTopBarData.SearchBar -> {
      SearchTextField(
        searchText, placeholderText, onSearchTextChanged, onClearClick
      )
    }
    is AppTopBarData.CanGoBack -> {
      InfoIconButton(onClick = { onInfoClick() })
    }
    AppTopBarData.Default -> {
      SearchIconButon(onClick = { onSearchClick() })
      InfoIconButton(onClick = { onInfoClick() })
    }
  }
}

@Composable
fun NavigationIconByType(data: AppTopBarData) {
  when (data) {
    is AppTopBarData.SearchBar -> {
      BackIconButton(onClick = { data.onBackClick() })
    }
    is AppTopBarData.CanGoBack -> {
      BackIconButton(onClick = { data.onBackClick() })
    }
    is AppTopBarData.Default -> {}
    is AppTopBarData.None -> {}
  }
}

@Preview(showBackground = true)
@Composable
private fun AppTopBarPreview() {
  HeroDexTheme {
    AppTopBar(data = AppTopBarData.SearchBar())
  }
}
