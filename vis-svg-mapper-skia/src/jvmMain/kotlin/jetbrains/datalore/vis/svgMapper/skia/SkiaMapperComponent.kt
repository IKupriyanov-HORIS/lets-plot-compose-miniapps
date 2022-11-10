package jetbrains.datalore.vis.svgMapper.skia

import jetbrains.datalore.base.event.MouseEventSpec
import jetbrains.datalore.base.registration.Disposable
import jetbrains.datalore.vis.svg.SvgSvgElement
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.SwingUtilities

class SkiaMapperComponent(
    private val svg: SvgSvgElement
) : JPanel(), Disposable {
    private val skiaWidget = SkikoAwtUtil.svgView(svg)

    init {
        layout = GridLayout(0, 1, 5, 5)
        border = BorderFactory.createLineBorder(Color.ORANGE, 1)
        skiaWidget.nativeLayer.attachTo(this)
        skiaWidget.setMouseEventListener { mouseEventSpec, mouseEvent ->
            val awtEvent = SkikoAwtUtil.toAwtEvent(mouseEventSpec, mouseEvent, this)
            when (mouseEventSpec) {
                MouseEventSpec.MOUSE_MOVED -> mouseMotionListeners.forEach { it.mouseMoved(awtEvent) }
                MouseEventSpec.MOUSE_DRAGGED -> mouseMotionListeners.forEach { it.mouseDragged(awtEvent) }

                MouseEventSpec.MOUSE_RELEASED -> mouseListeners.forEach { it.mouseReleased(awtEvent) }
                MouseEventSpec.MOUSE_PRESSED -> mouseListeners.forEach { it.mousePressed(awtEvent) }
                MouseEventSpec.MOUSE_ENTERED -> mouseListeners.forEach { it.mouseEntered(awtEvent) }
                MouseEventSpec.MOUSE_LEFT -> mouseListeners.forEach { it.mouseExited(awtEvent) }
                MouseEventSpec.MOUSE_CLICKED -> mouseListeners.forEach { it.mouseClicked(awtEvent) }
                MouseEventSpec.MOUSE_DOUBLE_CLICKED -> mouseListeners.forEach { it.mouseClicked(awtEvent) }
                else -> println(mouseEventSpec)
            }
        }
        SwingUtilities.invokeLater { skiaWidget.nativeLayer.needRedraw() }
    }

    override fun getPreferredSize(): Dimension {
        val size = Dimension(svg.width().get()!!.toInt(), svg.height().get()!!.toInt())
        skiaWidget.nativeLayer.preferredSize = size
        return size
    }

    override fun dispose() {
        skiaWidget.nativeLayer.dispose()
    }
}
