package masegi.sho.classtable.presentation.views.edittime


import android.annotation.SuppressLint
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import masegi.sho.classtable.R

import masegi.sho.classtable.kotlin.data.model.Time

@SuppressLint("ValidFragment")
class TimePickerDialogFragment private constructor() : androidx.fragment.app.DialogFragment() {


    // MARK: - Property

    private var onSetStartTime: (() -> Unit)? = null
    private var onSetEndTime: (() -> Unit)? = null
    private var onSetTime: ((Time) -> Unit)? = null
    private lateinit var time: Time


    // MARK: - Builder

    class Builder(time: Time) { // TODO: 出来れば引数をなくしたい


        // MARK: - Property

        private val dialog = TimePickerDialogFragment()


        // MARK: - Initializer

        init { dialog.time = time }


        // MARK: - Internal

        internal fun setOnSetStartTimeListener(callback: (() -> Unit)?): Builder {

            dialog.onSetStartTime = callback
            return this
        }

        internal fun setOnSetEndTimeListener(callback: (() -> Unit)?): Builder {

            dialog.onSetEndTime = callback
            return this
        }

        internal fun setOnSetTimeListener(callback: ((Time) -> Unit)?): Builder {

            dialog.onSetTime = callback
            return this
        }

        internal fun show(fragmentManager: androidx.fragment.app.FragmentManager) {

            dialog.show(fragmentManager, "TimePickerDialog")
        }
    }

    // MARK: - DialogFragment

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var _onSetStartTime: (() -> Unit)? = null
        var _onSetEndTime: (() -> Unit)? = null
        val startListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

            time.startHour = hourOfDay
            time.startMin = minute
            onSetStartTime?.invoke()
            _onSetStartTime?.invoke()
        }
        val endListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

            time.endHour = hourOfDay
            time.endMin = minute
            onSetEndTime?.invoke()
            onSetTime?.invoke(time)
            _onSetEndTime?.invoke()
        }
        val startTimePickerDialog = TimePickerDialog(
                context,
                R.style.TimePickerDialog,
                startListener,
                time.startHour,
                time.startMin,
                true
        )
        startTimePickerDialog.setTitle("Start")
        val endTimePickerDialog = TimePickerDialog(
                context,
                R.style.TimePickerDialog,
                endListener,
                time.endHour,
                time.endMin,
                true
        )
        endTimePickerDialog.setTitle("End")
        _onSetStartTime = {

            startTimePickerDialog.hide()
            endTimePickerDialog.show()
        }
        _onSetEndTime = {

            endTimePickerDialog.hide()
            startTimePickerDialog.dismiss()
            endTimePickerDialog.dismiss()
        }
        return startTimePickerDialog
    }
}// Required empty public constructor
