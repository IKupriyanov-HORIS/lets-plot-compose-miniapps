package me.ikupriyanov.demo.svg

import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.base.values.Color
import jetbrains.datalore.base.values.FontFace
import jetbrains.datalore.base.values.FontFamily
import jetbrains.datalore.plot.builder.interact.TooltipSpec
import jetbrains.datalore.plot.builder.tooltip.TooltipBox
import jetbrains.datalore.vis.StyleSheet
import jetbrains.datalore.vis.TextStyle
import jetbrains.datalore.vis.svg.SvgCssResource
import jetbrains.datalore.vis.svg.SvgStyleElement
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.svgToString.SvgToString
import me.ikupriyanov.demo.DemoUtil
import javax.swing.SwingUtilities

fun main() {
/*
<svg xmlns="http://www.w3.org/2000/svg" width="600.0" height="400.0">
  <style type="text/css">
  .tooltip-title {
   fill: #000000;
   font-family: serif;
   font-size: 14.0px;
   font-weight: bold;
   font-style: normal;
}
.tooltip-label {
   fill: #808080;
   font-family: serif;
   font-size: 10.0px;
   font-weight: normal;
   font-style: normal;
}
.tooltip-text {
   fill: #808080;
   font-family: serif;
   font-size: 10.0px;
   font-weight: normal;
   font-style: normal;
}

  </style>
  <g transform="">
    <g>
      <path stroke="rgb(0,0,0)" stroke-opacity="1.0" stroke-width="2.0" fill="rgb(255,255,255)" fill-opacity="1.0" d="M142.0 46.0 L142.0 46.0 C142.0 46.0 146.0 46.0 146.0 42.0 L146.0 29.0 L146.0 17.0 L146.0 4.0 C146.0 4.0 146.0 0.0 142.0 0.0 L79.0 0.0 L67.0 0.0 L4.0 0.0 C4.0 0.0 0.0 0.0 0.0 4.0 L0.0 17.0 L0.0 29.0 L0.0 42.0 C0.0 42.0 0.0 46.0 4.0 46.0 L67.0 46.0 L83.0 90.0 L79.0 46.0 L142.0 46.0 ">
      </path>
    </g>
    <g>
      <svg xmlns="http://www.w3.org/2000/svg" x="0.0" y="0.0" width="146.0" height="46.0">
        <svg xmlns="http://www.w3.org/2000/svg" x="0.0" y="0.0" width="0.0" height="0.0">
        </svg>
        <svg xmlns="http://www.w3.org/2000/svg" x="27.0" y="10.0" width="136.0" height="36.0">
          <g>
            <text class="tooltip-label" x="-0.0" y="0.0">
            sepal width (cm)
            </text>
          </g>
          <g>
            <text class="tooltip-text" style="fill:#808080;" x="109.0" y="0.0" text-anchor="end">
            2.974
            </text>
          </g>
          <g>
            <text class="tooltip-text" style="fill:#808080;" x="54.5" y="16.0" text-anchor="middle">
            0.79
            </text>
          </g>
          <path d="M2.0 13.0 H-39.0 " stroke-width="0.7" stroke-opacity="1.0" stroke="rgb(210,210,210)">
          </path>
        </svg>
      </svg>
      <path fill-opacity="1.0" fill="rgb(0,255,0)" d="M10.0 10.0 H11.5 V36.0 H10.0 V10.0 ">
      </path>
      <path fill-opacity="1.0" fill="rgb(255,192,0)" d="M11.5 10.0 H15.5 V36.0 H11.5 V10.0 ">
      </path>
      <path fill-opacity="1.0" fill="rgb(0,255,0)" d="M15.5 10.0 H17.0 V36.0 H15.5 V10.0 ">
      </path>
    </g>
  </g>
</svg>
 */
    val doc = SvgSvgElement()
    val textStyles: Map<String, TextStyle> = mapOf(
        "tooltip-title" to TextStyle(FontFamily.SERIF.name, face = FontFace.BOLD, size = 14.0, color = Color.BLACK),
        "tooltip-label" to TextStyle(FontFamily.SERIF.name, face = FontFace.NORMAL, size = 10.0, color = Color.GRAY),
        "tooltip-text" to TextStyle(FontFamily.SERIF.name, face = FontFace.NORMAL, size = 10.0, color = Color.GRAY)
    )

    doc.children().add(createStyleElement(textStyles))
    doc.width().set(600.0)
    doc.height().set(400.0)
    val tooltip = TooltipBox()
    doc.children().add(tooltip.rootGroup)

    DemoUtil.show(doc, "Tooltip DemoA")
    SwingUtilities.invokeLater {
        tooltip
            .apply {
                update(
                    fillColor = Color.LIGHT_YELLOW,
                    textColor = Color.BLUE,
                    borderColor = Color.BLACK,
                    strokeWidth = 2.0,
                    lines = listOf(
                        TooltipSpec.Line.withLabelAndValue("some label:", "value"),
                        TooltipSpec.Line.withValue("only value"),
                    ),
                    title = null,
                    textClassName = "plot-text",
                    rotate = false,
                    tooltipMinWidth = null,
                    borderRadius = 4.0,
                    markerColors = listOf(Color.DARK_GREEN, Color.GRAY)
                )

                setPosition(
                    tooltipCoord = DoubleVector(0.0, 0.0),
                    pointerCoord = DoubleVector(83.0, 90.0),
                    orientation = TooltipBox.Orientation.VERTICAL
                )
            }
        SwingUtilities.invokeLater {
            val str = SvgToString(null).render(doc)
            println(str)
        }
    }
}

private fun createStyleElement(textStyles: Map<String, TextStyle>): SvgStyleElement {
    return SvgStyleElement(object : SvgCssResource {
        override fun css(): String {
            return StyleSheet(textStyles, defaultFamily = FontFamily.SERIF.toString()).toCSS()
        }
    })
}
