package masegi.sho.classtable.presentation.customview

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import android.util.AttributeSet
import android.view.LayoutInflater
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ViewSettingColorBinding
import masegi.sho.classtable.kotlin.data.model.ThemeColor
import masegi.sho.classtable.presentation.common.binding.bindCircleThemeColor
import masegi.sho.classtable.presentation.customview.ColorPickerDialog.ColorPickerDialog
import java.util.ArrayList

/**
 * Created by masegi on 2018/04/07.
 */


class SettingColorRowView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0
) : androidx.constraintlayout.widget.ConstraintLayout(context, attrs, defStyleAttr) {


    // MARK: - Property

    private val binding: ViewSettingColorBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_setting_color,
            this,
            true
    )

    internal var theme: ThemeColor = ThemeColor.BLUE
        set(value) {

            field = value
            binding.colorName.text = value.colorName
            binding.colorView.bindCircleThemeColor(value)
        }

   internal var onColorChangeListener: ((ThemeColor) -> Unit)? = null

    // Do not implement this parameter. This parameter is used by data binding
    internal var _onColorChangeListener: (() -> Unit)? = null


    // MARK: - View

    constructor(context: Context): this(context, null)

    init {

        binding.colorName.text = theme.colorName
        binding.colorView.bindCircleThemeColor(theme)
        binding.root.setOnClickListener {

            showColorPickerDialog()
        }
    }

    // MARK: - Internal

    internal fun setOnChangedListener(listener: (ThemeColor) -> Unit) {

        this.onColorChangeListener = listener
    }


    // MARK: - Private

    private fun showColorPickerDialog() {

        if (context is AppCompatActivity) {

            val dialog: ColorPickerDialog = ColorPickerDialog.newInstance(
                    ColorPickerDialog.SELECTION_SINGLE,
                    ThemeColor.primaryColorResIdList,
                    4,
                    ColorPickerDialog.SIZE_SMALL
            )
            dialog.setOnDialogButtonListener(object : ColorPickerDialog.OnDialogButtonListener {

                override fun onDonePressed(mSelectedColors: ArrayList<Int>) {

                    if (mSelectedColors.size > 0) {

                        val themeColor = ThemeColor.getByPrimaryColorResId(mSelectedColors[0])
                        theme = themeColor
                        onColorChangeListener?.invoke(themeColor)
                        _onColorChangeListener?.invoke()
                    }
                }

                override fun onDismiss() {}
            })
            val fragmentManager: androidx.fragment.app.FragmentManager = (context as AppCompatActivity).supportFragmentManager
            dialog.show(fragmentManager, "color_picker")
        }
    }
}
