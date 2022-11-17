package me.ikupriyanov.demo.plot

import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.plot.MonolithicCommon
import jetbrains.datalore.plotDemo.model.plotConfig.Area
import jetbrains.datalore.vis.svgMapper.skia.plotComponent


fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Area plot (Compose Desktop)") {
        MaterialTheme {
            //Svg(DemoModelA.createModel(), density = 2.0)
            with(Area()) {
                SwingPanel(
                    modifier = Modifier.size(600.dp, 400.dp),
                    factory = {
                        plotComponent(
                            MonolithicCommon.processRawSpecs(plotSpecList().first(), frontendOnly = false),
                            DoubleVector(600.0, 400.0)
                        )
                    }
                )
            }
        }
    }
}