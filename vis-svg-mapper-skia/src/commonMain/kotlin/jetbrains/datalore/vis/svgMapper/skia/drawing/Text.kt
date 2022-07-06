/*
 * Copyright (c) 2022. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package jetbrains.datalore.vis.svgMapper.skia.drawing

import org.jetbrains.skia.*

class Text : Figure() {
    var textOrigin: VerticalAlignment? by visualProp(null)
    var textAlignment: HorizontalAlignment? by visualProp(null)
    var x: Float by visualProp(0.0f)
    var y: Float by visualProp(0.0f)
    var text: String by visualProp("")
    var fontFamily: String? by visualProp(null)
    var fontStyle: FontStyle by visualProp(FontStyle.NORMAL)
    var fontSize by visualProp(16.0f)

    private val cx by dependencyProp(Text::textLine, Text::textAlignment) {
        when (textAlignment) {
            HorizontalAlignment.LEFT -> 0.0f
            HorizontalAlignment.CENTER -> -textLine.width / 2.0f
            HorizontalAlignment.RIGHT -> -textLine.width
            null -> 0.0f
        }
    }

    private val cy by dependencyProp(Text::textLine, Text::textOrigin) {
        when (textOrigin) {
            VerticalAlignment.TOP -> textLine.xHeight
            VerticalAlignment.CENTER -> textLine.xHeight / 2.0f
            VerticalAlignment.BOTTOM -> -textLine.xHeight
            null -> 0.0f
        }
    }

    init {
        fill = Color4f(Color.BLACK)
    }

    private val font by dependencyProp(Text::fontFamily, Text::fontStyle, Text::fontSize) {
        Font(
            fontFamily
                ?.let { Typeface.makeFromName(it, fontStyle) }
                ?: run { Typeface.makeDefault() },
            fontSize
        )
    }

    private val textLine by dependencyProp(Text::text, Text::font) {
        TextLine.make(text, font)
    }

    override fun doDraw(canvas: Canvas) {
        fillPaint?.let { canvas.drawTextLine(textLine, x + cx, y + cy, it) }
        strokePaint?.let { canvas.drawTextLine(textLine, x + cx, y + cy, it) }
    }

    override fun doGetBounds(): Rect {
        return Rect.makeXYWH(x + cx, y + cy, textLine.width, textLine.height).offset(absoluteOffsetX, absoluteOffsetY)
    }

    enum class VerticalAlignment {
        TOP,
        CENTER,
        BOTTOM
    }

    enum class HorizontalAlignment {
        LEFT,
        CENTER,
        RIGHT
    }
}
