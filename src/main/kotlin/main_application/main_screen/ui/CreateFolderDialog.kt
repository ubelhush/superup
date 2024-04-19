package main_application.main_screen.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import main_application.main_screen.events.DialogEvent
import main_application.main_screen.view_model.DialogState

@Composable
fun CreateFolderDialog(
    state: DialogState,
    onEvent:(DialogEvent)->Unit
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Card {
            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                Text(
                    text = if(state.isFolder) "folder name" else "file name"
                )
                TextField(
                    value = state.folderName,
                    onValueChange = {newName->
                             onEvent(DialogEvent.OnNameChange(newName))
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                Row {
                    Button(onClick = {
                        onEvent(DialogEvent.OnDismiss)
                    }) {
                        Text(
                            text = "cansel"
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = {
                        onEvent(DialogEvent.OnConfirm)
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