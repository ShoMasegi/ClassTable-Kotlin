package masegi.sho.classtable.data.db

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson

/**
 * Created by masegi on 2018/03/08.
 */

interface LessonDatabase {
    
    fun getAllLessons(): Flowable<List<Lesson>>
    fun getLesson(tid: Int, id: Int): Maybe<Lesson>
    fun getLessons(tid: Int, week: DayOfWeek, start: Int, end: Int): Maybe<List<Lesson>>
    fun getLessons(tid: Int, name: String): Flowable<List<Lesson>>
    fun getLesson(tid: Int, week: DayOfWeek, start: Int): Maybe<Lesson>
    fun deleteAll()
    fun deleteAll(tid: Int)
    fun delete(lesson: Lesson)
    fun insert(lesson: Lesson)
}