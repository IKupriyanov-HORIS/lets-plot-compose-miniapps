/*
 * Copyright (c) 2022. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package jetbrains.datalore.vis.svgMapper.skia.attr

import jetbrains.datalore.base.geometry.DoubleRectangle
import jetbrains.datalore.vis.svg.*
import jetbrains.datalore.vis.svgMapper.skia.SvgTransformParser.parseSvgTransform
import jetbrains.datalore.vis.svgMapper.skia.drawing.Element
import jetbrains.datalore.vis.svgMapper.skia.drawing.SkPath
import org.jetbrains.skia.Rect

internal abstract class SvgAttrMapping<in TargetT : Element> {
    open fun setAttribute(target: TargetT, name: String, value: Any?) {
        when (name) {
            SvgGraphicsElement.VISIBILITY.name -> TODO() //target.isVisible = visibilityAsBoolean(value)
            SvgGraphicsElement.OPACITY.name -> TODO() //target.opacity = asDouble(value)
            SvgGraphicsElement.CLIP_BOUNDS_JFX.name -> target.clipPath = (value as? DoubleRectangle)?.let {
                SkPath().addRect(Rect.makeLTRB(it.left.toFloat(), it.top.toFloat(), it.right.toFloat(), it.bottom.toFloat()))
            }
            SvgGraphicsElement.CLIP_PATH.name -> Unit // Not supported.
            SvgConstants.SVG_STYLE_ATTRIBUTE -> setStyle(value as? String ?: "", target)
            SvgStylableElement.CLASS.name -> setStyleClass(value as String?, target)
            SvgTransformable.TRANSFORM.name -> setTransform((value as SvgTransform).toString(), target)
            else -> throw IllegalArgumentException("Unsupported attribute `$name` in ${target::class.simpleName}")
        }
    }

    private fun visibilityAsBoolean(value: Any?): Boolean {
        return when (value) {
            is Boolean -> value
            is SvgGraphicsElement.Visibility -> value == SvgGraphicsElement.Visibility.VISIBLE
            is String -> value == SvgGraphicsElement.Visibility.VISIBLE.toString() || asBoolean(value)
            else -> false
        }
    }

    companion object {
        private fun setStyle(value: String, target: Element) {
            println("setStyle is not implemented. style=`$value`")

            //val valueFx = value.split(";").joinToString(";") { if (it.isNotEmpty()) "-fx-${it.trim()}" else it }
            //target.style = valueFx
        }

        private fun setStyleClass(value: String?, target: Element) {
            target.styleClass = value?.split(" ")
        }

        private fun setTransform(value: String, target: Element) {
            target.transforms.clear()
            target.transforms.addAll(parseSvgTransform(value))
        }

        val Any.asFloat: Float
            get() = when (this) {
                is Number -> this.toFloat()
                is String -> this.toFloat()
                else -> error("Unsupported float value: $this")
            }

        fun asDouble(value: Any?): Double {
            if (value is Double) return value
            return (value as String).toDouble()
        }

        fun asBoolean(value: Any?): Boolean {
            return (value as? String)?.toBoolean() ?: false
        }
    }
}