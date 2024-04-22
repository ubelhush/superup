package terminal

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import terminal.main_screen.ui.MainScreen

fun main() = application {
    Window(
        state = WindowState(
            size = DpSize(700.dp, 600.dp)
        ),
        onCloseRequest = ::exitApplication
    ) {
        MainScreen()
    }
}