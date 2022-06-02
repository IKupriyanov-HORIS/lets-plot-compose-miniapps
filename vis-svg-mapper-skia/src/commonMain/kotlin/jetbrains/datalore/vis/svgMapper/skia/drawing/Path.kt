/*
 * Copyright (c) 2022. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package jetbrains.datalore.vis.svgMapper.skia.drawing

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Path

class Path: Figure() {
    var skiaPath: Path? by visualProp(null)

    override fun doDraw(canvas: Canvas) {
        if (skiaPath == null) {
            return
        }

        fillPaint?.let { canvas.drawPath(skiaPath!!, it) }
        strokePaint?.let { canvas.drawPath(skiaPath!!, it) }

    }
}