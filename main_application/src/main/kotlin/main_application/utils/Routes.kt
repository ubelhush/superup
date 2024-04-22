package main_application.utils

object Routes {
    //relative paths to the main directories
    const val ROOT_PATH="./root"
    const val SYSTEM_PATH="${ROOT_PATH}/system"
    const val TRASH_PATH="${ROOT_PATH}/trash"
    const val HOME_PATH="${ROOT_PATH}/home"

    //The name of the main directory
    const val ROOT="root"
    const val SYSTEM="system"
    const val TRASH="trash"
    const val HOME="home"

    //relative file paths
    const val TERMINAL="${SYSTEM_PATH}/terminal/bin/terminal"
    const val PROCESS_MONITOR="${SYSTEM_PATH}/process_monitor/bin/process_monitor"
    const val FUNCTIONAL="${SYSTEM_PATH}/functional/bin/functional"
    const val MESSAGE_QUEUE="${SYSTEM_PATH}/message_queue/bin/message_queue"
}