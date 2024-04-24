package process_monitor.main_screen.event

sealed class MainEvent {
    data object OnShowDialog:MainEvent()
}