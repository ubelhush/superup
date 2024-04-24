package process_monitor.main_screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import main_application.dialog.create_folder_dialog.SaveReportDialog
import process_monitor.main_screen.event.MainEvent
import process_monitor.main_screen.view_model.MainViewModel

@Composable
fun MainScreen(args: Array<String>) {
    val viewModel = remember { MainViewModel(args) }
    val state by viewModel.mainState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        Button(onClick = {
            viewModel.onEvent(MainEvent.OnShowDialog)
        }) {
            Text(
                text = "save report",
                color = Color.White
            )
        }

        Line(isHorizontal = true)

        Row(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Column(
                Modifier.fillMaxHeight().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "pid",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Line(isHorizontal = true)

                state.processes.map { it.pid }.forEach { pid ->
                    Text(
                        text = pid,
                        color = Color.White
                    )
                }
            }
            Column(
                Modifier.fillMaxHeight().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "user",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Line(isHorizontal = true)

                state.processes.map { it.user }.forEach { user ->
                    Text(
                        text = user,
                        color = Color.White
                    )
                }
            }
            Column(
                Modifier.fillMaxHeight().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "lstart",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Line(isHorizontal = true)

                state.processes.map { it.lstart }.forEach { user ->
                    Text(
                        text = user,
                        color = Color.White
                    )
                }
            }
            Column(
                Modifier.fillMaxHeight().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "cpu",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Line(isHorizontal = true)
                state.processes.map { it.cpu }.forEach { cpu ->
                    Text(
                        text = cpu,
                        color = Color.White
                    )
                }
            }
            Column(
                Modifier.fillMaxHeight().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "time",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Line(isHorizontal = true)
                state.processes.map { it.time }.forEach { time ->
                    Text(
                        text = time,
                        color = Color.White
                    )
                }
            }
            Column(
                Modifier.fillMaxHeight().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "cmd",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Line(isHorizontal = true)
                state.processes.map { it.cmd }.forEach { cmd ->
                    Text(
                        text = cmd,
                        color = Color.White
                    )
                }
            }
        }
    }

    if (viewModel.dialogState.showDialog) {
        SaveReportDialog(
            state = viewModel.dialogState,
            onEvent = viewModel::onEvent
        )
    }
}