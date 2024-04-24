package main_application.main_screen.events

sealed class SidePanelEvent {
    data class OnFolderClick(val path:String): SidePanelEvent()
    data object GetConnectedDevices:SidePanelEvent()
}