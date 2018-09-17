package masegi.sho.classtable.presentation.customview

import android.app.Dialog
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ViewNumberPickerBinding
import masegi.sho.classtable.utli.ext.setVisible

/**
 * Created by masegi on 2018/03/30.
 */

class NumberPickerDialog : androidx.fragment.app.DialogFragment() {


    // MARK: - Builder

    internal class Builder(val context: Context, @StyleRes private val themeResId: Int) {

        constructor(context: Context): this(context, 0)


        // MARK: - Property

        internal var mIsHiddenTimesLabel: Boolean = false
        internal var mTitle: String = ""
        internal var mMinValue: Int = 0
        internal var mMaxValue: Int = 0
        internal var mDefaultValue: Int = 0
        internal var mCallback: ((Int) -> Unit)? = null


        // MARK: - Internal

        internal fun create(): Dialog {

            val builder = AlertDialog.Builder(context, themeResId)
            val inflater = LayoutInflater.from(context)
            val binding: ViewNumberPickerBinding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.view_number_picker,
                    null,
                    false
            )
            binding.numberPicker.run {

                minValue = mMinValue
                maxValue = mMaxValue
                value = mDefaultValue
            }
            binding.timesLabel.setVisible(!mIsHiddenTimesLabel)
            builder.setTitle(mTitle)
                    .setView(binding.root)
                    .setPositiveButton(android.R.string.ok) { _, _ ->

                        mCallback?.invoke(binding.numberPicker.value)
                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ -> }
            return builder.create()
        }
    }
}