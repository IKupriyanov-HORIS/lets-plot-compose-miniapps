package me.ikupriyanov.demo.svg

import jetbrains.datalore.vis.svgMapper.demo.DemoModelC
import me.ikupriyanov.demo.utils.SvgWindowSkia

fun main() {
    SvgWindowSkia("SwingSkia DemoC", listOf(DemoModelC.createModel()))
}
