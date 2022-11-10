package me.ikupriyanov.demo.plot

import jetbrains.datalore.plotDemo.model.plotConfig.BarPlot
import jetbrains.datalore.vis.svgMapper.skia.PlotViewerWindowSkia

object BarPlotViewerSkia {
    @JvmStatic
    fun main(args: Array<String>) {
        with(BarPlot()) {
            PlotViewerWindowSkia(
                "Bar plot",
                null,
                plotSpecList().first(),
//                    Dimension(900, 700),
                preserveAspectRatio = false
            ).open()
        }
    }
}
