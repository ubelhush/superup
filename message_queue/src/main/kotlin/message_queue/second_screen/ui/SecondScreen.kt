package message_queue.second_screen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.channels.Channel
import message_queue.second_screen.view_model.SecondViewModel

@Composable
fun SecondScreen(channel: Channel<String>, onCloseRequest: () -> Unit) {
    Window(
        state = WindowState(
            size = DpSize(700.dp, 500.dp)
        ),
        onCloseRequest = onCloseRequest
    ){
        val coroutineScope = rememberCoroutineScope()
        val viewModel = remember {
            SecondViewModel(
                channel = channel,
                coroutineScope = coroutineScope
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){
            Text(text = viewModel.secondState.lastMessage)
        }
    }
}