package main_application.top_menu

import main_application.utils.Routes

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
            ContextItem("about") { out ->
                out(
                    TopMenuEvent.ShowSimpleDialog(
                        "Операционные системы и оболочки,\n" +
                                "Язык программирования: Kotlin,\n" +
                                "Чурашов Алмаз Марсович, ПрИ-23"
                    )
                )
            },
            ContextItem("hotkey") { out ->
                out(
                    TopMenuEvent.ShowSimpleDialog(
                        "Ctrl + C копирование\n" +
                                "Ctrl + V вставка\n" +
                                "Ctrl + X вырезка\n" +
                                "Ctrl + Delete удаление\n"+
                                "Alt + F4 выход из приложения\n"
                    )
                )
            }
        )
    )
}


data class ContextItem(
    val title: String,
    val action: (onEvent: (TopMenuEvent) -> Unit) -> Unit = {}
)