/*
 * Copyright (c) 2022. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package jetbrains.datalore.vis.svgMapper.skia.drawing

import jetbrains.datalore.base.observable.collections.CollectionItemEvent
import jetbrains.datalore.base.observable.collections.list.ObservableArrayList
import jetbrains.datalore.base.observable.event.EventHandler
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Drawable
import org.jetbrains.skia.Point
import org.jetbrains.skia.Rect
import kotlin.properties.Delegates.observable
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

typealias SkPath = org.jetbrains.skia.Path
abstract class Element: Drawable() {
    var styleClass: List<String>? by visualProp(null)
    var parent: Element? by visualProp(null)
    var transforms = ObservableArrayList<Transform>()
    var clipPath: SkPath? by visualProp(null)

    private val propertyDeps = mutableMapOf<KProperty<*>, MutableList<DependencyProperty<*>>>()

    init {
        transforms.addHandler(object : EventHandler<CollectionItemEvent<out Transform>> {
            override fun onEvent(event: CollectionItemEvent<out Transform>) {
                repaint()
            }
        })
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return
        canvas.save()
        applyTransforms(canvas)
        clipPath?.let(canvas::clipPath)
        doDraw(canvas)
        canvas.restore()
    }

    protected fun applyTransforms(canvas: Canvas) {
        transforms.forEach {
            it.apply(canvas)
        }
    }

    protected open fun doDraw(canvas: Canvas) {}

    protected fun repaint() {
        notifyDrawingChanged()
    }

    protected fun getTranslate(): Point {
        var p = parent
        var offsetX = 0.0f
        var offsetY = 0.0f

        //while (p != null) {
        //    offsetX += p.x
        //    offsetY += p.y
        //}

        return Point(offsetX, offsetY)
    }

    class DependencyProperty<T>(
        private val delegate: () -> T
    ) : ReadOnlyProperty<Any?, T> {
        private var isDirty: Boolean = false
        private var value: T = delegate()

        fun invalidate() {
            isDirty = true
        }

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            if (isDirty) {
                isDirty = false
                value = delegate()
            }
            return value
        }
    }

    protected fun <T> visualProp(initialValue: T): ReadWriteProperty<Any?, T> =
        observable(initialValue) { property, oldValue, newValue ->
            if (oldValue != newValue) {
                this.propertyDeps.getOrDefault(property, emptyList()).forEach(DependencyProperty<*>::invalidate)
                this.repaint()
            }
    }

    protected fun <T> dependencyProp(vararg deps: KProperty<*>, delegate: () -> T): DependencyProperty<T> =
        DependencyProperty<T>(delegate).also { prop ->
            deps.forEach {
                propertyDeps.getOrPut(it, ::mutableListOf).add(prop)
            }
        }

    override fun onGetBounds(): Rect {
        var root = parent
        while (root?.parent != null) {
            root = parent
        }
        return root?.onGetBounds() ?: Rect(0.0f, 0.0f, 0.0f, 0.0f)
    }
}
