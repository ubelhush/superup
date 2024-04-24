package terminal.main_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import terminal.main_screen.events.TerminalEvent
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.nio.file.Paths
import java.util.*

class MainViewModel(private val coroutineScope: CoroutineScope) {
    var mainState by mutableStateOf(MainState(
        answerText = "Welcome to the terminal",
        inputPrompt = ">>> "
    ))
        private set

    init {
        mainState = mainState.copy(
            currentDir = Paths.get("").toAbsolutePath().toString()
        )
    }

    fun onEvent(event: TerminalEvent) {
        when (event) {
            TerminalEvent.ExecuteCommand -> {
                val commandWithParam = mainState.commandText.split(" ")
                compareCommand(commandWithParam)

                mainState = mainState.copy(
                    commandText = ""
                )
            }

            is TerminalEvent.OnTextChange -> {
                mainState = mainState.copy(
                    commandText = event.text
                )
            }
        }
    }

    private fun compareCommand(command: List<String>) {
        var result = ""

        when (command.first()) {
            "ip" -> {
                if (command.size != 3) {
                    result = "Usage: ip address show"
                } else {
                    try {
                        result = runProcess(command)
                    } catch (e: Exception) {
                        result = e.message.toString() + "\n"
                    }
                }
            }

            "traceroute" -> {
                if (command.size != 2) {
                    result = "Usage: traceroute <example.com>"
                } else {
                    try {
                        result = runProcess(command)
                    } catch (e: Exception) {
                        result = e.message.toString() + "\n"
                    }
                }
            }

            "ss" -> {
                if (command.size != 2) {
                    result = "Usage: ss -tuln"
                } else {
                    try {
                        result = runProcess(command)
                    } catch (e: Exception) {
                        result = e.message.toString() + "\n"
                    }
                }
            }

            "wget" -> {
                if (command.size != 2) {
                    result = "Usage: wget"
                } else {
                    try {
                        result = runProcess(command)
                    } catch (e: Exception) {
                        result = e.message.toString() + "\n"
                    }
                }
            }

            "find" -> {
                if (command.size != 2) {
                    result = "Usage: find <search_term>"
                } else {
                    val foundItems: List<String> = findItems(
                        mainState.currentDir,
                        command[1]
                    )

                    result = if (foundItems.isEmpty()) {
                        "No items found."
                    } else {
                        "Found items: \n" + foundItems.joinToString("\n")
                    }
                }
            }

            "ls" -> {
                val directoryPath: String = if (command.size > 1) command[1] else mainState.currentDir
                val directory = File(directoryPath)

                if (directory.exists() && directory.isDirectory) {
                    val files = directory.listFiles()
                    result = "Listing files and directories in $directoryPath:\n"
                    result += files?.joinToString("\n") { it.name }
                } else {
                    result = "Directory $directoryPath not found."
                }
            }

            "mkdir" -> {
                if (command.size != 2) {
                    result = "Usage: mkdir <directory_name>"
                } else {
                    val newDirectoryName: String = command[1]
                    val newDirectory = File(newDirectoryName)

                    result = if (!newDirectory.exists()) {
                        val created = newDirectory.mkdir()
                        if (created) {
                            "Directory $newDirectoryName created successfully."
                        } else {
                            "Failed to create directory $newDirectoryName."
                        }

                    } else {
                        "Directory $newDirectoryName already exists."
                    }
                }
            }

            "cd" -> {
                if (command.size != 2) {
                    result = "Usage: cd <directory_path>"
                } else {
                    val targetDirectory: String = command[1]
                    val newDir = File(targetDirectory)
                    result = if (newDir.exists() && newDir.isDirectory) {
                        mainState = mainState.copy(
                            currentDir = newDir.absolutePath
                        )

                        "Changed directory to " + mainState.currentDir
                    } else {
                        "Directory $targetDirectory not found."
                    }
                }
            }

            "pwd" -> {
                result = "Current directory: ${mainState.currentDir}"
            }

            "clear" -> {
                mainState = mainState.copy(answerText = "")
            }

            "help"->{
                result="ip address show - It is used to display information about network interfaces, including IP addresses.\n\n" +
                        "traceroute - It is used to track the route that data packets travel from your computer to the target node or host on the network.\n\n" +
                        "ss -tuln - used to list all open sockets\n\n" +
                        "wget - It is used to download files from the Internet over HTTP, HTTPS and FTP.\n\n" +
                        "find - it is used to search for files and directories in the file system\n\n" +
                        "ls - used to display the contents of the catalog\n\n" +
                        "mkdir - used to create a new directory\n\n" +
                        "cd - used to change the current working directory\n\n" +
                        "pwd - used to display the current working directory (current location)\n\n" +
                        "clear - It is used to clear the terminal screen."
            }

            else -> {
                result = " Unknown command: ${command.joinToString(" ")}"
            }
        }

        mainState = mainState.copy(
            answerText = mainState.answerText + "\n" + ">>>" + command.joinToString() + "\n" + result
        )
    }

    private fun runProcess(command: List<String>): String {
        val process = ProcessBuilder(command).start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val answer = reader.readLines().joinToString("\n")

        return answer
    }

    private fun findItems(directoryPath: String, searchTerm: String): List<String> {
        val foundItems: MutableList<String> = mutableListOf()
        findItemsRecursive(File(directoryPath), searchTerm, foundItems)
        return foundItems
    }

    private fun findItemsRecursive(directory: File, searchTerm: String, foundItems: MutableList<String>) {
        val files = directory.listFiles()
        if (files != null) {
            for (file in files) {
                if (file.name.contains(searchTerm)) {
                    foundItems.add(file.absolutePath)
                }
                if (file.isDirectory) {
                    findItemsRecursive(file, searchTerm, foundItems)
                }
            }
        }
    }
}

data class MainState(
    val currentDir: String = "",
    val commandText: String = "",
    val inputPrompt:String ="",
    val answerText: String = ""
)