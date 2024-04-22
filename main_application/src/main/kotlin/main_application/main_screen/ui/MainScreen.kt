package main_application.main_screen.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import main_application.common_ui.Line
import main_application.common_ui.TopMenu
import main_application.dialog.CreateFolderDialog
import main_application.main_screen.view_model.MainViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen() {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = remember { MainViewModel() }

    Column {
        TopMenu(
            modifier = Modifier
                .fillMaxWidth()
                .height(47.dp)
        )

        Line(isHorizontal = true, color = Color.DarkGray)

        FileTopMenu(
            state = viewModel.mainState,
            onEvent = viewModel::onEvent,
            modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
        )
        Line(isHorizontal = true)

        Row {
            SidePanel(
                state = viewModel.mainState,
                modifier = Modifier.fillMaxHeight().width(144.dp),
                onEvent =viewModel::onEvent
            )

            Line(isHorizontal = false)

            FolderContentUI(
                state=viewModel.mainState,
                onEvent = {event->
                    viewModel.onEvent(event)
                },
                modifier = Modifier.fillMaxSize().weight(8f)
            )
        }
    }

    if (viewModel.dialogState.showDialog){
        CreateFolderDialog(
            state =viewModel.dialogState,
            onEvent=viewModel::onEvent
        )
    }
}