package masegi.sho.classtable.kotlin.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import masegi.sho.classtable.utli.CalendarUtil
import org.parceler.Parcel
import java.util.*

/**
 * Created by masegi on 2018/03/04.
 */

@Parcel
@Entity(tableName = "task")
data class Task(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        var tid: Int = 0,
        var lid: Int = 0,
        var name: String = "",
        var content: String = "",
        var createdAt: Calendar = Calendar.getInstance(),
        var dueAt: Calendar = Calendar.getInstance(),
        var theme: ThemeColor = ThemeColor.DEFAULT,
        var isCompleted: Boolean = false
)
{

    // MARK: - Property

    val createdAtString: String
        get() = CalendarUtil.calendarToCreateDate(createdAt)

    val dueAtString: String
        get() = CalendarUtil.calendarToDueDate(dueAt)
}
