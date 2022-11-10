package me.ikupriyanov.demo.svg

import jetbrains.datalore.vis.svgMapper.demo.DemoModelC
import jetbrains.datalore.vis.svgMapper.skia.SvgViewerDemoWindowSkia

fun main() {
    SvgViewerDemoWindowSkia("SwingSkia DemoC", listOf(DemoModelC.createModel()))
}
