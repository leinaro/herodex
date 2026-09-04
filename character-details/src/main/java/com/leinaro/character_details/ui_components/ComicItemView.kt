package com.leinaro.character_details.ui_components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.leinaro.core.R
import com.leinaro.core.theme.Black
import com.leinaro.core.theme.White
import com.leinaro.domain.ui_models.ComicUiModel

@Composable
fun ComicItemView(comic: ComicUiModel) {
  val context = LocalContext.current

  Card(
    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    modifier = Modifier
      .padding(8.dp)
      .clickable {
        Toast
          .makeText(
            context,
            "Ups! esta funcionalidad aún no esta lista", Toast.LENGTH_LONG
          )
          .show()
      }
  ) {
    Column(
      modifier = Modifier.width(171.dp),
      verticalArrangement = Arrangement.SpaceBetween,
    ) {
      AsyncImage(
        model = comic.imageUrl,
        placeholder = painterResource(R.drawable.image_placeholder),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
        error = painterResource(R.drawable.image_placeholder),
        onError = {
          Log.e("ComicItemView", it.result.throwable.message.orEmpty())
        }
      )
      Text(
        modifier = Modifier
          .background(Black)
          .fillMaxWidth()
          .fillMaxHeight()
          .padding(16.dp),
        textAlign = TextAlign.Center,
        text = comic.name,
        color = White,
        style = MaterialTheme.typography.bodyLarge,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

@Preview @Composable fun ComicItemViewPreview() {
  ComicItemView(ComicUiModel("adsfsdfasf adfdsfaf asdf sd sdfg sdf sdfs dsf"))
}