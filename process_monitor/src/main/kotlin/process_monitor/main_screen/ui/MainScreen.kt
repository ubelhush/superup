package process_monitor.main_screen.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import process_monitor.main_screen.view_model.MainViewModel

@Composable
fun MainScreen(args: Array<String>) {
    val viewModel = remember { MainViewModel(args) }
    val state by viewModel.mainState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Column(
                Modifier.fillMaxHeight().weight(1f)
            ) {
                state.processes.map { it.pid }.forEach { pid->
                    Text(text = pid)
                }
            }
            Column(
                Modifier.fillMaxHeight().weight(1f)
            ) {
                state.processes.map { it.user }.forEach { user->
                    Text(text = user)
                }
            }
            Column(
                Modifier.fillMaxHeight().weight(1f)
            ) {
                state.processes.map { it.cpu }.forEach { cpu->
                    Text(text = cpu)
                }
            }
            Column(
                Modifier.fillMaxHeight().weight(1f)
            ) {
                state.processes.map { it.time }.forEach { time->
                    Text(text = time)
                }
            }
            Column(
                Modifier.fillMaxHeight().weight(1f)
            ) {
                state.processes.map { it.cmd }.forEach { cmd->
                    Text(text = cmd)
                }
            }
        }
    }
}