package main_application.main_screen.events

sealed class FolderContentEvent {
    data class OnFolderClick(val folderName:String): FolderContentEvent()
    data class RemoveToTrash(val fileName:String): FolderContentEvent()
    data class DeleteFile(val fileName:String): FolderContentEvent()
    data object CreateFile: FolderContentEvent()
    data object CreateFolder: FolderContentEvent()
}