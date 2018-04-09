package masegi.sho.classtable.utli

import masegi.sho.classtable.util.ext.ordinal
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by masegi on 2018/03/10.
 */

class CalendarUtil {

    companion object {

        private const val DATABASE_CALENDAR_FORMAT: String = "yyyyMMddHHmmss"
        private const val DUE_DATE_FORMAT: String = "EEE, MMM dd, yyyy"
        private const val CREATE_DATE_FORMAT: String = "EEE,dd MMM"
        private const val SIMPLE_DATE_FORMAT: String = " MMM"
        private const val SIMPLE_WEEK_FORMAR: String = "EEEE "
        private const val SIMPLE_TIME_FORMAT: String = "HH:mm"


        @JvmStatic
        internal fun calendarToString(calendar: Calendar): String =
                SimpleDateFormat(DATABASE_CALENDAR_FORMAT, Locale("en"))
                        .format(calendar.time)

        @JvmStatic
        internal fun stringToCalendar(dateString: String): Calendar {

            val en = Locale("en")
            val dueDateSdf = SimpleDateFormat(DATABASE_CALENDAR_FORMAT, en)
            val calendar = Calendar.getInstance().apply { clear() }
            var date = calendar.time
            try {

                date = dueDateSdf.parse(dateString)
            }
            catch (e: ParseException) {

                e.printStackTrace()
            }
            calendar.time = date
            return calendar
        }

        @JvmStatic
        internal fun calendarToDueDate(dueDate: Calendar): String =
                SimpleDateFormat(DUE_DATE_FORMAT, Locale("en"))
                        .format(dueDate.time)

        @JvmStatic
        internal fun calendarToCreateDate(createDate: Calendar): String =
                SimpleDateFormat(CREATE_DATE_FORMAT, Locale("en"))
                        .format(createDate.time)

        @JvmStatic
        internal fun calendarToSimpleDate(calendar: Calendar): String {

            val sdf = SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale("en"))
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            return dayOfMonth.ordinal + sdf.format(calendar.time)
        }

        @JvmStatic
        internal fun calendarToTodoDate(calendar: Calendar): String {

            val sdf = SimpleDateFormat(SIMPLE_WEEK_FORMAR, Locale("en"))
            return sdf.format(calendar.time) + calendarToSimpleDate(calendar)
        }

        @JvmStatic
        internal fun calendarToSimpleTime(calendar: Calendar): String =
                SimpleDateFormat(SIMPLE_TIME_FORMAT).format(calendar.time)
    }
}