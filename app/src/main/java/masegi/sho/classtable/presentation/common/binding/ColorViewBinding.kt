package masegi.sho.classtable.presentation.common.binding

import android.databinding.BindingAdapter
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.view.View
import masegi.sho.classtable.kotlin.data.model.ThemeColor

/**
 * Created by masegi on 2018/03/29.
 */

@BindingAdapter("themeColor")
fun View.bindThemeColor(newThemeColor: ThemeColor?) {

    val theme = newThemeColor ?: ThemeColor.DEFAULT
    setBackgroundColor(ContextCompat.getColor(context, theme.primaryColorResId))
}

@BindingAdapter("squareTheme")
fun View.bindSquareThemeColor(newThemeColor: ThemeColor?) {

    val theme = newThemeColor ?: ThemeColor.DEFAULT
    val drawable =  GradientDrawable().apply {

        shape = GradientDrawable.RECTANGLE
        val scale = (resources.displayMetrics.density * 1.0).toInt()
        if (theme == ThemeColor.DEFAULT) {

            setStroke(scale, ContextCompat.getColor(context, theme.primaryColorDarkResId))
        }
        setColor(ContextCompat.getColor(context, theme.primaryColorResId))
    }
    background = drawable
}

@BindingAdapter("circleTheme")
fun View.bindCircleThemeColor(newThemeColor: ThemeColor?) {

    val theme = newThemeColor ?: ThemeColor.BLUE
    val colorRes = if (theme != ThemeColor.DEFAULT) theme.primaryColorResId else theme.primaryColorDarkResId
    val drawable = GradientDrawable().apply {

        shape = GradientDrawable.OVAL
        setColor(ContextCompat.getColor(context, colorRes))
    }
    background = drawable
}
