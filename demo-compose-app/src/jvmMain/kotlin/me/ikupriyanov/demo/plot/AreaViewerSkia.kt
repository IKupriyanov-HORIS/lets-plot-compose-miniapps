package me.ikupriyanov.demo.plot

import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import jetbrains.datalore.plotDemo.model.plotConfig.Area


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            //Svg(DemoModelA.createModel(), density = 2.0)
            with(Area()) {
                SwingPanel(
                    modifier = Modifier.size(600.dp, 400.dp),
                    factory = {
                        rawPlotPanel(plotSpecList().first())
                    }
                )
            }
        }
    }
}