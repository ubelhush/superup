package main_application.main_screen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import main_application.common_ui.Line
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
                .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 16.dp)
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

            Spacer(
                modifier=Modifier.weight(1f)
            )

            if (state.connectedDevices.isEmpty()){
                Button(
                    onClick = {onEvent(SidePanelEvent.GetConnectedDevices)},
                    modifier=Modifier.fillMaxWidth()
                ){
                    Text(text = "get devices")
                }
            }else{
                Line(
                    isHorizontal = true,
                    width = 2.dp
                    )

                state.connectedDevices.forEach { device->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier=Modifier
                            .fillMaxWidth()
                            .clickable {
                                onEvent(SidePanelEvent.OnFolderClick(device.path))
                            }
                    ){
                        Text(text = device.name,
                            modifier=Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                    }
                    Line(isHorizontal = true)
                }
            }
        }
    }
}