package main_application.main_screen.events

sealed class UiEvent {
    data object OnExitApp:UiEvent()
    data class OnCopy(val text:String):UiEvent()
    data object OnPut:UiEvent()
    data class OnCut(val text:String):UiEvent()
}