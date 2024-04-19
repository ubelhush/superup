package terminal.main_screen.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import common_ui.Line
import terminal.main_screen.events.TerminalEvent
import terminal.main_screen.view_model.MainViewModel

@Composable
fun MainScreen() {
    val coroutineScope= rememberCoroutineScope()
    val viewModel= remember { MainViewModel(coroutineScope) }
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .background(Color.DarkGray)
        ) {
            Text(
                text = viewModel.mainState.answerText,
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(ScrollState(0))
                )
        }

        Line(isHorizontal = true)

        TextField(
            value = viewModel.mainState.commandText,
            onValueChange = {newText->
                 viewModel.onEvent(TerminalEvent.OnTextChange(newText))
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .onKeyEvent { keyEvent->
                if(keyEvent.key == Key.Enter){
                    viewModel.onEvent(TerminalEvent.ExecuteCommand)
                }
                true
            }
        )
    }
}