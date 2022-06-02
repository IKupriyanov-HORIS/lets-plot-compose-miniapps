/*
 * Copyright (c) 2022. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package jetbrains.datalore.vis.svgMapper.skia.drawing

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Font
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Typeface

class Text : Figure() {
    var x: Float by visualProp(0.0f)
    var y: Float by visualProp(0.0f)
    var text: String by visualProp("")
    var fontFamily: String? by visualProp(null)
    var fontStyle: FontStyle by visualProp(FontStyle.NORMAL)
    var fontSize by visualProp(10.0f)

    private val font by dependencyProp(Text::fontFamily, Text::fontStyle, Text::fontSize) {
        Font(
            fontFamily
                ?.let { Typeface.makeFromName(it, fontStyle) }
                ?: run { Typeface.makeDefault() },
            fontSize
        )
    }

    override fun doDraw(canvas: Canvas) {
        fillPaint?.let { canvas.drawString(text, x, y, font, it) }
        strokePaint?.let { canvas.drawString(text, x, y, font, it) }
    }
}
