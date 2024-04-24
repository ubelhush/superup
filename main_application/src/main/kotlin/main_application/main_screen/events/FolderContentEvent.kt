package main_application.main_screen.events

sealed class FolderContentEvent {
    data class OnSingleClick(val fileName:String):FolderContentEvent()
    data class OnFolderDoubleClick(val folderName:String): FolderContentEvent()
    data class RemoveToTrash(val fileName:String): FolderContentEvent()
    data class DeleteFile(val fileName:String): FolderContentEvent()
    data object CreateFile: FolderContentEvent()
    data object CreateFolder: FolderContentEvent()
    data class PutFiles(val routes:String?):FolderContentEvent()
}