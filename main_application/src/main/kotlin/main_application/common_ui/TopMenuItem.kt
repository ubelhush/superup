package main_application.common_ui

import main_application.utils.Routes
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

sealed class TopMenuItem(val title: String, val contextItems: List<ContextItem>) {
    data object Tasks : TopMenuItem(
        title = "Tasks",
        contextItems = listOf(
            ContextItem("child processes") {
                val pid = ProcessHandle.current().pid()
                val commandWithArg = listOf(Routes.PROCESS_MONITOR, pid.toString())
                ProcessBuilder(commandWithArg).start()
            },
            ContextItem("functional") {
                val pid = ProcessHandle.current().pid()
                val commandWithArg = listOf(Routes.FUNCTIONAL, pid.toString())
                ProcessBuilder(commandWithArg).start()
            },
            ContextItem("message queque") {
                val commandWithArg = listOf(Routes.MESSAGE_QUEUE)
                println("test")
                ProcessBuilder(commandWithArg).start()
            }
        )
    )

    data object Terminal : TopMenuItem(
        title = "terminal",
        contextItems = listOf(
            ContextItem("run") {
                ProcessBuilder(Routes.TERMINAL).start()
            }
        )
    )

    data object Help : TopMenuItem(
        title = "help",
        contextItems = listOf(
            ContextItem("about") {

            }
        )
    )
}

data class ContextItem(
    val title: String,
    val action: () -> Unit
)