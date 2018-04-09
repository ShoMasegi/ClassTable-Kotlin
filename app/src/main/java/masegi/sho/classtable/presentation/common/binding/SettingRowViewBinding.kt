package masegi.sho.classtable.presentation.common.binding

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.databinding.InverseBindingListener
import masegi.sho.classtable.kotlin.data.model.ThemeColor
import masegi.sho.classtable.presentation.customview.SettingColorRowView
import masegi.sho.classtable.presentation.customview.SettingTimeRowView
import java.util.*

/**
 * Created by masegi on 2018/04/07.
 */

@BindingAdapter(value = ["selectedTheme", "selectedThemeAttrChanged"], requireAll = false)
fun bindTheme(
        colorRowView: SettingColorRowView,
        newThemeColor: ThemeColor?,
        newThemeColorAttrChanged: InverseBindingListener?
)
{

    colorRowView._onColorChangeListener = { newThemeColorAttrChanged?.onChange() }
    newThemeColor?.let { colorRowView.theme = it }
}

@InverseBindingAdapter(attribute = "selectedTheme", event = "selectedThemeAttrChanged")
fun captureThemeChanged(colorRowView: SettingColorRowView): ThemeColor
        = colorRowView.theme


@BindingAdapter(value = ["selectedDate", "selectedDateAttrChanged"], requireAll = false)
fun bindDate(
        timeRowView: SettingTimeRowView,
        newCalendar: Calendar?,
        newCalendarAttrChanged: InverseBindingListener?
)
{

    timeRowView._onDateChangedStub = { newCalendarAttrChanged?.onChange() }
    timeRowView._onTimeChangedStub = { newCalendarAttrChanged?.onChange() }
    newCalendar?.let { timeRowView.dueDate = it }
}

@InverseBindingAdapter(attribute = "selectedDate", event = "selectedDateAttrChanged")
fun captureDateChanged(timeRowView: SettingTimeRowView): Calendar
    = timeRowView.dueDate
