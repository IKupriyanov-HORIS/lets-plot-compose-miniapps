package jetbrains.datalore.vis.swing.skia

import jetbrains.datalore.vis.demoUtils.swing.SvgViewerDemoWindowBase
import jetbrains.datalore.vis.svg.SvgSvgElement
import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.JComponent

class SvgViewerDemoWindowSkia(
    title: String,
    svgRoots: List<SvgSvgElement>,
    maxCol: Int = 2,
) : SvgViewerDemoWindowBase(
    title,
    svgRoots = svgRoots,
    maxCol = maxCol,
) {
    override fun createPlotComponent(svgRoot: SvgSvgElement): JComponent {
        val component = SkiaMapperPanel(svgRoot)
        component.border = BorderFactory.createLineBorder(Color.ORANGE, 1)
        return component
    }
}