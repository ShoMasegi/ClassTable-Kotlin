package masegi.sho.classtable.presentation.views

import android.content.Context
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * Created by masegi on 2018/04/15.
 */

class ContentWithBottomNavigationBehavior(
        context: Context,
        attrs: AttributeSet
) : androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior<ViewGroup>(context, attrs) {


    private var previousHeight: Float = 0F

    override fun onMeasureChild(parent: androidx.coordinatorlayout.widget.CoordinatorLayout,
                                child: ViewGroup,
                                parentWidthMeasureSpec: Int,
                                widthUsed: Int,
                                parentHeightMeasureSpec: Int,
                                heightUsed: Int): Boolean {

        val bnv: com.google.android.material.bottomnavigation.BottomNavigationView = parent.getDependencies(child)
                .firstOrNull { it is com.google.android.material.bottomnavigation.BottomNavigationView } as? com.google.android.material.bottomnavigation.BottomNavigationView
                ?: return super.onMeasureChild(
                        parent,
                        child,
                        parentWidthMeasureSpec,
                        widthUsed,
                        parentHeightMeasureSpec,
                        heightUsed
                )
        val parentMeasureSpecHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec)
        val availableHeight = if (parentMeasureSpecHeight == 0) parent.height else parentMeasureSpecHeight
        val bnvHeight = if (bnv.translationY == 0F) bnv.height else 0
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(availableHeight - bnvHeight, View.MeasureSpec.EXACTLY)

        parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed)
        return true
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: ViewGroup, dependency: View): Boolean {

        return dependency is com.google.android.material.bottomnavigation.BottomNavigationView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: ViewGroup, dependency: View): Boolean {

        if (dependency is com.google.android.material.bottomnavigation.BottomNavigationView) {

            val height = dependency.height - dependency.translationY
            if (previousHeight != height) {

                child.requestLayout()
                previousHeight = height
            }
            return true
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}