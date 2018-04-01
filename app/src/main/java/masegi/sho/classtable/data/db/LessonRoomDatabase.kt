package masegi.sho.classtable.data.db

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.db.dao.LessonDao
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.common.KotPrefs
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/08.
 */

class LessonRoomDatabase @Inject constructor(
        private val lessonDao: LessonDao
) : LessonDatabase {

    override fun getAllLessons(): Flowable<List<Lesson>> =
            lessonDao.getAllLessons()

    override fun getALlLessons(tid: Int): Flowable<List<Lesson>> =
            lessonDao.getAllLessons(tid)

    override fun getLesson(tid: Int, id: Int): Maybe<Lesson> =
            lessonDao.getLesson(tid, id)

    override fun getLessons(
            tid: Int,
            week: DayOfWeek,
            startFrom: Int,
            startTo: Int
    ): Maybe<List<Lesson>> = lessonDao.getLessons(tid, week.toString(), startFrom, startTo)



    override fun getLessons(tid: Int, name: String): Flowable<List<Lesson>> =
            lessonDao.getLessons(tid, name)

    override fun getLesson(tid: Int, week: DayOfWeek, start: Int): Maybe<Lesson> =
            lessonDao.getLesson(tid, week.toString(), start)

    override fun getWeekLessons(tid: Int, week: DayOfWeek): Flowable<List<Lesson>> =
            lessonDao.getLessons(tid, week.toString())

    override fun deleteAll() = lessonDao.deleteAll()

    override fun deleteAll(tid: Int) = lessonDao.deleteAll(tid)

    override fun delete(tid: Int, id: Int) = lessonDao.delete(tid, id)

    override fun insert(lesson: Lesson) {

        try {

            if (lesson.did != 0) {

                lessonDao.delete(lesson.tid, lesson.id)
                lesson.did = 0
            }
            if (lesson.id == 0) {

                lesson.id = KotPrefs.stamp++
            }
            for (i in 0 until lesson.section) {

                lessonDao.insert(lesson)
                lesson.start++
            }
        } catch (e: Exception) {

            Log.e("LessonRoomDatabase", e.message)
        }
    }
}