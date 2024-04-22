package functional.main_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class MainViewModel(
    private val args: Array<String>,
    private val coroutineScope: CoroutineScope) {
    var mainState by mutableStateOf(MainState())
        private set

    init {
        coroutineScope.launch {
            if (args.size in 1..2) {
                val userInfo = getUsersInfo()
                val processPriority = getProcessPriority(args[0])
                val descriptorsCount = getDescriptorCount(args.getOrNull(1))

                mainState=mainState.copy(
                    userInfo=userInfo,
                    processPriority =processPriority,
                    descriptorsCount = descriptorsCount
                )
            }
        }
    }

    private fun getProcessPriority(pid: String): String {
        val process = ProcessBuilder(listOf("ps", "-p", pid, "-o", "pri")).start()
        val processPriority = BufferedReader(InputStreamReader(process.inputStream)).readLines().last()

        return processPriority
    }

    private fun getUsersInfo(): String {
        val process = ProcessBuilder("w").start()
        val userInfo = BufferedReader(InputStreamReader(process.inputStream)).readLines().joinToString("\n")
        return userInfo
    }

    private fun getDescriptorCount(userName: String?):String{
            val command: List<String> = if (userName != null)
                listOf("sh", "-c", "lsof -u $userName | wc -l")
            else
                listOf("sh", "-c", "lsof | wc -l")

            val process = ProcessBuilder(command).start()
            val descriptionCount = BufferedReader(InputStreamReader(process.inputStream)).readLines().joinToString()

            return descriptionCount
    }
}

data class MainState(
    val userInfo: String="",
    val processPriority: String="",
    val descriptorsCount:String="",
)