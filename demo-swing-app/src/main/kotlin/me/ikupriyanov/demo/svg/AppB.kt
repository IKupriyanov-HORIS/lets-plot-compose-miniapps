package me.ikupriyanov.demo.svg

import jetbrains.datalore.vis.svgMapper.demo.DemoModelB
import me.ikupriyanov.demo.utils.SvgWindowSkia

fun main() {
    SvgWindowSkia("SwingSkia DemoB", listOf(DemoModelB.createModel()))
}
