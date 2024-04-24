package main_application.top_menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import main_application.dialog.simple_dialog.SimpleDialogEvent
import main_application.dialog.simple_dialog.SimpleDialogState

class TopMenuViewModel {
    var topMenuState by mutableStateOf(TopMenuState())
    var simpleDialogState by mutableStateOf(SimpleDialogState())

    fun onEvent(event: TopMenuEvent){
        when(event){
            is TopMenuEvent.ShowSimpleDialog -> {
                simpleDialogState=simpleDialogState.copy(
                    text = event.text,
                    showDialog = true
                )
            }
        }
    }

    fun onEvent(event: SimpleDialogEvent){
        when(event){
            SimpleDialogEvent.OnDismiss -> {
                simpleDialogState=simpleDialogState.copy(
                    showDialog = false
                )
            }
        }
    }
}