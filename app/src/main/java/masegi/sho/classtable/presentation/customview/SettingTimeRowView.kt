package masegi.sho.classtable.presentation.customview

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.TimePicker
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ViewSettingTimeBinding
import masegi.sho.classtable.utli.CalendarUtil
import java.util.*

/**
 * Created by masegi on 2018/04/08.
 */


class SettingTimeRowView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    // MARK: - Property

    internal var dueDate: Calendar
        get() = Calendar.getInstance().apply {

            set(Calendar.YEAR, mDate.get(Calendar.YEAR))
            set(Calendar.MONTH, mDate.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, mDate.get(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, mTime.get(Calendar.HOUR_OF_DAY))
            set(Calendar.MINUTE, mTime.get(Calendar.MINUTE))
        }
        set(value) {

            val calendar = Calendar.getInstance()
            mDate = calendar.apply {

                set(Calendar.YEAR, value.get(Calendar.YEAR))
                set(Calendar.MONTH, value.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, value.get(Calendar.DAY_OF_MONTH))
            }
            mTime = calendar.apply {

                set(Calendar.HOUR_OF_DAY, value.get(Calendar.HOUR_OF_DAY))
                set(Calendar.MINUTE, value.get(Calendar.MINUTE))
            }
        }

    internal fun setOnDateTimeChangedListener(
            onDateChanged: ((DatePicker, year: Int, month: Int, day: Int) -> Unit)?,
            onTimeChanged: ((TimePicker, hourOfDay: Int, minute: Int) -> Unit)?
    ) {

        onDateChangedStub = onDateChanged
        onTimeChangedStub = onTimeChanged
    }

    private val binding: ViewSettingTimeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_setting_time,
            this,
            true
    )

    private var mDate: Calendar = Calendar.getInstance()
        set(value) {

            field = value
            binding.dateView.text = CalendarUtil.calendarToDueDate(value)
        }
    private var mTime: Calendar = Calendar.getInstance()
        set(value) {

            field = value
            binding.timeView.text = CalendarUtil.calendarToSimpleTime(value)
        }
    private val now: Calendar = Calendar.getInstance().apply {

        set(Calendar.HOUR_OF_DAY, get(Calendar.HOUR) + 1)
        set(Calendar.MINUTE, 0)
    }

    private var onDateChangedStub: ((DatePicker, year: Int, month: Int, day: Int) -> Unit)? = null
    private var onTimeChangedStub: ((TimePicker, hourOfDay: Int, minute: Int) -> Unit)? = null

    // Do not implement these parameters. These parameters are used by data binding
    internal var _onDateChangedStub: (() -> Unit)? = null
    internal var _onTimeChangedStub: (() -> Unit)? = null


    // MARK: - View

    constructor(context: Context): this(context, null)

    init {

        binding.root.setOnClickListener { showDatePickerDialog() }
        binding.timeView.setOnClickListener { showTimePickerDialog() }
    }


    // MARK: - Private

    private fun showDatePickerDialog() = DatePickerDialog(
            context,
            { view: DatePicker, year: Int, month: Int, day: Int ->

                val calendar = Calendar.getInstance().apply { set(year, month, day) }
                mDate = calendar
                onDateChangedStub?.invoke(view, year, month, day)
                _onDateChangedStub?.invoke()
            },
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
    ).show()

    private fun showTimePickerDialog() = TimePickerDialog(
            context,
            { view: TimePicker, hour: Int, minute: Int ->

                val calendar = Calendar.getInstance().apply {

                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                }
                mTime = calendar
                onTimeChangedStub?.invoke(view, hour, minute)
                _onTimeChangedStub?.invoke()
            },
            now.get(Calendar.HOUR_OF_DAY),
            now.get(Calendar.MINUTE),
            true
    ).show()
}
