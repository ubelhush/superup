package message_queue.second_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SecondViewModel(
    private val channel: Channel<String>,
    private val coroutineScope: CoroutineScope
) {
    var secondState by mutableStateOf(SecondState())
        private set
    private val timer=Timer("receive message")

    init {
        receivingHandle()
    }

    private fun receivingHandle(){
        coroutineScope.launch {
            secondState=secondState.copy(
                lastMessage = channel.receive()
            )

            delay(5000)
            receivingHandle()
        }
    }
}

data class SecondState(
    val lastMessage:String=""
)

