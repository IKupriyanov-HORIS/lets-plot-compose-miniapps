package me.ikupriyanov.demo.svg

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import jetbrains.datalore.mapper.core.MappingContext
import jetbrains.datalore.vis.svg.SvgNodeContainer
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.svgMapper.skia.SvgSkiaPeer
import jetbrains.datalore.vis.svgMapper.skia.SvgSvgElementMapper

@Composable
fun Svg(svg: SvgSvgElement, width: Double? = null, height: Double? = null, density: Float = 1f) {
    @Suppress("UNUSED_VARIABLE")
    val nodeContainer = SvgNodeContainer(svg)  // attach root

    val rootMapper = SvgSvgElementMapper(svg, SvgSkiaPeer())
    rootMapper.attachRoot(MappingContext())

    @Suppress("NAME_SHADOWING")
    val width = width ?: svg.width().get()!!
    @Suppress("NAME_SHADOWING")
    val height = height ?: svg.height().get()!!

    Canvas(modifier = Modifier.size(width.dp, height.dp)) {
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.scale(density, density)
            canvas.nativeCanvas.drawDrawable(rootMapper.target)
        }
    }
}