package me.ikupriyanov.demo.plot

import jetbrains.datalore.plotDemo.model.plotConfig.Area
import jetbrains.datalore.vis.swing.skia.PlotViewerWindowSkia

object AreaViewerSkia {
    @JvmStatic
    fun main(args: Array<String>) {
        with(Area()) {
            PlotViewerWindowSkia(
                "Area plot",
                null,
                plotSpecList().first(),
//                    Dimension(900, 700),
                preserveAspectRatio = false
            ).open()
        }
    }
}
