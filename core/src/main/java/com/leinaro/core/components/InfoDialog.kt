package com.leinaro.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leinaro.core.R
import com.leinaro.core.theme.HeroDexTheme

@Composable
fun InfoDialog(
  title: String = "",
  message: String = "",
  onDismissRequest: () -> Unit = {}
) {
  AlertDialog(
    onDismissRequest = onDismissRequest,
    title = {
      Text(text = title)
    },
    text = {
      Column {
        Text(message)
      }
    },
    confirmButton = {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(all = 8.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        Button(
          modifier = Modifier.fillMaxWidth(),
          onClick = onDismissRequest
        ) {
          Text(stringResource(R.string.close))
        }
      }
    }
  )
}

@Preview(showBackground = true)
@Composable
private fun InfoDialogPreview() {
  HeroDexTheme {
    InfoDialog()
  }
}
