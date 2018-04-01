package masegi.sho.classtable.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.DayOfWeek.*

/**
 * Created by masegi on 2018/03/10.
 */

@Entity(tableName = "pref")
data class Pref(
        @PrimaryKey
        var tid: Int = 0,
        var name: String = "",
        @Ignore
        private var _weeks: List<DayOfWeek> = mutableListOf(MON, TUE, WED, THU, FRI),
        var weekString: String = "0111110",
        var dayLessonCount: Int = 0,
        var shouldNotify: Boolean = false,
        var attendManage: Boolean = false,
        var attendMode: AttendMode = AttendMode.NOTIFICATION
)
{


    // MARK: - Property

    @Ignore
    var weeks: List<DayOfWeek> = _weeks
        set(value) {

            val result: String = ""
            DayOfWeek.values().forEach {

                result + if (value.contains(it)) '1' else '0'
            }
            weekString = result
            field = value
        }
        get() {

            var index: Int = 0
            val list: List<Boolean> = weekString.map { it == '1' }
            val result: MutableList<DayOfWeek> = mutableListOf()
            list.forEach {

                if (it) result.add(DayOfWeek.getValue(index++))
            }
            return result
        }
}