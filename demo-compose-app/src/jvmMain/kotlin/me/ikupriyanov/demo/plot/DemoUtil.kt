package me.ikupriyanov.demo.plot

import jetbrains.datalore.plot.MonolithicCommon
import jetbrains.datalore.vis.swing.skia.DefaultPlotPanelSkia


fun rawPlotPanel(rawSpec: MutableMap<String, Any>): DefaultPlotPanelSkia {
    return DefaultPlotPanelSkia(
        MonolithicCommon.processRawSpecs(rawSpec, frontendOnly = false),
        preserveAspectRatio = true,
        preferredSizeFromPlot = false,
        repaintDelay = 300
    ) { }
}