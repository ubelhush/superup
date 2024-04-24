package main_application.main_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import main_application.dialog.create_folder_dialog.CFDialogEvent
import main_application.dialog.create_folder_dialog.FolderDialogState
import main_application.main_screen.events.*
import main_application.utils.Routes
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


class MainViewModel(
    private val coroutineScope: CoroutineScope
) {
    var mainState by mutableStateOf(MainState())
        private set
    var dialogState by mutableStateOf(FolderDialogState())
        private set
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val previousPaths: MutableList<String> = mutableListOf()
    private val forwardPaths: MutableList<String> = mutableListOf()

    init {
        Paths.get("").toAbsolutePath().toString().let { currentPath ->

            mainState = mainState.copy(
                currentPath = Routes.ROOT_PATH,
                mainPaths = mapOf(
                    Routes.ROOT to Routes.ROOT_PATH,
                    Routes.HOME to Routes.HOME_PATH,
                    Routes.SYSTEM to Routes.SYSTEM_PATH,
                    Routes.TRASH to Routes.TRASH_PATH
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

            is FileTopMenuEvent.OnTextChange -> {
                mainState = mainState.copy(
                    searchText = event.text
                )
            }

            FileTopMenuEvent.OnSearch -> {
                val result= mutableListOf<FileData>()
                searchFile(
                    name = mainState.searchText,
                    file = File(mainState.currentPath),
                    result=result
                )

                mainState = mainState.copy(
                    searchText = "",
                    folderContent = result
                )
            }
        }
    }

    fun onEvent(event: SidePanelEvent) {
        when (event) {
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

            SidePanelEvent.GetConnectedDevices -> {
                getConnectedDevices()
            }
        }
    }

    fun onEvent(event: FolderContentEvent) {
        when (event) {
            is FolderContentEvent.OnSingleClick -> {
                val oldFile = mainState.folderContent.find { it.name == event.fileName }
                val newFile = oldFile?.copy(
                    isSelect = !oldFile.isSelect
                )
                val tempList = mainState.folderContent.toMutableList().apply {
                    val oldFileIndex = indexOf(oldFile)
                    newFile?.let {
                        set(oldFileIndex, newFile)
                    }
                }

                mainState = mainState.copy(
                    folderContent = tempList
                )
            }

            is FolderContentEvent.OnFolderDoubleClick -> {
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
                mainState.mainPaths[Routes.TRASH]?.let { trashPath ->
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

            is FolderContentEvent.PutFiles -> {
                if (event.routes != null) {
                    val isCut = event.routes.split("|")[0] == "cut"


                    val routes = if (isCut)
                        event.routes.split("|")[1].split("\n")
                    else
                        event.routes.split("\n")

                    routes.forEach { route ->
                        val newName = route.split("/").last()
                        val sourceFile = File(route)
                        val destinationFile = File(mainState.currentPath + "/" + newName)

                        if (isCut)
                            sourceFile.renameTo(destinationFile)
                        else
                            Files.copy(
                                sourceFile.toPath(),
                                destinationFile.toPath(),
                                StandardCopyOption.REPLACE_EXISTING
                            )
                    }
                }

                getFolderContent()
            }
        }
    }

    fun onEvent(event: CFDialogEvent) {
        when (event) {
            is CFDialogEvent.OnConfirm -> {
                if (dialogState.isFolder) {
                    createFolder("${mainState.currentPath}/${dialogState.folderName}")
                } else {
                    createFile("${mainState.currentPath}/${dialogState.folderName}")
                }

                dialogState = dialogState.copy(
                    folderName = "",
                    showDialog = false
                )

                getFolderContent()
            }

            is CFDialogEvent.OnDismiss -> {
                dialogState = dialogState.copy(
                    folderName = "",
                    showDialog = false
                )
            }

            is CFDialogEvent.OnNameChange -> {
                dialogState = dialogState.copy(
                    folderName = event.name
                )
            }
        }
    }

    fun onEvent(event: KeyEvent) {
        when (event) {
            KeyEvent.OnExitApp -> {
                onEvent(UiEvent.OnExitApp)
            }

            KeyEvent.OnCopy -> {
                val fileRoutes = mainState.folderContent
                    .filter { it.isSelect }
                    .joinToString("\n") {
                        mainState.currentPath + "/" + it.name
                    }

                onEvent(UiEvent.OnCopy(fileRoutes))
            }

            KeyEvent.OnCut -> {
                val fileRoutes = "cut|" + mainState.folderContent
                    .filter { it.isSelect }
                    .joinToString("\n") {
                        mainState.currentPath + "/" + it.name
                    }
                onEvent(UiEvent.OnCut(fileRoutes))
            }

            KeyEvent.OnPut -> {
                onEvent(UiEvent.OnPut)
            }

            KeyEvent.OnDelete -> {
                val fileRoutes = mainState.folderContent
                    .filter { it.isSelect }
                    .map { mainState.currentPath + "/" + it.name }


                fileRoutes.forEach { route ->
                    File(route).delete()
                }

                getFolderContent()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private fun onEvent(event: UiEvent) {
        coroutineScope.launch(newSingleThreadContext("ui event")) {
            _uiEvent.send(event)
        }
    }

    private fun getFolderContent() {

        val folder = File(mainState.currentPath)

        if (folder.exists() && folder.isDirectory) {
            val folderContent = folder.listFiles()?.mapNotNull { file ->
                FileData(
                    name = file.name,
                    isFolder = file.isDirectory
                )
            }

            mainState = mainState.copy(
                folderContent = folderContent ?: emptyList()
            )
        }
    }

    private fun createFolder(path: String) {
        File(path).mkdir()
    }

    private fun createFile(path: String) {
        File(path).createNewFile()
    }

    private fun searchFile(name: String, file: File, result: MutableList<FileData>) {
        val list: Array<out File>? = file.listFiles()
        if (list != null) {
            for (fil in list) {
                if (fil.isDirectory) {
                    if (fil.name.contains(name)) result.add(
                        FileData(
                            name = fil.name,
                            isFolder = true
                        )
                    )
                    searchFile(name, fil, result)
                } else if (fil.name.contains(name)) {
                    result.add(
                        FileData(
                            name = fil.name,
                            isFolder = false
                        )
                    )
                }
            }
        }
    }

    private fun getConnectedDevices(){
        val deviceList= mutableListOf<DeviceData>()
        var isUSB= false

        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            val process = ProcessBuilder("lsblk", "-o", "MOUNTPOINT,MODEL").start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            reader.readLines().forEach { line ->
                val data = line.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                if (isUSB){
                    if (data.isNotEmpty() && data[0].isNotEmpty()){
                        deviceList.add(
                            DeviceData(
                                path = data[0],
                                name = data[0].split("/").last()
                            )
                        )
                        println(deviceList.size)
                    }else{
                        isUSB=false
                    }
                }
                else if (line.contains("USB DISK 2.0")) {
                    isUSB=true
                }
            }

            mainState=mainState.copy(
                connectedDevices = deviceList
            )
        }
    }
}

data class MainState(
    val currentPath: String = "",
    val searchText: String = "",
    val mainPaths: Map<String, String> = emptyMap(),
    val connectedDevices:List<DeviceData> = emptyList(),
    val folderContent: List<FileData> = emptyList(),
)

data class FileData(
    val name: String = "",
    val isFolder: Boolean = false,
    val isSelect: Boolean = false
)

data class DeviceData(
    val name:String="",
    val path:String=""
)