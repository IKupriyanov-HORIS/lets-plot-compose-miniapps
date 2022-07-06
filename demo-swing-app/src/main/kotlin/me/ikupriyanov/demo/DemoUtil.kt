package me.ikupriyanov.demo

import jetbrains.datalore.vis.svg.SvgElement
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetnrains.datalore.vis.swing.skia.SkiaMapperPanel
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

object DemoUtil {
    fun show(svg: SvgElement, title: String) {
        val svgRoot = SvgSvgElement(500.0, 500.0)
        svgRoot.children().add(svg)
        SwingUtilities.invokeLater {
            val window = JFrame()
            window.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
            window.title = title

            window.contentPane.add(SkiaMapperPanel(svgRoot), BorderLayout.CENTER)

            window.pack()
            window.setLocationRelativeTo(null)
            window.isVisible = true
        }
    }
}