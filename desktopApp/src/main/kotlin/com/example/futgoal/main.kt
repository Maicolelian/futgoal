package com.example.futgoal

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.futgoal.data.DatabaseDriverFactory

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Futgoal",
    ) {
        App(DatabaseDriverFactory())
    }
}
