import jetbrains.datalore.base.registration.Disposable
import jetbrains.datalore.mapper.core.MappingContext
import jetbrains.datalore.vis.svg.SvgNodeContainer
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.svgMapper.skia.SvgSkiaPeer
import jetbrains.datalore.vis.svgMapper.skia.SvgSvgElementMapper
import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkikoView
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.SwingUtilities

class SkiaMapperPanel(
    private val svg: SvgSvgElement,
) : JPanel(), Disposable {
    private val nodeContainer = SvgNodeContainer(svg)  // attach root
    private val skiaLayer = SkiaLayer()

    init {
        layout = GridLayout(0, 1, 5, 5)
        border = BorderFactory.createLineBorder(Color.ORANGE, 1)

        val rootMapper = SvgSvgElementMapper(svg, SvgSkiaPeer())
        rootMapper.attachRoot(MappingContext())
        val plotView = object : SkikoView {
            override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
                canvas.drawDrawable(rootMapper.target)
            }
        }

        skiaLayer.addView(plotView)
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
