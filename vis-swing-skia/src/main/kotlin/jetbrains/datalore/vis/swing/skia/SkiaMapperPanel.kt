package jetbrains.datalore.vis.swing.skia

import jetbrains.datalore.base.registration.Disposable
import jetbrains.datalore.mapper.core.MappingContext
import jetbrains.datalore.vis.svg.SvgNodeContainer
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.svgMapper.skia.SvgSkiaPeer
import jetbrains.datalore.vis.svgMapper.skia.SvgSvgElementMapper
import jetbrains.datalore.vis.svgMapper.skia.drawing.traceTree
import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkikoPointerEvent
import org.jetbrains.skiko.SkikoPointerEventKind
import org.jetbrains.skiko.SkikoView
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.SwingUtilities

class SkiaMapperPanel(
    private val svg: SvgSvgElement
) : JPanel(), Disposable {
    @Suppress("unused")
    private val nodeContainer = SvgNodeContainer(svg)  // attach root
    private val skiaLayer = SkiaLayer()
    private val rootMapper = SvgSvgElementMapper(svg, SvgSkiaPeer())

    init {
        layout = GridLayout(0, 1, 5, 5)
        border = BorderFactory.createLineBorder(Color.ORANGE, 1)

        rootMapper.attachRoot(MappingContext())

        skiaLayer.addView(object : SkikoView {
            override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
                val contentScale = skiaLayer.contentScale
                canvas.scale(contentScale, contentScale)

                traceTree(rootMapper.target)
                canvas.drawDrawable(rootMapper.target)
            }

            override fun onPointerEvent(event: SkikoPointerEvent) {
                when (event.kind) {
                    SkikoPointerEventKind.UP -> mouseListeners.forEach { it.mouseReleased(event.platform) }
                    SkikoPointerEventKind.DOWN -> mouseListeners.forEach { it.mousePressed(event.platform) }
                    SkikoPointerEventKind.MOVE -> mouseMotionListeners.forEach { it.mouseMoved(event.platform) }
                    SkikoPointerEventKind.DRAG -> mouseMotionListeners.forEach { it.mouseDragged(event.platform) }
                    SkikoPointerEventKind.ENTER -> mouseListeners.forEach { it.mouseEntered(event.platform) }
                    SkikoPointerEventKind.EXIT -> mouseListeners.forEach { it.mouseExited(event.platform) }
                    SkikoPointerEventKind.SCROLL -> Unit // ignore
                    SkikoPointerEventKind.UNKNOWN -> TODO()
                }
                skiaLayer.needRedraw()
            }
        })

        skiaLayer.attachTo(this)
        SwingUtilities.invokeLater(skiaLayer::needRedraw)
    }

    override fun getPreferredSize(): Dimension {
        val size = Dimension(svg.width().get()!!.toInt(), svg.height().get()!!.toInt())
        skiaLayer.preferredSize = size
        return size
    }

    override fun dispose() {
        skiaLayer.dispose()
    }
}
