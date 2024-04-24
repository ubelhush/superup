package main_application.dialog.create_folder_dialog

sealed class SRDialogEvent {
    data object OnDismiss: SRDialogEvent()
    data object OnConfirm: SRDialogEvent()
    data class OnNameChange(val name:String): SRDialogEvent()
}