package jetbrains.datalore.vis.swing.skia

import jetbrains.datalore.base.event.Button
import jetbrains.datalore.base.event.MouseEvent
import jetbrains.datalore.base.event.MouseEventSpec
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.svgMapper.skia.SkiaWidget
import org.jetbrains.skiko.SkiaLayer
import java.awt.Component
import java.time.Instant

object SkikoAwtUtil {
    fun svgView(svg: SvgSvgElement): SkiaWidget {
        return SkiaWidget(svg, SkiaLayer()) { skiaLayer, skikoView ->
            skiaLayer.addView(skikoView)
        }
    }

    fun toAwtEvent(eventSpec: MouseEventSpec, event: MouseEvent, source: Component): java.awt.event.MouseEvent {
        val id = when (eventSpec) {
            MouseEventSpec.MOUSE_ENTERED -> java.awt.event.MouseEvent.MOUSE_ENTERED
            MouseEventSpec.MOUSE_LEFT -> java.awt.event.MouseEvent.MOUSE_EXITED
            MouseEventSpec.MOUSE_MOVED -> java.awt.event.MouseEvent.MOUSE_MOVED
            MouseEventSpec.MOUSE_DRAGGED -> java.awt.event.MouseEvent.MOUSE_DRAGGED
            MouseEventSpec.MOUSE_CLICKED -> java.awt.event.MouseEvent.MOUSE_CLICKED
            MouseEventSpec.MOUSE_DOUBLE_CLICKED -> java.awt.event.MouseEvent.MOUSE_CLICKED
            MouseEventSpec.MOUSE_PRESSED -> java.awt.event.MouseEvent.MOUSE_PRESSED
            MouseEventSpec.MOUSE_RELEASED -> java.awt.event.MouseEvent.MOUSE_RELEASED
        }

        val modifiers = 0 // ignore for a while
        val clickCount = when (eventSpec) {
            MouseEventSpec.MOUSE_CLICKED -> 1
            MouseEventSpec.MOUSE_DOUBLE_CLICKED -> 2
            else -> 0
        }

        val button = when (event.button) {
            null, Button.NONE -> java.awt.event.MouseEvent.NOBUTTON
            Button.LEFT -> java.awt.event.MouseEvent.BUTTON1
            Button.MIDDLE -> java.awt.event.MouseEvent.BUTTON2
            Button.RIGHT -> java.awt.event.MouseEvent.BUTTON3
        }
        return java.awt.event.MouseEvent(source, id, Instant.now().toEpochMilli(), modifiers, event.x, event.y, clickCount, false, button)
    }
}