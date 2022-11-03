package jetbrains.datalore.vis.svgMapper.skia

import jetbrains.datalore.base.event.Button
import jetbrains.datalore.base.event.KeyModifiers
import jetbrains.datalore.base.event.KeyModifiers.Companion.emptyModifiers
import jetbrains.datalore.base.event.MouseEvent
import jetbrains.datalore.base.event.MouseEventSpec
import jetbrains.datalore.mapper.core.MappingContext
import jetbrains.datalore.vis.svg.SvgNodeContainer
import jetbrains.datalore.vis.svg.SvgSvgElement
import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.*

class SvgSkikoLayer(
    private val svg: SvgSvgElement,
    val skiaLayer: SkiaLayer
) {
    @Suppress("unused")
    private val nodeContainer = SvgNodeContainer(svg)  // attach root
    private val rootMapper = SvgSvgElementMapper(svg, SvgSkiaPeer())
    private var mouseEventHandler: (MouseEventSpec, MouseEvent) -> Unit = EMPTY_MOUSE_EVENT_HANDLER

    init {
        rootMapper.attachRoot(MappingContext())

        skiaLayer.skikoView = object : SkikoView {
            override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
                canvas.scale(skiaLayer.contentScale, skiaLayer.contentScale)
                canvas.drawDrawable(rootMapper.target)
            }

            override fun onGestureEvent(event: SkikoGestureEvent) {
                when (event.kind) {
                    SkikoGestureEventKind.LONGPRESS -> MouseEventSpec.MOUSE_LEFT
                    SkikoGestureEventKind.PAN -> MouseEventSpec.MOUSE_MOVED
                    SkikoGestureEventKind.TAP -> MouseEventSpec.MOUSE_MOVED
                    else -> null
                }?.let {
                    mouseEventHandler(it, event.toMouseEvent())
                    skiaLayer.needRedraw()
                }
            }

            override fun onPointerEvent(event: SkikoPointerEvent) {
                when (event.kind) {
                    SkikoPointerEventKind.UP -> MouseEventSpec.MOUSE_RELEASED
                    SkikoPointerEventKind.DOWN -> MouseEventSpec.MOUSE_PRESSED
                    SkikoPointerEventKind.MOVE -> MouseEventSpec.MOUSE_MOVED
                    SkikoPointerEventKind.DRAG -> MouseEventSpec.MOUSE_DRAGGED
                    SkikoPointerEventKind.ENTER -> MouseEventSpec.MOUSE_ENTERED
                    SkikoPointerEventKind.EXIT -> MouseEventSpec.MOUSE_LEFT
                    else -> null
                }?.let {
                    mouseEventHandler(it, event.toMouseEvent())
                    skiaLayer.needRedraw()
                }
            }
        }
    }

    fun setMouseEventListener(handler: (MouseEventSpec, MouseEvent) -> Unit) {
        mouseEventHandler = handler
    }

    companion object {
        val EMPTY_MOUSE_EVENT_HANDLER: (MouseEventSpec, MouseEvent) -> Unit = { _, _ -> }
    }
}


fun skikoSvgLayer(svg: SvgSvgElement, skiaLayer: SkiaLayer): SvgSkikoLayer {
    return SvgSkikoLayer(svg, skiaLayer)
}

private fun SkikoGestureEvent.toMouseEvent(): MouseEvent {
    return MouseEvent(
        x = x.toInt(),
        y = y.toInt(),
        button = Button.NONE,
        modifiers = emptyModifiers()
    )
}

private fun SkikoPointerEvent.toMouseEvent(): MouseEvent {
    return MouseEvent(
        x = x.toInt(),
        y = y.toInt(),
        button = when (button) {
            SkikoMouseButtons.LEFT -> Button.LEFT
            SkikoMouseButtons.MIDDLE -> Button.MIDDLE
            SkikoMouseButtons.RIGHT -> Button.RIGHT
            else -> Button.NONE.also { println("Unsupported button: $button") }
        },
        modifiers = KeyModifiers(
            isCtrl = modifiers.has(SkikoInputModifiers.CONTROL),
            isAlt = modifiers.has(SkikoInputModifiers.ALT),
            isShift = modifiers.has(SkikoInputModifiers.SHIFT),
            isMeta = modifiers.has(SkikoInputModifiers.META)
        )
    )
}
