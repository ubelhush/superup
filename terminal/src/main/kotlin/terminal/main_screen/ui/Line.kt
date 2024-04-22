package terminal.main_screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Line(
    isHorizontal: Boolean,
    color: Color=Color.Gray,
    width: Dp =1.dp
) {
    if (isHorizontal) {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(width)
            .background(color)
        )

    }
    else {
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(width)
                .background(color)
        )
    }
}