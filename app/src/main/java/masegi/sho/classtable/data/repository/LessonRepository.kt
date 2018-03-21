package masegi.sho.classtable.data.repository

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import masegi.sho.classtable.data.model.Memo
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.kotlin.data.model.Task

/**
 * Created by masegi on 2018/03/17.
 */

interface LessonRepository {

    val lessons: Flowable<List<Lesson>>
    val memos: Flowable<List<Memo>>
    val tasks: Flowable<List<Task>>

    fun getLesson(week: DayOfWeek, start: Int): Maybe<Lesson>
    fun save(lesson: Lesson)
    fun delete(lesson: Lesson)
    fun save(task: Task)
    fun delete(task: Task)
    fun save(memo: Memo)
}