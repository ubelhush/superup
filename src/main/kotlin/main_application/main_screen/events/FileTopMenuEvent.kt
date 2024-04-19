package main_application.main_screen.events

sealed class FileTopMenuEvent {
    data object OnClickBack: FileTopMenuEvent()
    data object OnClickForward: FileTopMenuEvent()
    data object CreateFolder: FileTopMenuEvent()
}