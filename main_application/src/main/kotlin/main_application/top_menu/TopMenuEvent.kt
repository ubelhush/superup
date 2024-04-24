package main_application.top_menu

sealed class TopMenuEvent {
    data class ShowSimpleDialog(val text:String): TopMenuEvent()
}