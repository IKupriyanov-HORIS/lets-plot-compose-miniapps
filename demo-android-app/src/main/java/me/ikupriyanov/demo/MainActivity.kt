package me.ikupriyanov.demo

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import jetbrains.datalore.vis.svg.SvgElement
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.svgMapper.demo.DemoModelA
import jetbrains.datalore.vis.svgMapper.demo.DemoModelB
import jetbrains.datalore.vis.svgMapper.demo.DemoModelC

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        setContentView(layout, layout.layoutParams)

        layout.addView(addSvg("Demo A", DemoModelA.createModel()))
        layout.addView(addSvg("Demo B", DemoModelB.createModel()))
        layout.addView(addSvg("Demo C", DemoModelC.createModel()))
    }

    private fun addSvg(title: String, svg: SvgElement): LinearLayout {
        val svgContainer = LinearLayout(this)
        svgContainer.orientation = LinearLayout.VERTICAL
        svgContainer.layoutParams = LinearLayout.LayoutParams(500, 500)
        svgContainer.addView(TextView(this).apply {
            text = title
        })

        SkiaMapperLayer(
            SvgSvgElement(500.0, 500.0).apply {
                children().add(svg)
            }
        ).attachTo(svgContainer)

        return svgContainer
    }
}