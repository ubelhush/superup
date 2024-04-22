package functional.main_screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import functional.main_screen.view_model.MainViewModel
import kotlinx.coroutines.Dispatchers

@Composable
fun MainScreen(args: Array<String>) {
    val coroutineScope = rememberCoroutineScope { Dispatchers.IO }
    val viewModel = remember {
        MainViewModel(
            args = args,
            coroutineScope = coroutineScope
        )
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.DarkGray)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            InfoItem(
                title = "информация о пользователях:",
                info = viewModel.mainState.userInfo,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            InfoItem(
                title = "приоритет процессов:",
                info = viewModel.mainState.processPriority,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            InfoItem(
                title = "количество дескрипторов:",
                info = viewModel.mainState.descriptorsCount,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }
    }
}