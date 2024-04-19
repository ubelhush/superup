package main_application.main_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import main_application.main_screen.events.DialogEvent
import main_application.main_screen.events.FileTopMenuEvent
import main_application.main_screen.events.FolderContentEvent
import main_application.main_screen.events.SidePanelEvent
import utils.Routes
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class MainViewModel() {
    var mainState by mutableStateOf(MainState())
        private set
    var dialogState by mutableStateOf(DialogState())
        private set

    private val previousPaths: MutableList<String> = mutableListOf()
    private val forwardPaths: MutableList<String> = mutableListOf()

    init {
        Paths.get("").toAbsolutePath().toString().let { currentPath->

            mainState = mainState.copy(
                currentPath = currentPath+Routes.ROOT_PATH,
                mainPaths= mapOf(
                    Routes.ROOT to currentPath+Routes.ROOT_PATH,
                    Routes.HOME to currentPath+Routes.HOME_PATH,
                    Routes.SYSTEM to currentPath+Routes.SYSTEM_PATH,
                    Routes.TRASH to currentPath+Routes.TRASH_PATH
                )
            )

            getFolderContent()
        }
    }

    fun onEvent(event: FileTopMenuEvent) {
        when (event) {
            FileTopMenuEvent.OnClickBack -> {
                if (previousPaths.size > 0) {
                    forwardPaths.add(mainState.currentPath)

                    mainState = mainState.copy(
                        currentPath = previousPaths.last()
                    )

                    previousPaths.removeAt(previousPaths.lastIndex)
                    getFolderContent()
                }
            }

            FileTopMenuEvent.OnClickForward -> {
                if (forwardPaths.size > 0) {
                    previousPaths.add(mainState.currentPath)

                    mainState = mainState.copy(
                        currentPath = forwardPaths.last()
                    )

                    forwardPaths.removeAt(forwardPaths.lastIndex)
                    getFolderContent()
                }
            }

            FileTopMenuEvent.CreateFolder -> {
                dialogState = dialogState.copy(
                    showDialog = true,
                    isFolder = true
                )
            }
        }
    }

    fun onEvent(event: SidePanelEvent){
        when(event){
            is SidePanelEvent.OnFolderClick -> {
                val folder = File(event.path)

                if (folder.exists() && folder.isDirectory) {
                    previousPaths.add(mainState.currentPath)

                    mainState = mainState.copy(
                        currentPath = event.path
                    )

                    getFolderContent()
                }
            }
        }
    }

    fun onEvent(event: FolderContentEvent) {
        when (event) {
            is FolderContentEvent.OnFolderClick -> {
                val newCurrentPath = "${mainState.currentPath}/${event.folderName}"
                val folder = File(newCurrentPath)

                if (folder.exists() && folder.isDirectory) {
                    previousPaths.add(mainState.currentPath)

                    mainState = mainState.copy(
                        currentPath = newCurrentPath
                    )

                    getFolderContent()
                }
            }

            is FolderContentEvent.DeleteFile -> {
                val deleteFile = File("${mainState.currentPath}/${event.fileName}")

                if (deleteFile.exists()) {
                    deleteFile.delete()
                    getFolderContent()
                }
            }

            is FolderContentEvent.RemoveToTrash -> {
                mainState.mainPaths[Routes.TRASH]?.let { trashPath->
                    val sourcePath = Paths.get("${mainState.currentPath}/${event.fileName}")
                    val targetPath = Paths.get("${trashPath}/${event.fileName}")

                    Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING)
                    getFolderContent()
                }
            }

            is FolderContentEvent.CreateFile -> {
                dialogState = dialogState.copy(
                    showDialog = true,
                    isFolder = false
                )
            }
            is FolderContentEvent.CreateFolder -> {
                dialogState = dialogState.copy(
                    showDialog = true,
                    isFolder = true
                )
            }
        }
    }

    fun onEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnConfirm -> {
                if (dialogState.isFolder){
                    createFolder("${mainState.currentPath}/${dialogState.folderName}")
                }
                else{
                    createFile("${mainState.currentPath}/${dialogState.folderName}")
                }

                dialogState = dialogState.copy(
                    folderName = "",
                    showDialog = false
                )

                getFolderContent()
            }

            is DialogEvent.OnDismiss -> {
                dialogState = dialogState.copy(
                    folderName = "",
                    showDialog = false
                )
            }

            is DialogEvent.OnNameChange -> {
                dialogState = dialogState.copy(
                    folderName = event.name
                )
            }
        }
    }

    private fun getFolderContent() {

        val folder = File(mainState.currentPath)

        if (folder.exists() && folder.isDirectory) {
            val folderContent = folder.listFiles()?.mapNotNull { file ->
                file.name
            }

            mainState = mainState.copy(
                folderContent = folderContent ?: emptyList()
            )
        }
    }

    private fun createFolder(path: String) {
        File(path).mkdir()
    }

    private fun createFile(path:String){
        File(path).createNewFile()
    }
}

data class MainState(
    val currentPath: String = "",
    val mainPaths:Map<String, String> = emptyMap(),
    val folderContent: List<String> = emptyList(),
)

data class DialogState(
    val folderName: String = "",
    val isFolder:Boolean=true,
    val showDialog: Boolean = false
)