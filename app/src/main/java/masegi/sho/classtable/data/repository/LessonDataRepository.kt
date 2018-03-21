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

    override val lessons: Flowable<List<Lesson>> = lessonDatabase.getALlLessons(Prefs.tid)
    override val memos: Flowable<List<Memo>> = restoreDatabase.getAllMemo(Prefs.tid)
    override val tasks: Flowable<List<Task>> = restoreDatabase.getAllTask(Prefs.tid)


    override fun getLesson(week: DayOfWeek, start: Int): Maybe<Lesson> =
            lessonDatabase.getLesson(Prefs.tid, week, start)


    override fun save(lesson: Lesson) { // TODO: RETURN result: Single<Bool>

        lessonDatabase.getLessons(
                lesson.tid,
                lesson.week,
                lesson.start,
                lesson.start + lesson.section
        ).subscribeOn(Schedulers.io())
            .subscribeBy(
                    onSuccess = {

                        if (it.isNotEmpty()) {

                            it.forEach { if (it.id != lesson.id) { return@subscribeBy } }
                            this.insert(lesson)
                        }
                        else {

                            this.insert(lesson)
                        }
                    },
                    onError = { },
                    onComplete = { this.insert(lesson) }
            )
    }

    override fun delete(lesson: Lesson) = lessonDatabase.delete(lesson.tid, lesson.id)

    override fun save(task: Task) = restoreDatabase.insertTask(task)

    override fun delete(task: Task) = restoreDatabase.deleteTask(task.tid, task.id)

    override fun save(memo: Memo) = restoreDatabase.insertMemo(memo)

    private fun insert(lesson: Lesson) = lessonDatabase.insert(lesson)
}