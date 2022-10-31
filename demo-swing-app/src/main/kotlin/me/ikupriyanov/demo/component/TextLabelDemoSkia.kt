/*
 * Copyright (c) 2019. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package me.ikupriyanov.demo.component

import jetbrains.datalore.vis.swing.skia.SvgViewerDemoWindowSkia

fun main() {
    with(TextLabelDemo()) {
        SvgViewerDemoWindowSkia(
            "Text label",
            createSvgRoots(listOf(createModel())),
        ).open()
    }
}
