package main_application.dialog.create_folder_dialog

data class FolderDialogState(
    val folderName: String = "",
    val isFolder: Boolean = true,
    val showDialog: Boolean = false
)
