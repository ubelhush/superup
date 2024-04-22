package terminal.main_screen.events

sealed class TerminalEvent {
    data class OnTextChange(val text:String): TerminalEvent()
    data object ExecuteCommand: TerminalEvent()
}