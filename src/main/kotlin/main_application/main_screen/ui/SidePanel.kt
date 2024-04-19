package main_application.main_screen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common_ui.Line
import main_application.main_screen.events.SidePanelEvent
import main_application.main_screen.view_model.MainState

@Composable
fun SidePanel(
    state: MainState,
    onEvent: (SidePanelEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ){
            state.mainPaths.forEach { path->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier=Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(SidePanelEvent.OnFolderClick(path.value))
                        }
                ){
                    Text(text = path.key,
                        modifier=Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }
                Line(isHorizontal = true)
            }
        }
    }
}