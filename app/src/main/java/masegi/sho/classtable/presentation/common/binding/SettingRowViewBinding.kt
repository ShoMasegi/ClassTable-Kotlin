package masegi.sho.classtable.presentation.common.binding

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import masegi.sho.classtable.kotlin.data.model.ThemeColor
import masegi.sho.classtable.presentation.customview.*
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


@BindingAdapter(value = ["text", "textAttrChanged"], requireAll = false)
fun bindText(
        editRowView: SettingEditRowView,
        newText: String?,
        newTextAttrChanged: InverseBindingListener?
)
{

    editRowView._afterTextChanged = { newTextAttrChanged?.onChange() }
    editRowView.text = newText
}

@InverseBindingAdapter(attribute = "text", event = "textAttrChanged")
fun captureTextChanged(editRowView: SettingEditRowView): String
    = editRowView.text ?: ""

@BindingAdapter(value = ["isChecked", "isCheckedAttrChanged"], requireAll = false)
fun bindIsChecked(
        switchRowView: SettingSwitchRowView,
        isChecked: Boolean,
        isCheckedAttrChanged: InverseBindingListener?
)
{

    switchRowView.isChecked = isChecked
    switchRowView._onCheckedChangedStub = { isCheckedAttrChanged?.onChange() }
}

@InverseBindingAdapter(attribute = "isChecked", event = "isCheckedAttrChanged")
fun captureIsChecked(switchRowView: SettingSwitchRowView): Boolean
        = switchRowView.isChecked

@BindingAdapter("settingEnabled")
fun SettingSwitchRowView.setSettingEnabled(boolean: Boolean) { this.isEnabled = boolean }

@BindingAdapter("settingDefaultValue")
fun SettingSwitchRowView.setSettingDefaultValue(defaultValue: Boolean) = this.setDefault(defaultValue)

@BindingAdapter("settingEnabled")
fun SettingSubTitleRow.setSettingEnabled(boolean: Boolean) { this.isEnabled = boolean }

@BindingAdapter("settingTitle")
fun SettingSubTitleRow.setSettingTitle(title: String) { this.title = title }

@BindingAdapter("settingSubTitle")
fun SettingSubTitleRow.setSettingSubTitle(subtitle: String) { this.subtitle = subtitle }
