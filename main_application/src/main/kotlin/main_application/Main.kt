package main_application

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import main_application.main_screen.ui.MainScreen
import main_application.utils.Routes
import java.nio.file.Files
import java.nio.file.Paths


fun main() = application {

    init()

    Window(
        state = WindowState(
            size = DpSize(700.dp, 600.dp)
        ),
        onCloseRequest = ::exitApplication
    ) {
        MainScreen()
    }
}

fun init() {
    Files.createDirectories(
        Paths.get(Routes.HOME_PATH)
    )
    Files.createDirectories(
        Paths.get(Routes.SYSTEM_PATH)
    )
    Files.createDirectories(
        Paths.get(Routes.TRASH_PATH)
    )
}

