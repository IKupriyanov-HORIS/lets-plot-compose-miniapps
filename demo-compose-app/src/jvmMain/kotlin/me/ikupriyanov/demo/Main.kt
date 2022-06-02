package me.ikupriyanov

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import jetbrains.datalore.vis.svgMapper.demo.DemoModelA
import me.ikupriyanov.composable.Svg


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            Svg(DemoModelA.createModel(), density = 2.0)
        }
    }
}
