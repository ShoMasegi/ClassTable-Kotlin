package masegi.sho.classtable.data.db

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.db.dao.MemoDao
import masegi.sho.classtable.data.db.dao.TaskDao
import masegi.sho.classtable.data.model.Memo
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.kotlin.data.model.Task
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/10.
 */

class RestoreRoomDatabase @Inject constructor(
        private val taskDao: TaskDao,
        private val memoDao: MemoDao
) : RestoreDatabase {

    override fun getAllTask(): Flowable<List<Task>> = taskDao.getAllTask()

    override fun getAllTask(tid: Int): Flowable<List<Task>> = taskDao.getAllTask(tid)

    override fun getTasks(lesson: Lesson): Flowable<List<Task>> = taskDao.getTasks(lesson.tid, lesson.id)

    override fun getTask(tid: Int, id: Int): Maybe<Task> = taskDao.getTask(tid, id)

    override fun deleteAllTask() = taskDao.deleteAll()

    override fun deleteAllTask(tid: Int) = taskDao.deleteAll(tid)

    override fun deleteTask(lesson: Lesson) = taskDao.delete(lesson.tid, lesson.id)

    override fun deleteTask(tid: Int, id: Int) = taskDao.delete(tid, id)

    override fun insertTask(task: Task) = taskDao.insert(task)

    override fun insertTask(tasks: List<Task>) = taskDao.insert(tasks)

    override fun getAllMemo(): Flowable<List<Memo>> = memoDao.getAllMemo()

    override fun getAllMemo(tid: Int): Flowable<List<Memo>> = memoDao.getAllMemo(tid)

    override fun getMemo(lesson: Lesson): Maybe<Memo> = memoDao.getMemo(lesson.tid, lesson.id)

    override fun deleteAllMemo() = memoDao.deleteAll()

    override fun deleteAllMemo(tid: Int) = memoDao.deleteAll(tid)

    override fun deleteMemo(lesson: Lesson) = memoDao.delete(lesson.tid, lesson.id)

    override fun insertMemo(memo: Memo) = memoDao.insert(memo)

    override fun insertMemo(memo: List<Memo>) = memoDao.insert(memo)
}
