package masegi.sho.classtable.presentation.common.binding

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.databinding.InverseBindingListener
import android.support.v7.widget.AppCompatSpinner
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

/**
 * Created by masegi on 2018/03/21.
 */

@BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
fun bindSpinnerData(
        spinner: AppCompatSpinner,
        newSelectedValue: String?,
        newTextAttrChanged: InverseBindingListener?
)
{

    spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) { }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            newTextAttrChanged?.onChange()
        }
    }
    if (newSelectedValue != null && spinner.adapter != null) {

        val position: Int = (spinner.adapter as ArrayAdapter<String>).getPosition(newSelectedValue)
        spinner.setSelection(position, true)
    }
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun captureSelectedValue(spinner: AppCompatSpinner): String = spinner.selectedItem as String


@BindingAdapter(value = ["selectedIntValue", "selectedIntValueAttrChanged"], requireAll = false)
fun bindSpinnerIntData(
        spinner: AppCompatSpinner,
        newSelectedValue: Int?,
        newTextAttrChanged: InverseBindingListener?
)
{

    spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) { }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            newTextAttrChanged?.onChange()
        }
    }
    if (newSelectedValue != null && spinner.adapter != null) {

        val position: Int = (spinner.adapter as ArrayAdapter<Int>).getPosition(newSelectedValue)
        spinner.setSelection(position, true)
    }
}

@InverseBindingAdapter(attribute = "selectedIntValue", event = "selectedIntValueAttrChanged")
fun captureSelectedIntValue(spinner: AppCompatSpinner): Int = spinner.selectedItem as Int
