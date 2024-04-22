package process_monitor.main_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class MainViewModel(private val pids: Array<String>) {
    private val _mainState= MutableStateFlow(MainState())
    var mainState =_mainState.asStateFlow()

    private val timer = Timer("update process")

    init {
        val task = object : TimerTask() {
            override fun run() {
                val processes=getProcessChildrenID().map { pid->
                    getProcessInfo(pid)
                }

                _mainState.value=mainState.value.copy(
                    processes = processes.mapNotNull { it }
                )
            }
        }
        timer.schedule(task, 5, 5000)
    }

    private fun getProcessChildrenID(): List<String> {
        var childProcessPIDS = emptyList<String>()

        pids.forEach { pid ->
            try {
                val p = Runtime.getRuntime().exec("pgrep -P $pid")
                val reader = BufferedReader(InputStreamReader(p.inputStream))
                childProcessPIDS = reader.readLines()

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return childProcessPIDS
    }

    private fun getProcessInfo(pid: String): ProcessData? {
        var processInfo: ProcessData? = null

        try {
            val process = ProcessBuilder(
                "ps",
                "-p",
                pid,
                "-o",
                "pid,user,%cpu,time,cmd"
            ).start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))

            processInfo = reader.readLines().mapIndexedNotNull { index, line ->
                if (index != 0) {
                    val args = line.split(" ").filter { it.isNotBlank() }
                    ProcessData(
                        pid = args[0],
                        user = args[1],
                        cpu = args[2],
                        time = args[3],
                        cmd = args[4]
                    )
                } else {
                    null
                }
            }.first()

            process.destroy()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return processInfo
    }
}

data class MainState(
    val processes: List<ProcessData> = emptyList()
)

data class ProcessData(
    val pid: String = "",
    val user: String = "",
    val cpu: String = "",
    val time: String = "",
    val cmd: String = "",
)