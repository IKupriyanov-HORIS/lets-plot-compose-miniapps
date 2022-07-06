package me.ikupriyanov.demo.plot

import jetbrains.datalore.plotDemo.model.plotConfig.ThemeOptions
import jetbrains.datalore.vis.swing.skia.PlotViewerWindowSkia

object ThemeOptionsViewerSkia {
    @JvmStatic
    fun main(args: Array<String>) {
        with(ThemeOptions()) {
            PlotViewerWindowSkia(
                "Theme options",
                null,
                plotSpecList().first(),
//                    Dimension(900, 700),
                preserveAspectRatio = false
            ).open()
        }
    }
}
