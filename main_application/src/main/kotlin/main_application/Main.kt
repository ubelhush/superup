package main_application

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main_application.main_screen.ui.MainScreen
import main_application.utils.Routes
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Paths


fun main() = application {
    init()
    MainScreen(::exitApplication)
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

