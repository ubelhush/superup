package main_application.top_menu

import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import main_application.dialog.simple_dialog.SimpleInfoDialog

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopMenu(
    modifier: Modifier = Modifier
) {
    val viewModel = remember { TopMenuViewModel() }
    val menuItems = listOf(
        TopMenuItem.Tasks,
        TopMenuItem.Terminal,
        TopMenuItem.Help,
    )
    val coroutineScope = rememberCoroutineScope()
    var currentItem by remember { mutableStateOf<TopMenuItem?>(null) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        menuItems.forEach { menuItem ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .onPointerEvent(PointerEventType.Enter) {
                        currentItem = menuItem
                    }
            ) {
                Text(
                    text = menuItem.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                )
                DropdownMenu(
                    expanded = currentItem == menuItem,
                    onDismissRequest = { currentItem = null }
                ) {
                    currentItem?.contextItems?.forEach { contextItem ->
                        DropdownMenuItem(
                            onClick = {
                                coroutineScope.launch {
                                    contextItem.action(viewModel::onEvent)
                                }
                            }
                        ) {
                            Text(text = contextItem.title)
                        }
                    }
                }
            }
        }
    }

    if(viewModel.simpleDialogState.showDialog){
        SimpleInfoDialog(
            state = viewModel.simpleDialogState,
            onEvent = viewModel::onEvent
        )
    }
}