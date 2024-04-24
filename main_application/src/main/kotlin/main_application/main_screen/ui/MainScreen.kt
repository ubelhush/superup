package main_application.main_screen.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import main_application.common_ui.Line
import main_application.top_menu.TopMenu
import main_application.dialog.create_folder_dialog.CreateFolderDialog
import main_application.main_screen.events.FolderContentEvent
import main_application.main_screen.events.KeyEvent
import main_application.main_screen.events.UiEvent
import main_application.main_screen.view_model.MainViewModel

@Composable
fun MainScreen(exitApplication: () -> Unit) {
    val coroutineScope= rememberCoroutineScope()
    val viewModel = remember { MainViewModel(coroutineScope) }
    var clipboardManager:ClipboardManager?=null

    LaunchedEffect(key1 = null){
        viewModel.uiEvent.collect{event->
            when(event){
                is UiEvent.OnExitApp -> {
                    exitApplication()
                }
                is UiEvent.OnCopy->{
                    clipboardManager?.setText(AnnotatedString(event.text))
                }
                is UiEvent.OnPut->{
                    val buffer=clipboardManager?.getText()
                    viewModel.onEvent(FolderContentEvent.PutFiles(buffer?.text))
                }
                is UiEvent.OnCut->{
                    clipboardManager?.setText(AnnotatedString(event.text))
                }
                else->{

                }
            }
        }
    }

    Window(
        state = WindowState(
            size = DpSize(700.dp, 600.dp)
        ),
        onKeyEvent = { event ->
            if (event.isAltPressed && event.key == Key.F4) {
                viewModel.onEvent(KeyEvent.OnExitApp)
                true
            }
            else if(event.isCtrlPressed && event.key == Key.C){
                viewModel.onEvent(KeyEvent.OnCopy)
                true
            }
            else if (event.isCtrlPressed && event.key == Key.V){
                viewModel.onEvent(KeyEvent.OnPut)
                true
            }
            else if (event.isCtrlPressed && event.key == Key.X){
                viewModel.onEvent(KeyEvent.OnCut)
                true
            }
            else if (event.isCtrlPressed && event.key == Key.Delete){
                viewModel.onEvent(KeyEvent.OnDelete)
                true
            }
            else {
                false
            }
        },
        onCloseRequest = exitApplication
    ) {
        clipboardManager = LocalClipboardManager.current

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
                    onEvent = viewModel::onEvent
                )

                Line(isHorizontal = false)

                FolderContentUI(
                    state = viewModel.mainState,
                    onEvent = { event ->
                        viewModel.onEvent(event)
                    },
                    modifier = Modifier.fillMaxSize().weight(8f)
                )
            }
        }

        if (viewModel.dialogState.showDialog) {
            CreateFolderDialog(
                state = viewModel.dialogState,
                onEvent = viewModel::onEvent
            )
        }
    }
}