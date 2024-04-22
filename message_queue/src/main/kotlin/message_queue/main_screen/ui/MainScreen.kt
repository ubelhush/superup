package message_queue.main_screen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.channels.Channel
import message_queue.main_screen.event.MainEvent
import message_queue.main_screen.view_model.MainViewModel

@Composable
fun MainScreen(
    channel: Channel<String>,
    onCloseRequest: () -> Unit
) {
    Window(
        state = WindowState(
            size = DpSize(700.dp, 500.dp)
        ),
        onCloseRequest = onCloseRequest
    ) {
        val coroutineScope = rememberCoroutineScope()
        val viewModel = remember {
            MainViewModel(
                channel = channel,
                coroutineScope = coroutineScope
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = viewModel.mainState.message,
                onValueChange = { newMessage ->
                    viewModel.onEvent(MainEvent.OnMessageChange(newMessage))
                },
                singleLine = true,
                modifier = Modifier.onKeyEvent { keyEvent ->
                    if (keyEvent.key == Key.Enter) {
                        viewModel.onEvent(MainEvent.OnSend)
                    }
                    true
                }
            )
        }
    }
}