package org.jetbrains.skiko.sample

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import jetbrains.datalore.vis.svg.SvgSvgElement

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        setContentView(layout, layout.layoutParams)

        val holder = LinearLayout(this).apply {
            //layoutParams = ViewGroup.LayoutParams(1000, 1200)
        }
        layout.addView(holder)

        //val skiaLayer = SkiaLayer().apply { gesturesToListen = SkikoGestureEventKind.values() }
        //skiaLayer.skikoView = GenericSkikoView(skiaLayer, RotatingSquare())
        //skiaLayer.attachTo(holder)

        val svgRoot = SvgSvgElement(500.0, 500.0)
        svgRoot.children().add(DemoModelA.createModel())
        val v = SkiaMapperLayer(svgRoot)
        v.attachTo(holder)
    }
}
