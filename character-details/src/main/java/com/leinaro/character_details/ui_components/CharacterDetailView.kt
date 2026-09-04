package com.leinaro.character_details.ui_components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.leinaro.core.R
import com.leinaro.core.theme.Black
import com.leinaro.core.theme.CgRed
import com.leinaro.core.theme.White
import com.leinaro.domain.ui_models.CharacterUiModel
import com.leinaro.domain.ui_models.ComicUiModel

@Composable fun CharacterDetailView(
  characterUiModel: CharacterUiModel,
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxWidth(),
    verticalArrangement = Arrangement.SpaceBetween,
    horizontalAlignment = Alignment.Start,
  ) {
    item {
      AsyncImage(
        model = characterUiModel.landscapeUrl,
        placeholder = painterResource(R.drawable.image_placeholder),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
        error = painterResource(R.drawable.image_placeholder),
        onError = {
          Log.e(javaClass.name, it.result.throwable.message.orEmpty())
        }
      )
      Text(
        modifier = Modifier
          .background(Black)
          .fillMaxWidth()
          .padding(16.dp, 24.dp),
        textAlign = TextAlign.Center,
        text = characterUiModel.name,
        color = White,
        style = MaterialTheme.typography.titleLarge
      )

      Text(
        modifier = Modifier
          .background(Black)
          .fillMaxWidth()
          .padding(16.dp, 4.dp),
        textAlign = TextAlign.Justify,
        text = characterUiModel.description,
        color = White,
        style = MaterialTheme.typography.bodyLarge
      )
    }

    item {
      var isExpanded by rememberSaveable { mutableStateOf(false) }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(CgRed)
          .clickable { isExpanded = !isExpanded },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Text(
          color = White,
          text = "Comics (${characterUiModel.comics.size})",
          modifier = Modifier
            .weight(8f)
            .padding(8.dp),
          style = MaterialTheme.typography.titleMedium
        )
        Text(
          textAlign = TextAlign.End,
          color = White,
          text = "(${characterUiModel.comics.size})",
          modifier = Modifier
            .weight(8f)
            .padding(8.dp),
          style = MaterialTheme.typography.titleMedium
        )
        Icon(
          imageVector = Icons.Filled.ArrowDropDown,
          modifier = Modifier.weight(1f),
          contentDescription = null
        )
      }
      AnimatedVisibility(visible = isExpanded) {
      LazyHorizontalGrid(
        modifier = Modifier.height(350.dp), // itemHeight * rowCount + verticalSpacing * (rowCount - 1)
        rows = GridCells.Fixed(1)
      ) {
        items(characterUiModel.comics) { comic ->
          ComicItemView(comic)
        }
      }
       }
    }
  }
}

@Preview @Composable fun PreviewMessageCard() {
  CharacterDetailView(
    CharacterUiModel(
      1,
      "Android",
      "Android",
      "https://api.dicebear.com/9.x/bottts/svg?seed=android",
      comics = listOf(ComicUiModel(name = "adsf asdf aff f fafed fsd as dfadsf")),
    )
  )
}