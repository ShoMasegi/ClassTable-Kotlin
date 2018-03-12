package masegi.sho.classtable.kotlin.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import masegi.sho.classtable.utli.CalendarUtil
import java.util.*

/**
 * Created by masegi on 2018/03/04.
 */

@Entity(tableName = "task")
data class Task(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val tid: Int,
        val lid: Int,
        var name: String,
        var content: String,
        val createdAt: Calendar = Calendar.getInstance(),
        var dueAt: Calendar,
        var theme: ThemeColor = ThemeColor.DEFAULT,
        var isCompleted: Boolean
)
{


    // MARK: - Property

    val createdAtString: String
        get() = CalendarUtil.calendarToCreateDate(createdAt)

    val dueAtString: String
        get() = CalendarUtil.calendarToDueDate(dueAt)
}
