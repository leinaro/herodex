package com.leinaro.core.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.leinaro.core.R

@Composable
fun <T> SimpleItem(
  name: String,
  thumbnailUrl: String,
  item: T,
  onItemClick: ((item: T) -> Unit) = {},
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp, 8.dp)
      .clickable { onItemClick(item) },
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      AsyncImage(
        model = thumbnailUrl,
        placeholder = painterResource(R.drawable.image_placeholder),
        contentDescription = null,
        modifier = Modifier
          .size(70.dp)
          .padding(0.dp),
        contentScale = ContentScale.Fit,
        error = painterResource(R.drawable.image_placeholder),
        onError = {
          Log.e(javaClass.name, it.result.throwable.message.orEmpty())
        }
      )
      Column(
        modifier = Modifier
          .weight(8f)
          .padding(8.dp)
      ) {
        Text(
          text = "Name",
          style = MaterialTheme.typography.labelSmall
        )
        Text(
          text = name,
          style = MaterialTheme.typography.titleLarge
        )
      }
      Icon(
        modifier = Modifier.weight(1f),
        painter = painterResource(id = R.drawable.chevron_right_48px),
        contentDescription = null
      )
    }
  }
}
