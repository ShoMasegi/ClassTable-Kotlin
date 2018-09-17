package masegi.sho.classtable.kotlin.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import masegi.sho.classtable.util.ext.ordinal
import masegi.sho.classtable.utli.CalendarUtil
import java.util.*

/**
 * Created by masegi on 2018/03/04.
 */

@Entity(tableName = "time")
data class Time(
        var tid: Int = 0,
        @PrimaryKey var periodNum: Int = 0,
        var startHour: Int = 0,
        var startMin: Int = 0,
        var endHour: Int = 0,
        var endMin: Int = 0
)
{


    // MARK: - Property

    @Ignore
    var startTime: String = ""
        private set
        get() {

            val calendar = Calendar.getInstance().apply {

                set(Calendar.HOUR_OF_DAY, startHour)
                set(Calendar.MINUTE, startMin)
            }
            return CalendarUtil.calendarToSimpleTime(calendar)
        }

    @Ignore
    var endTime: String = ""
        private set
        get() {

            val calendar = Calendar.getInstance().apply {

                set(Calendar.HOUR_OF_DAY, endHour)
                set(Calendar.MINUTE, endMin)
            }
            return CalendarUtil.calendarToSimpleTime(calendar)
        }

    @Ignore
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