package message_queue.main_screen.event

sealed class MainEvent {
    data class OnMessageChange(val message:String):MainEvent()
    data object OnSend:MainEvent()
}