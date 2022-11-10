package jetbrains.datalore.vis.svgMapper.skia

import android.content.Context
import android.widget.LinearLayout
import jetbrains.datalore.vis.svg.SvgSvgElement
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkikoGestureEventKind.*

object SkikoAndroidUtil {
    fun Context.wrap(skiaWidget: SkiaWidget): LinearLayout {
        val container = LinearLayout(this)
        val density = this.resources.displayMetrics.density
        container.layoutParams = LinearLayout.LayoutParams(
            (skiaWidget.width()  * density).toInt(),
            (skiaWidget.height() * density).toInt()
        )

        skiaWidget.nativeLayer.attachTo(container)
        return container
    }

    fun svgView(svg: SvgSvgElement): SkiaWidget {
        return SkiaWidget(svg, SkiaLayer()) { skiaLayer, skikoView ->
            skiaLayer.gesturesToListen = arrayOf(PAN, DOUBLETAP, TAP, LONGPRESS)
            skiaLayer.skikoView = skikoView
        }
    }
}