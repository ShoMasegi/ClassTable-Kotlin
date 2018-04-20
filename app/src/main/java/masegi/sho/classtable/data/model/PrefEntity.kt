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
data class PrefEntity(
        @PrimaryKey
        var tid: Int = 1,
        var name: String = "Default Table",
        var weekString: String = "0111110",
        var dayLessonCount: Int = 5,
        var shouldNotify: Boolean = false,
        var attendManage: Boolean = false,
        var attendMode: AttendMode = AttendMode.NOTIFICATION
)
{


    // MARK: - Property

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
}