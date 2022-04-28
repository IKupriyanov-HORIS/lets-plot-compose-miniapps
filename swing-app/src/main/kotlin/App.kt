/*
 * Copyright (c) 2022. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

/*
 * Copyright (c) 2022. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package svgMapperDemo

import DemoModelA
import SkiaMapperPanel
import jetbrains.datalore.vis.svg.SvgSvgElement
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

fun main() {
    val svgRoot = SvgSvgElement(500.0, 500.0)
    svgRoot.children().add(DemoModelA.createModel())
    SwingUtilities.invokeLater {
        val window = JFrame()
        window.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        window.title = "SwingSkiaWindow"

        window.contentPane.add(SkiaMapperPanel(svgRoot), BorderLayout.CENTER)

        window.pack()
        window.setLocationRelativeTo(null)
        window.isVisible = true
    }
}
