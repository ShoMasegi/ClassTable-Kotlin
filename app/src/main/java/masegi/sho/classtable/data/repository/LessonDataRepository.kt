package masegi.sho.classtable.data.repository

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.data.db.LessonDatabase
import masegi.sho.classtable.data.db.RestoreDatabase
import masegi.sho.classtable.data.model.Memo
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.kotlin.data.model.Task
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/17.
 */

class LessonDataRepository @Inject constructor(
        private val lessonDatabase: LessonDatabase,
        private val restoreDatabase: RestoreDatabase
) : LessonRepository {


    override val lessons: Flowable<List<Lesson>> = lessonDatabase.getAllLessons()
    override val memos: Flowable<List<Memo>> = restoreDatabase.getAllMemo()
    override val tasks: Flowable<List<Task>> = restoreDatabase.getAllTask()

    override fun getLesson(week: DayOfWeek, start: Int): Maybe<Lesson> =
            lessonDatabase.getLesson(Prefs.tid, week, start)

    override fun getLessons(week: DayOfWeek, start: Int, end: Int): Maybe<List<Lesson>> =
            lessonDatabase.getLessons(Prefs.tid, week, start, end)

    override fun insert(lesson: Lesson) = lessonDatabase.insert(lesson)

    override fun delete(lesson: Lesson) {

        lessonDatabase.delete(lesson)
        restoreDatabase.deleteTask(lesson)
        restoreDatabase.deleteMemo(lesson)
    }

    override fun deleteAllLesson(tid: Int) = lessonDatabase.deleteAll(tid)

    override fun getTasks(lesson: Lesson) = restoreDatabase.getTasks(lesson)

    override fun save(task: Task) = restoreDatabase.insertTask(task)

    override fun delete(task: Task) = restoreDatabase.deleteTask(task.tid, task.id)

    override fun deleteAllTask(tid: Int) = restoreDatabase.deleteAllTask(tid)

    override fun getMemo(lesson: Lesson): Maybe<Memo> = restoreDatabase.getMemo(lesson)

    override fun save(memo: Memo) = restoreDatabase.insertMemo(memo)

    override fun deleteAllMemo(tid: Int) = restoreDatabase.deleteAllMemo(tid)

    override fun delete(tid: Int) {

        deleteAllLesson(tid)
        deleteAllTask(tid)
        deleteAllMemo(tid)
    }
}