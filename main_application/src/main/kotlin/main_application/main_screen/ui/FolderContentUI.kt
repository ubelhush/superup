package main_application.main_screen.ui

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import main_application.common_ui.Line
import main_application.main_screen.events.FolderContentEvent
import main_application.main_screen.view_model.MainState
import main_application.utils.Routes

@Composable
fun FolderContentUI(
    state: MainState,
    onEvent: (FolderContentEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        ContextMenu(
            currentPath = state.currentPath,
            onEvent = onEvent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Column {
                    state.folderContent.forEach { fileName ->
                        ContextMenu(
                            fileName=fileName,
                            currentPath = state.currentPath,
                            onEvent=onEvent
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onEvent(FolderContentEvent.OnFolderClick(fileName))
                                    }
                            ) {
                                Text(text = fileName, modifier = Modifier.padding(4.dp))
                            }
                        }
                        Line(true)
                    }
                }
            }
        }

        if (state.folderContent.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "пусто",
                    fontSize = 40.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
private fun ContextMenu(
    fileName: String? = null,
    currentPath:String,
    onEvent: (FolderContentEvent) -> Unit,
    content: @Composable () -> Unit
) {
    if(currentPath.contains(Routes.SYSTEM)){
        content()
    }
    else if (fileName == null || fileName == Routes.SYSTEM) {
        ContextMenuArea(
            items = {
                listOf(
                    ContextMenuItem(label = "create folder") {
                        onEvent(FolderContentEvent.CreateFolder)
                    },
                    ContextMenuItem(label = "create file") {
                        onEvent(FolderContentEvent.CreateFile)
                    }
                )
            }
        ) {
            content()
        }
    } else {
        ContextMenuArea(
            items = {
                listOf(
                    ContextMenuItem(label = "create folder") {
                        onEvent(FolderContentEvent.CreateFolder)
                    },
                    ContextMenuItem(label = "create file") {
                        onEvent(FolderContentEvent.CreateFile)
                    },
                    ContextMenuItem(label = "remove to trash") {
                        onEvent(FolderContentEvent.RemoveToTrash(fileName))
                    },
                    ContextMenuItem(label = "delete") {
                        onEvent(FolderContentEvent.DeleteFile(fileName))
                    }
                )
            }
        ) {
            content()
        }
    }
}