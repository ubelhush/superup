package main_application.dialog.simple_dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun SimpleInfoDialog(
    state: SimpleDialogState,
    onEvent: (SimpleDialogEvent) -> Unit
) {
    Dialog(
        onDismissRequest = {
            onEvent(SimpleDialogEvent.OnDismiss)
        }
    ) {
        Card {
            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.text,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                Button(onClick = {
                    onEvent(SimpleDialogEvent.OnDismiss)
                }) {
                    Text(
                        text = "ok"
                    )
                }
            }
        }
    }
}