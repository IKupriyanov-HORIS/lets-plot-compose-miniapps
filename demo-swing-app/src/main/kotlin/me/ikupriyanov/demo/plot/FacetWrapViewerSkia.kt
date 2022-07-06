package me.ikupriyanov.demo.plot

import jetbrains.datalore.plotDemo.model.plotConfig.FacetWrapDemo
import jetbrains.datalore.vis.swing.skia.PlotViewerWindowSkia

object FacetWrapViewerSkia {
    @JvmStatic
    fun main(args: Array<String>) {
        with(FacetWrapDemo()) {
            PlotViewerWindowSkia(
                "Facet wrap (Skia)",
                null,
                plotSpecList().first(),
//                    Dimension(900, 700),
                preserveAspectRatio = false
            ).open()
        }
    }
}
