package me.ikupriyanov.demo.svg

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import jetbrains.datalore.vis.svgMapper.demo.DemoModelA
import me.ikupriyanov.demo.utils.svg

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "SVG demo (Compose Desktop)") {
        MaterialTheme {
            svg(DemoModelA.createModel())
        }
    }
}
