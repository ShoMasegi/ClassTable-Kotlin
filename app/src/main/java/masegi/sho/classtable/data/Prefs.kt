package masegi.sho.classtable.data

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.enumpref.enumValuePref
import masegi.sho.classtable.R
import masegi.sho.classtable.data.model.AttendMode.*
import masegi.sho.classtable.data.model.PrefEntity
import masegi.sho.classtable.kotlin.data.model.DayOfWeek

/**
 * Created by masegi on 2018/03/06.
 */

internal object Prefs : KotprefModel() {


    // MARK: - Property

    var tid by intPref(key = R.string.pref_key_tid)
    var name by stringPref(key = R.string.pref_key_name)
    var dayLessonCount by intPref(default = 5, key = R.string.pref_key_lesson_count)
    var shouldNotify by booleanPref(key = R.string.pref_key_notify)
    var attendManage by booleanPref(key = R.string.pref_key_manage)
    var attendMode by enumValuePref(default = NOTIFICATION, key = R.string.pref_key_mode)
    private var weekString by stringPref(default = "0111110", key = R.string.pref_key_week)
    var weeks: List<DayOfWeek>
        set(value) {

            var result: String = ""
            DayOfWeek.values().forEach {

                result += if (value.contains(it)) '1' else '0'
            }
            weekString = result
        }
        get() {

            val string: String = weekString
            val result: MutableList<DayOfWeek> = mutableListOf()
            string.forEachIndexed { i, char -> if (char == '1') result.add(DayOfWeek.getValue(i)) }
            return result
        }


    // MARK: - Internal

    internal fun sync(pref: PrefEntity) {

        tid = pref.tid
        name = pref.name
        weeks = pref.weeks
        dayLessonCount = pref.dayLessonCount
        shouldNotify = pref.shouldNotify
        attendManage = pref.attendManage
        attendMode = pref.attendMode
    }
}
