package main_application.main_screen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.woong.compose.grid.SimpleGridCells
import io.woong.compose.grid.VerticalGrid
import main_application.main_screen.view_model.FileData

@Composable
fun Grids(
    files: List<FileData>,
    modifier: Modifier = Modifier,
    columnSize: Dp = 40.dp,
    horizontalSpace: Dp = 40.dp,
    verticalSpace: Dp = 40.dp,
    item: @Composable (FileData) -> Unit
) {
    VerticalGrid(
        columns = SimpleGridCells.Adaptive(columnSize),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
        verticalArrangement = Arrangement.spacedBy(verticalSpace),
        modifier = modifier
    ) {
        for (file in files){
            item(file)
        }
    }
}