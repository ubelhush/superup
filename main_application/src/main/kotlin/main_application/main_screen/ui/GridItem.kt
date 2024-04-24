package main_application.main_screen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import main_application.main_screen.view_model.FileData

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GridItem(
    fileData: FileData,
    onSingleClick: () -> Unit,
    onDoubleClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                if (fileData.isSelect)
                    Color.Blue.copy(alpha = 0.3f)
                else
                    Color.Transparent
            )
            .onPointerEvent(PointerEventType.Press) {
                when {
                    it.buttons.isPrimaryPressed -> when (it.awtEventOrNull?.clickCount) {
                        1 -> {
                            onSingleClick()
                        }

                        2 -> {
                            onDoubleClick()
                        }
                    }

                    it.buttons.isSecondaryPressed -> {}
                }
            }
    ) {
        if (fileData.isFolder) {
            Image(
                painter = painterResource("folder.png"),
                contentDescription = "folder",
                modifier = Modifier.size(72.dp)
            )
        } else {
            Image(
                painter = painterResource("file.png"),
                contentDescription = "file",
                modifier = Modifier.size(72.dp)
            )
        }

        Text(
            text = fileData.name
        )
    }
}