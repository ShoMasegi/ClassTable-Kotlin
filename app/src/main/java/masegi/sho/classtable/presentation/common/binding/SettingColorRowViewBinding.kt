package masegi.sho.classtable.presentation.common.binding

import android.databinding.BindingAdapter
import masegi.sho.classtable.kotlin.data.model.ThemeColor
import masegi.sho.classtable.presentation.customview.SettingColorRowView

/**
 * Created by masegi on 2018/04/07.
 */

@BindingAdapter("themeColor")
fun SettingColorRowView.bindThemeColor(
        newThemeColor: ThemeColor?
)
{

    val themeColor = newThemeColor ?: ThemeColor.DEFAULT
    theme = themeColor
}