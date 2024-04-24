package main_application.dialog.create_folder_dialog

sealed class CFDialogEvent {
    data object OnDismiss: CFDialogEvent()
    data object OnConfirm: CFDialogEvent()
    data class OnNameChange(val name:String): CFDialogEvent()
}