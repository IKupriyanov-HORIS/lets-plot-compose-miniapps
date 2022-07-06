/*
 * Copyright (c) 2021. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package jetbrains.datalore.vis.swing.skia

import jetbrains.datalore.base.logging.PortableLogging
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.swing.PlotSpecComponentProvider
import jetnrains.datalore.vis.swing.skia.SkiaMapperPanel
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
    svgComponentFactory = { svg: SvgSvgElement -> SkiaMapperPanel(svg) },
    executor = executor,
    computationMessagesHandler = computationMessagesHandler
) {

    /**
     * Override when in in IDEA plugin.
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

    companion object {
        private val LOG = PortableLogging.logger(DefaultPlotComponentProviderSkia::class)
    }
}