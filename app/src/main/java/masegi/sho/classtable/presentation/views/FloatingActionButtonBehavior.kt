package masegi.sho.classtable.presentation.views

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu

/**
 * Created by masegi on 2018/04/02.
 */

class FloatingActionButtonBehavior(
        context: Context,
        attributeSet: AttributeSet
) : androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior<View>(context, attributeSet) {


    // MARK: - Property

    private var mTranslationY: Float = 0F


    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {

        if ((child is FloatingActionButton || child is FloatingActionMenu)
                && dependency is com.google.android.material.snackbar.Snackbar.SnackbarLayout) {

            parent.let { updateTranslation(it, child, dependency) }
        }
        return false
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {

        return dependency is com.google.android.material.snackbar.Snackbar.SnackbarLayout
    }

    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View, dependency: View) {

        if ((child is FloatingActionButton || child is FloatingActionMenu)
        && dependency is com.google.android.material.snackbar.Snackbar.SnackbarLayout) {

            parent?.let { updateTranslation(it, child, dependency) }
        }
    }


    // MARK: - Private

    private fun updateTranslation(parent: CoordinatorLayout, child: View, dependency: View) {


        val translationY = getTranslationY(parent, child)
        if (translationY != mTranslationY) {

            ViewCompat.animate(child).cancel()
            if (Math.abs(translationY - mTranslationY) == dependency.height.toFloat()) {

                ViewCompat.animate(child)
                        .translationY(translationY)
                        .setListener(null)
            }
            else {

                child.translationY = translationY
            }
            mTranslationY = translationY
        }
    }

    private fun getTranslationY(parent: CoordinatorLayout, child: View): Float {

        var minOffset = 0F
        val dependencies = parent.getDependencies(child)
        dependencies.forEach {

            if (it is com.google.android.material.snackbar.Snackbar.SnackbarLayout && parent.doViewsOverlap(child, it)) {

                minOffset = Math.min(minOffset, it.translationY - it.height)
            }
        }
        return minOffset
    }
}