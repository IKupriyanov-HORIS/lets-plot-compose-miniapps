package org.jetbrains.skiko.sample

import jetbrains.datalore.mapper.core.MappingContext
import jetbrains.datalore.vis.svg.SvgNodeContainer
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.svgMapper.skia.SvgSkiaPeer
import jetbrains.datalore.vis.svgMapper.skia.SvgSvgElementMapper
import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkikoView

class SkiaMapperLayer(
    private val svg: SvgSvgElement
) : SkiaLayer() {
    private val nodeContainer = SvgNodeContainer(svg)  // attach root

    init {
        val rootMapper = SvgSvgElementMapper(svg, SvgSkiaPeer())
        rootMapper.attachRoot(MappingContext())
        val plotView = object : SkikoView {
            override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
                canvas.drawDrawable(rootMapper.target)
            }
        }

        skikoView = plotView
    }
}