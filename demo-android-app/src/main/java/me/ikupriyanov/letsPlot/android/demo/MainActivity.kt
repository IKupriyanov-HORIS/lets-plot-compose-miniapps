package me.ikupriyanov.letsPlot.android.demo

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.ScrollView
import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.plotDemo.model.plotConfig.Area
import jetbrains.datalore.vis.svgMapper.skia.MonolithicSkia
import jetbrains.datalore.vis.svgMapper.skia.SkikoAndroidUtil.svgView
import jetbrains.datalore.vis.svgMapper.skia.SkikoAndroidUtil.wrap

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        val scrollView = ScrollView(this)
        scrollView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        scrollView.addView(layout)
        setContentView(scrollView, layout.layoutParams)

        //layout.addView(wrap(svgView(DemoModelA.createModel())))
        //layout.addView(addSvg("Demo B", DemoModelB.createModel()))
        //layout.addView(addSvg("Demo C", DemoModelC.createModel()))

        with(Area()) {
            val plotView = MonolithicSkia.buildPlotFromRawSpecs(
                plotSpecList().first(),
                DoubleVector(400.0, 300.0),
                null,
                ::svgView,
                { it.invoke() },
                { strings -> strings.joinToString().also(::println) }
            )
            layout.addView(wrap(plotView))
        }
    }
}
