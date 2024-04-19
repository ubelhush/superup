package main_application.main_screen.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import main_application.main_screen.events.FileTopMenuEvent
import main_application.main_screen.view_model.MainState

@Composable
fun FileTopMenu(
    state: MainState,
    onEvent:(FileTopMenuEvent)->Unit,
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
                    Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
            Button(
                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                onClick = { onEvent(FileTopMenuEvent.OnClickForward) }
            ) {
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "forward"
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            TextField(
                value = "",
                onValueChange = {},
                singleLine = true,
                label = {
                    Text(text = state.currentPath)
                },
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "search"
                        )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.weight(7f)
            )
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