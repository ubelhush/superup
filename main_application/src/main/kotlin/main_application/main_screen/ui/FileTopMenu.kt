package main_application.main_screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import main_application.main_screen.events.FileTopMenuEvent
import main_application.main_screen.events.UiEvent
import main_application.main_screen.view_model.MainState

@Composable
fun FileTopMenu(
    state: MainState,
    onEvent: (FileTopMenuEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize().padding(4.dp)
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Button(
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                onClick = { onEvent(FileTopMenuEvent.OnClickBack) }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }
            Button(
                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                onClick = { onEvent(FileTopMenuEvent.OnClickForward) }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "forward"
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(7f)
                    .background(Color.LightGray)
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Enter) {
                            onEvent(FileTopMenuEvent.OnSearch)
                        }
                        false
                    }
            ) {
                BasicTextField(
                    value = state.searchText,
                    onValueChange = {newText->
                        onEvent(FileTopMenuEvent.OnTextChange(newText))
                    },
                    singleLine = true,
                    modifier = Modifier.weight(1f).padding(start = 8.dp, end = 8.dp)
                )
                IconButton(onClick = {
                    onEvent(FileTopMenuEvent.OnSearch)
                }) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "search"
                    )
                }

            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    onEvent(FileTopMenuEvent.CreateFolder)
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "add"
                )
            }

            Spacer(
                modifier = Modifier.width(4.dp)
            )
        }
    }
}