package masegi.sho.classtable.data

import masegi.sho.classtable.data.model.AttendMode
import masegi.sho.classtable.data.model.AttendMode.*
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.DayOfWeek.*

/**
 * Created by masegi on 2018/03/06.
 */

internal object Prefs {


        // MARK: - Property

        var tid: Int = 0
        var name: String = ""
        var weeks: List<DayOfWeek> = listOf(MON, TUE, WED, THU, FRI)
        var dayLessonCount: Int = 5
        var shouldNotify: Boolean = false
        var attendManage: Boolean = false
        var attendMode: AttendMode = NOTIFICATION


        // MARK: - Internal

        internal fun sync() {

        }
}

