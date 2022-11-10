package me.ikupriyanov.demo.svg

import jetbrains.datalore.vis.svgMapper.demo.DemoModelA
import jetbrains.datalore.vis.svgMapper.skia.SvgViewerDemoWindowSkia

fun main() {
    SvgViewerDemoWindowSkia( "SwingSkia DemoA", listOf(DemoModelA.createModel()))
}
