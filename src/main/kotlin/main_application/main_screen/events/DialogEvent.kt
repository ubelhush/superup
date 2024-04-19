package main_application.main_screen.events

sealed class DialogEvent {
    data object OnDismiss: DialogEvent()
    data object OnConfirm: DialogEvent()
    data class OnNameChange(val name:String): DialogEvent()
}