package message_queue.main_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import message_queue.main_screen.event.MainEvent

class MainViewModel(
    private val channel: Channel<String>,
    private val coroutineScope: CoroutineScope
) {
    var mainState by mutableStateOf(MainState())
        private set

    fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.OnSend -> {
                val temporary=mainState.message

                coroutineScope.launch {
                    channel.send(temporary)
                }

                mainState=mainState.copy(
                    message = ""
                )
            }
            is MainEvent.OnMessageChange->{
                mainState=mainState.copy(
                    message = event.message
                )
            }
        }
    }
}

data class MainState(
    val message:String=""
)