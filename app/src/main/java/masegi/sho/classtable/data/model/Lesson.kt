package masegi.sho.classtable.kotlin.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/**
 * Created by masegi on 2018/03/04.
 */

@Entity(tableName = "lesson")
data class Lesson(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var tid: Int = 0,
        var name: String,
        var start: Int = 1,
        var section: Int = 1,
        var week: DayOfWeek,
        @Embedded var room: Room?,
        var teacher: String?,
        @Embedded var attendance: Attendance,
        var theme: ThemeColor = ThemeColor.DEFAULT
)