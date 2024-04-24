package main_application.main_screen.events

sealed class KeyEvent {
    data object OnCopy:KeyEvent()
    data object OnPut:KeyEvent()
    data object OnCut:KeyEvent()
    data object OnDelete:KeyEvent()
    data object OnExitApp:KeyEvent()
}