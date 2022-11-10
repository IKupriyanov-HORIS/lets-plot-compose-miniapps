package jetbrains.datalore.vis.svgMapper.skia

import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.swing.PlotSpecComponentProvider
import javax.swing.JComponent
import javax.swing.JScrollPane
import javax.swing.ScrollPaneConstants

open class DefaultPlotComponentProviderSkia(
    processedSpec: MutableMap<String, Any>,
    preserveAspectRatio: Boolean,
    executor: (() -> Unit) -> Unit,
    computationMessagesHandler: (List<String>) -> Unit
) : PlotSpecComponentProvider(
    processedSpec = processedSpec,
    preserveAspectRatio = preserveAspectRatio,
    svgComponentFactory = { svg: SvgSvgElement -> SkiaMapperComponent(svg) },
    executor = executor,
    computationMessagesHandler = computationMessagesHandler
) {

    /**
     * Override when in IDEA plugin.
     * Use: JBScrollPane
     */
    override fun createScrollPane(plotComponent: JComponent): JScrollPane {
        return JScrollPane(
            plotComponent,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        ).apply {
            border = null
        }
    }
}