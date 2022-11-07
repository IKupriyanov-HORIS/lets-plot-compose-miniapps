package jetbrains.datalore.vis.svgMapper.skia

import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.plot.MonolithicCommon
import jetbrains.datalore.plot.builder.PlotContainer
import jetbrains.datalore.plot.config.PlotConfig
import jetbrains.datalore.plot.config.PlotConfigClientSide
import jetbrains.datalore.plot.server.config.BackendSpecTransformUtil
import jetbrains.datalore.vis.svg.SvgImageElementEx
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.svgToString.SvgToString
import org.jetbrains.skiko.*


object MonolithicSkia {
    fun buildPlotFromRawSpecs(
        plotSpec: MutableMap<String, Any>,
        plotSize: DoubleVector?,
        plotMaxWidth: Double?,
        svgComponentFactory: (svg: SvgSvgElement) -> SkiaWidget,
        executor: (() -> Unit) -> Unit,
        computationMessagesHandler: ((List<String>) -> Unit)
    ): SkiaWidget {
        return try {
            @Suppress("NAME_SHADOWING")
            val plotSpec = processSpecs(plotSpec, frontendOnly = false)
            buildPlotFromProcessedSpecs(
                plotSpec,
                plotSize,
                plotMaxWidth,
                svgComponentFactory,
                executor,
                computationMessagesHandler
            )
        } catch (e: RuntimeException) {
            handleException(e)
        }
    }

    @Suppress("SameParameterValue")
    private fun processSpecs(plotSpec: MutableMap<String, Any>, frontendOnly: Boolean): MutableMap<String, Any> {
        PlotConfig.assertPlotSpecOrErrorMessage(plotSpec)
        if (PlotConfig.isFailure(plotSpec)) {
            return plotSpec
        }

        // Backend transforms
        @Suppress("NAME_SHADOWING")
        val plotSpec =
            if (frontendOnly) {
                plotSpec
            } else {
                // This transform doesn't need to be "portable"
                // Could use PlotConfigServerSideJvm in case we needed "encoding"
                BackendSpecTransformUtil.processTransform(plotSpec)
            }

        if (PlotConfig.isFailure(plotSpec)) {
            return plotSpec
        }

        // Frontend transforms
        return PlotConfigClientSide.processTransform(plotSpec)
    }

    fun buildPlotFromProcessedSpecs(
        plotSpec: MutableMap<String, Any>,
        plotSize: DoubleVector?,
        plotMaxWidth: Double?,
        svgComponentFactory: (svg: SvgSvgElement) -> SkiaWidget,
        executor: (() -> Unit) -> Unit,
        computationMessagesHandler: ((List<String>) -> Unit)
    ): SkiaWidget {
        return try {
            val buildResult = MonolithicCommon.buildPlotsFromProcessedSpecs(
                plotSpec,
                plotSize,
                plotMaxWidth,
                plotPreferredWidth = null
            )
            if (buildResult.isError) {
                val errorMessage = (buildResult as MonolithicCommon.PlotsBuildResult.Error).error
                return createErrorLabel(errorMessage)
            }

            val success = buildResult as MonolithicCommon.PlotsBuildResult.Success
            val computationMessages = success.buildInfos.flatMap { it.computationMessages }
            computationMessagesHandler(computationMessages)
            if (success.buildInfos.size == 1) {
                // a single plot
                return buildPlotComponent(
                    success.buildInfos[0],
                    svgComponentFactory, executor
                )
            }
            // ggbunch
            return buildGGBunchComponent(
                success.buildInfos,
                svgComponentFactory, executor
            )

        } catch (e: RuntimeException) {
            handleException(e)
        }
    }

    private fun buildPlotComponent(
        plotBuildInfo: MonolithicCommon.PlotBuildInfo,
        svgComponentFactory: (svg: SvgSvgElement) -> SkiaWidget,
        executor: (() -> Unit) -> Unit
    ): SkiaWidget {
        val assembler = plotBuildInfo.plotAssembler

        if (assembler.containsLiveMap) {
            error("livemap is not supported")
            //return AwtLiveMapFactoryUtil.buildLiveMapComponent(
            //    assembler,
            //    plotBuildInfo.processedPlotSpec,
            //    plotBuildInfo.size,
            //    svgComponentFactory,
            //    executor
            //)
        }

        val plot = assembler.createPlot()
        val plotContainer = PlotContainer(plot, plotBuildInfo.size)

        return buildPlotComponent(plotContainer, svgComponentFactory, executor)
    }

    fun buildPlotComponent(
        plotContainer: PlotContainer,
        svgComponentFactory: (svg: SvgSvgElement) -> SkiaWidget,
        executor: (() -> Unit) -> Unit
    ): SkiaWidget {
        plotContainer.ensureContentBuilt()
        val svg = plotContainer.svg
        val plotComponent: SkiaWidget = svgComponentFactory(svg)

        plotComponent.setMouseEventListener { s, e ->
            executor {
                plotContainer.mouseEventPeer.dispatch(s, e)
            }
        }

        return plotComponent
    }

    private fun buildGGBunchComponent(
        plotInfos: List<MonolithicCommon.PlotBuildInfo>,
        svgComponentFactory: (svg: SvgSvgElement) -> SkiaWidget,
        executor: (() -> Unit) -> Unit
    ): SkiaWidget {
/*
        val bunchComponent = DisposableJPanel(null)

        bunchComponent.border = null
//        bunchComponent.background = Colors.parseColor(Defaults.BACKDROP_COLOR).let {
//            Color(
//                it.red,
//                it.green,
//                it.blue,
//                it.alpha
//            )
//        }
        bunchComponent.isOpaque = false

        for (plotInfo in plotInfos) {
            val plotComponent = buildPlotComponent(
                plotInfo,
                svgComponentFactory, executor
            )
            val bounds = plotInfo.bounds()
            plotComponent.bounds = Rectangle(
                bounds.origin.x.toInt(),
                bounds.origin.y.toInt(),
                bounds.dimension.x.toInt(),
                bounds.dimension.y.toInt()
            )
            bunchComponent.add(plotComponent)
        }

        val bunchBounds = plotInfos.map { it.bounds() }
            .fold(DoubleRectangle(DoubleVector.ZERO, DoubleVector.ZERO)) { acc, bounds ->
                acc.union(bounds)
            }

        val bunchDimensions = Dimension(
            bunchBounds.width.toInt(),
            bunchBounds.height.toInt()
        )

        bunchComponent.preferredSize = bunchDimensions
        bunchComponent.minimumSize = bunchDimensions
        bunchComponent.maximumSize = bunchDimensions
        return bunchComponent

 */
        error("ggbunch is not supported")
    }


    private fun handleException(e: RuntimeException): SkiaWidget {
        //val failureInfo = FailureHandler.failureInfo(e)
        //if (failureInfo.isInternalError) {
        //    LOG.error(e) {}
        //}
        //return createErrorLabel(failureInfo.message)
        e.printStackTrace()
        error("handleException(e)")
    }

    private fun createErrorLabel(s: String): SkiaWidget {
        //val label = JLabel(s)
        //label.foreground = Color.RED
        //return label
        error("createErrorLabel()")
    }
}

fun SvgSvgElement.asString(): String {
    return SvgToString(object : SvgImageElementEx.RGBEncoder {
        override fun toDataUrl(width: Int, height: Int, argbValues: IntArray) = ""
    }).render(this)
}