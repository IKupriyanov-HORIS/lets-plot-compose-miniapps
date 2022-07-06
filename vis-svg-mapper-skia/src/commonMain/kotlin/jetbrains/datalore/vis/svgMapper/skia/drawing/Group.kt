/*
 * Copyright (c) 2022. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package jetbrains.datalore.vis.svgMapper.skia.drawing

import org.jetbrains.skia.Rect
import kotlin.math.max
import kotlin.math.min

class Group: Parent() {
    override val offsetX: Float get() = translateX
    override val offsetY: Float get() = translateY

    override fun doGetBounds(): Rect {
        return children.fold<Element, Rect?>(null) { acc, element ->
            if (element.bounds.height == 0.0f && element.bounds.width == 0.0f) {
                return Rect.makeWH(0.0f, 0.0f)
            }

            acc?.let {
                Rect.makeLTRB(
                    min(it.left, element.bounds.left),
                    min(it.top, element.bounds.top),
                    max(it.right, element.bounds.right),
                    max(it.bottom, element.bounds.bottom)
                )

            } ?: element.bounds
        } ?: Rect.makeWH(0.0f, 0.0f)
    }
}
