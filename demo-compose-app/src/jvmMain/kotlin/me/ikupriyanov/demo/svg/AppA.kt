package me.ikupriyanov.demo.svg

import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import jetbrains.datalore.vis.svgMapper.demo.DemoModelA
import jetbrains.datalore.vis.svgMapper.skia.svgComponent

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "SVG demo (Compose Desktop)") {
        MaterialTheme {
            SwingPanel(
                modifier = Modifier.size(600.dp, 400.dp),
                factory = {
                    svgComponent(DemoModelA.createModel())
                }
            )
        }
    }
}
