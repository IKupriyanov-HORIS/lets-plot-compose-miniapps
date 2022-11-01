package me.ikupriyanov.demo.svg

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import jetbrains.datalore.vis.svgMapper.demo.DemoModelA
import me.ikupriyanov.demo.composable.Svg


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            Svg(DemoModelA.createModel(), density = LocalDensity.current.density)
        }
    }
}

