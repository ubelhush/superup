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
fun CreateFolderDialog(
    state: FolderDialogState,
    onEvent:(CFDialogEvent)->Unit
) {
    Dialog(
        onDismissRequest = {onEvent(CFDialogEvent.OnDismiss)}
    ) {
        Card {
            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                Text(
                    text = if(state.isFolder) "folder.png name" else "file name"
                )
                TextField(
                    value = state.folderName,
                    onValueChange = {newName->
                             onEvent(CFDialogEvent.OnNameChange(newName))
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                Row {
                    Button(onClick = {
                        onEvent(CFDialogEvent.OnDismiss)
                    }) {
                        Text(
                            text = "cansel"
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = {
                        onEvent(CFDialogEvent.OnConfirm)
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