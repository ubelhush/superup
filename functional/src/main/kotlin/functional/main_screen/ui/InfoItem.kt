package functional.main_screen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun InfoItem(title: String, info: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Line(isHorizontal = true)
        Box() {
            Text(text = title, color = Color.White)
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(text = info, color = Color.White)
            }
        }
        Line(isHorizontal = true)
    }
}