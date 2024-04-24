package process_monitor.main_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import main_application.dialog.create_folder_dialog.SRDialogEvent
import main_application.dialog.create_folder_dialog.SaveReportDialogState
import process_monitor.main_screen.event.MainEvent
import java.io.*
import java.util.*

class MainViewModel(private val pids: Array<String>) {
    private val _mainState= MutableStateFlow(MainState())
    var mainState =_mainState.asStateFlow()
    var dialogState by mutableStateOf(SaveReportDialogState())
    private set

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
        timer.schedule(task, 0, 3000)
    }

    fun onEvent(event:MainEvent){
        when(event){
            MainEvent.OnShowDialog -> {
                dialogState=dialogState.copy(
                    showDialog = true
                )
            }
        }
    }
    fun onEvent(event: SRDialogEvent){
        when(event){
            SRDialogEvent.OnConfirm -> {
                saveReport()
                dialogState=dialogState.copy(
                    name = "",
                    showDialog = false
                )
            }
            SRDialogEvent.OnDismiss -> {
                dialogState=dialogState.copy(
                    name = "",
                    showDialog = false
                )
            }
            is SRDialogEvent.OnNameChange -> {
                dialogState=dialogState.copy(
                    name = event.name
                )
            }
        }
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
                "pid,user,lstart,%cpu,time,cmd"
            ).start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))

            processInfo = reader.readLines().mapIndexedNotNull { index, line ->
                if (index != 0) {
                    val args = line.split(" ").filter { it.isNotBlank() }
                    ProcessData(
                        pid = args[0],
                        user = args[1],
                        lstart = args.subList(2,7).joinToString(" "),
                        cpu = args[7],
                        time = args[8],
                        cmd = args[9]
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

    private fun saveReport(){
        val fileWriter = FileWriter("./root/home/${dialogState.name}")
        val printWriter = PrintWriter(fileWriter)

        printWriter.println("time\t\t\t\tname")
        mainState.value.processes.forEach { processData ->
            printWriter.println("${processData.lstart}\t${processData.cmd}")
        }
    }
}

data class MainState(
    val processes: List<ProcessData> = emptyList()
)

data class ProcessData(
    val pid: String = "",
    val user: String = "",
    val lstart:String = "",
    val cpu: String = "",
    val time: String = "",
    val cmd: String = "",
)