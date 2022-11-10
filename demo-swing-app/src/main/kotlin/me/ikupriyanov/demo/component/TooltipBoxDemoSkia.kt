/*
 * Copyright (c) 2019. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package me.ikupriyanov.demo.component

import jetbrains.datalore.vis.svgMapper.skia.SvgViewerDemoWindowSkia
import java.awt.EventQueue.invokeLater

fun main() {
    with(TooltipBoxDemo()) {
        val models = createModels()
        val svgRoots = createSvgRoots(models.map { it.first })
        SvgViewerDemoWindowSkia(
            "Tooltip box",
            svgRoots,
        ).open()

        // TODO: Fix hack. Wait for attach - TooltipBox uses SvgPeer not available before.
        invokeLater {
            models.forEach { it.second() }
        }
    }
}
