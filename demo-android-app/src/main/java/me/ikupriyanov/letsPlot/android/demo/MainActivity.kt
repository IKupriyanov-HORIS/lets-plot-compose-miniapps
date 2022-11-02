package me.ikupriyanov.letsPlot.android.demo

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.plotDemo.model.plotConfig.Area
import jetbrains.datalore.vis.svg.SvgElement
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.svgMapper.skia.Plot
import jetbrains.datalore.vis.svgMapper.skia.skikoSvgLayer
import me.ikupriyanov.letsPlot.android.skia.SkiaMapperLayer
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkikoGestureEventKind

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setContentView(layout, layout.layoutParams)

        //layout.addView(addSvg("Demo A", DemoModelA.createModel()))
        //layout.addView(addSvg("Demo B", DemoModelB.createModel()))
        //layout.addView(addSvg("Demo C", DemoModelC.createModel()))

        with(Area()) {
            val skikoLayer = Plot.buildPlotFromRawSpecs(
                plotSpecList().first(),
                DoubleVector(600.0, 300.0),
                null,
                { svg -> skikoSvgLayer(svg, SkiaLayer().apply {
                    gesturesToListen = arrayOf(SkikoGestureEventKind.PAN, SkikoGestureEventKind.DOUBLETAP, SkikoGestureEventKind.TAP, SkikoGestureEventKind.LONGPRESS)
                }) },
                { function -> function() },
                { strings -> strings.joinToString().also { print(it) } }
            )
            skikoLayer.skiaLayer

            layout.addView(addSkia("area", skikoLayer.skiaLayer))
        }
    }

    private fun addSvg(title: String, svg: SvgElement): LinearLayout {
        return addSkia(
            title,
            SkiaMapperLayer(
                SvgSvgElement(500.0, 500.0).apply {
                    children().add(svg)
                }
            )
        )
    }

    private fun addSkia(title: String, layer: SkiaLayer): LinearLayout {
        val svgContainer = LinearLayout(this)
        svgContainer.orientation = LinearLayout.VERTICAL
        svgContainer.layoutParams = LinearLayout.LayoutParams(1800, 900)
        //svgContainer.addView(TextView(this).apply {
        //    text = title
        //})

        layer.attachTo(svgContainer)
        return svgContainer
    }
}