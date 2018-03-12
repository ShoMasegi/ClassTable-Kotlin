package masegi.sho.classtable.data

import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson

/**
 * Created by masegi on 2018/03/06.
 */

data class LessonDataSource(
        var dataSource: List<Lesson>
)
{

    fun getLesson(dayOfWeek: DayOfWeek, start: Int): Lesson? {

        val lessons = dataSource.filter { it.week == dayOfWeek && it.start == start }
        return if (lessons.isNotEmpty()) lessons[0] else null
    }
}