package main_application.dialog.create_folder_dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun SaveReportDialog(
    state: SaveReportDialogState,
    onEvent:(SRDialogEvent)->Unit
) {
    Dialog(
        onDismissRequest = {onEvent(SRDialogEvent.OnDismiss)}
    ) {
        Card {
            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                Text(
                    text = "name"
                )
                TextField(
                    value = state.name,
                    onValueChange = {newName->
                             onEvent(SRDialogEvent.OnNameChange(newName))
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                Row {
                    Button(onClick = {
                        onEvent(SRDialogEvent.OnDismiss)
                    }) {
                        Text(
                            text = "cansel"
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = {
                        onEvent(SRDialogEvent.OnConfirm)
                    }) {
                        Text(
                            text = "confirm"
                        )
                    }
                }
            }
        }
    }
}