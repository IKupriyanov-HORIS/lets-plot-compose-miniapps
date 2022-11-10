package jetbrains.datalore.vis.svgMapper.skia

import jetbrains.datalore.vis.swing.ApplicationContext
import javax.swing.SwingUtilities

class DefaultSwingContextSkia : ApplicationContext {
    override fun runWriteAction(action: Runnable) {
        action.run()
    }

    override fun invokeLater(action: Runnable, expared: () -> Boolean) {
        val exparableAction = Runnable {
            if (!expared()) {
                action.run()
            }
        }

        runInEdt(
            exparableAction,
            canRunImmediately = false
        )
    }

    companion object {
        val AWT_EDT_EXECUTOR = { action: () -> Unit ->
            runInEdt(
                action = { action() },
                canRunImmediately = true
            )
        }

        private fun runInEdt(action: Runnable, canRunImmediately: Boolean) {
            if (canRunImmediately && SwingUtilities.isEventDispatchThread()) {
                action.run()
            } else {
                SwingUtilities.invokeLater(action)
            }
        }
    }
}