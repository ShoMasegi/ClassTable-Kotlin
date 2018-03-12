package masegi.sho.classtable.data.db.mapper

import android.arch.persistence.room.TypeConverter
import masegi.sho.classtable.data.model.AttendMode
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.ThemeColor
import masegi.sho.classtable.utli.CalendarUtil
import java.util.*


/**
 * Created by masegi on 2018/03/10.
 */

object Converters {

    @JvmStatic
    @TypeConverter
    fun fromCalendar(value: String?): Calendar? =
            if (value != null) CalendarUtil.stringToCalendar(value) else null

    @JvmStatic
    @TypeConverter
    fun calendarToString(calendar: Calendar?): String? =
            if (calendar != null) CalendarUtil.calendarToString(calendar) else null

    @JvmStatic
    @TypeConverter
    fun fromDayOfWeek(value: String?): DayOfWeek? =
            if (value != null) DayOfWeek.valueOf(value) else null

    @JvmStatic
    @TypeConverter
    fun dayOfWeekToString(week: DayOfWeek?): String? = week?.toString()

    @JvmStatic
    @TypeConverter
    fun fromThemeColor(value: Int?): ThemeColor? =
            if (value != null) ThemeColor.get(value) else null

    @JvmStatic
    @TypeConverter
    fun themeColorToInt(theme: ThemeColor?): Int? = theme?.id

    @JvmStatic
    @TypeConverter
    fun fromAttendMode(value: String?): AttendMode? =
            if (value != null) AttendMode.valueOf(value) else null

    @JvmStatic
    @TypeConverter
    fun attendModeToString(mode: AttendMode?): String? = mode?.toString()
}
