package masegi.sho.classtable.kotlin.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable
import masegi.sho.classtable.util.ext.ordinal
import org.parceler.Parcel


/**
 * Created by masegi on 2018/03/04.
 */

@Parcel
@Entity(tableName = "lesson")
data class Lesson(
        @PrimaryKey(autoGenerate = true) var did: Int = 0,
        var id: Int = 0,
        var tid: Int = 0,
        var name: String,
        var start: Int = 1,
        var section: Int = 1,
        var week: DayOfWeek,
        @Embedded var room: Room = Room(),
        var teacher: String? = null,
        @Embedded var attendance: Attendance = Attendance(),
        var theme: ThemeColor = ThemeColor.DEFAULT
) : BaseObservable()
{

    private constructor() : this(name = "", week = DayOfWeek.SUN)

    var weekString: String
        set(value) { week = DayOfWeek.getValue(value) }
        get() = week.rawValue

    val startPeriod: String
        get() = start.ordinal
}