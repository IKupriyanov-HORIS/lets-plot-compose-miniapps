package me.ikupriyanov.demo.svg

import jetbrains.datalore.vis.svgMapper.demo.DemoModelB
import jetbrains.datalore.vis.swing.skia.SvgViewerDemoWindowSkia

fun main() {
    SvgViewerDemoWindowSkia("SwingSkia DemoB", listOf(DemoModelB.createModel()))
}
