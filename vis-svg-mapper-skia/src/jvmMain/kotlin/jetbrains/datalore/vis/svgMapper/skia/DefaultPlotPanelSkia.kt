package jetbrains.datalore.vis.svgMapper.skia

import jetbrains.datalore.vis.swing.PlotPanel

open class DefaultPlotPanelSkia(
    processedSpec: MutableMap<String, Any>,
    preserveAspectRatio: Boolean,
    preferredSizeFromPlot: Boolean,
    repaintDelay: Int,  // ms,
    computationMessagesHandler: (List<String>) -> Unit
) : PlotPanel(
    plotComponentProvider = DefaultPlotComponentProviderSkia(
        processedSpec = processedSpec,
        preserveAspectRatio = preserveAspectRatio,
        executor = DefaultSwingContextSkia.AWT_EDT_EXECUTOR,
        computationMessagesHandler = computationMessagesHandler
    ),
    preferredSizeFromPlot = preferredSizeFromPlot,
    repaintDelay = repaintDelay,
    applicationContext = DefaultSwingContextSkia()
)