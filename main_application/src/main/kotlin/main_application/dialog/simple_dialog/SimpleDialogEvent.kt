package main_application.dialog.simple_dialog

sealed class SimpleDialogEvent {
    data object OnDismiss:SimpleDialogEvent()
}