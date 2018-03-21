package masegi.sho.classtable.data.db.dao

import android.arch.persistence.room.*
import android.support.annotation.CheckResult
import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.kotlin.data.model.Lesson

/**
 * Created by masegi on 2018/03/08.
 */

@Dao
abstract class LessonDao {

    @CheckResult
    @Query("SELECT * FROM lesson")
    abstract fun getAllLessons(): Flowable<List<Lesson>>

    @CheckResult
    @Query("SELECT * FROM lesson WHERE tid = :tid")
    abstract fun getAllLessons(tid: Int): Flowable<List<Lesson>>

    @CheckResult
    @Query("SELECT * FROM lesson WHERE tid = :tid AND id = :id")
    abstract fun getLesson(tid: Int, id: Int): Maybe<Lesson>

    @CheckResult
    @Query("SELECT * FROM lesson WHERE tid = :tid AND week = :weekString " +
            "AND (start BETWEEN :startFrom AND :startTo)")
    abstract fun getLessons(tid: Int, weekString: String, startFrom: Int, startTo: Int): Maybe<List<Lesson>>

    @CheckResult
    @Query("SELECT * FROM lesson WHERE tid = :tid AND name = :name")
    abstract fun getLessons(tid: Int, name: String): Flowable<List<Lesson>>

    @CheckResult
    @Query("SELECT * FROM lesson WHERE tid = :tid AND week = :weekString " +
            "AND start = :start")
    abstract fun getLesson(tid: Int, weekString: String, start: Int): Maybe<Lesson>

    @CheckResult
    @Query("SELECT * FROM lesson WHERE tid = :tid AND week = :weekString")
    abstract fun getWeekLessons(tid: Int, weekString: String): Flowable<List<Lesson>>

    @Query("DELETE FROM lesson")
    abstract fun deleteAll()

    @Query("DELETE FROM lesson WHERE tid = :tid")
    abstract fun deleteAll(tid: Int)

    @Query("DELETE FROM lesson WHERE tid = :tid AND id = :id")
    abstract fun delete(tid: Int, id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(lesson: Lesson)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(lessons: List<Lesson>)
}
