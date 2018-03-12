package masegi.sho.classtable.kotlin.data.model

import masegi.sho.classtable.util.ext.ordinal
import masegi.sho.classtable.utli.CalendarUtil
import java.util.*

/**
 * Created by masegi on 2018/03/04.
 */

data class Time(
        var periodNum: Int,
        var startHour: Int,
        var startMin: Int,
        var endHour: Int,
        var endMin: Int
)
{


    // MARK: - Property

    var startTime: String = ""
        private set
        get() {

            val calendar = Calendar.getInstance().apply {

                set(Calendar.HOUR_OF_DAY, startHour)
                set(Calendar.MINUTE, startMin)
            }
            return CalendarUtil.calendarToSimpleTime(calendar)
        }

    var endTime: String = ""
        private set
        get() {

            val calendar = Calendar.getInstance().apply {

                set(Calendar.HOUR_OF_DAY, endHour)
                set(Calendar.MINUTE, endMin)
            }
            return CalendarUtil.calendarToSimpleTime(calendar)
        }

    var period: String = ""
        private set
        get() = periodNum.ordinal + " Period"


    // MARK: - Constructor

    constructor(periodNum: Int, start: Calendar, end: Calendar) : this(
            periodNum,
            start.get(Calendar.HOUR_OF_DAY),
            start.get(Calendar.MINUTE),
            end.get(Calendar.HOUR_OF_DAY),
            end.get(Calendar.MINUTE)
    )

}