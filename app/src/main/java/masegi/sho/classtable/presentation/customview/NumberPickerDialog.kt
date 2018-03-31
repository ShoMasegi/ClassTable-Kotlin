package masegi.sho.classtable.presentation.customview

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ViewNumberPickerBinding

/**
 * Created by masegi on 2018/03/30.
 */

class NumberPickerDialog : DialogFragment() {


    // MARK: - Builder

    internal class Builder(val context: Context) {


        // MARK: - Property

        internal var mTitle: String = ""
        internal var mMinValue: Int = 0
        internal var mMaxValue: Int = 0
        internal var mDefaultValue: Int = 0
        internal var mCallback: ((Int) -> Unit)? = null


        // MARK: - Internal

        internal fun create(): Dialog {

            val builder = AlertDialog.Builder(context)
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