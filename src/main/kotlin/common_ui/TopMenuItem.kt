package common_ui

sealed class TopMenuItem(val title: String, val contextItems: List<ContextItem>) {
    data object Test : TopMenuItem(
        title = "help",
        contextItems = listOf(
            ContextItem("about")
        )
    )
}

data class ContextItem(
    val title: String
)