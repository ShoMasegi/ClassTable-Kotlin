package masegi.sho.classtable.data.db

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.model.Memo
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.kotlin.data.model.Task

/**
 * Created by masegi on 2018/03/10.
 */

interface RestoreDatabase {

    fun getAllTask(): Flowable<List<Task>>
    fun getAllTask(tid: Int): Flowable<List<Task>>
    fun getAllMemo(): Flowable<List<Memo>>
    fun getAllMemo(tid: Int): Flowable<List<Memo>>
    fun getTasks(lesson: Lesson): Flowable<List<Task>>
    fun getTask(tid: Int, id: Int): Maybe<Task>
    fun getMemo(lesson: Lesson): Maybe<Memo>
    fun deleteAllTask()
    fun deleteAllTask(tid: Int)
    fun deleteAllMemo()
    fun deleteAllMemo(tid: Int)
    fun deleteTask(lesson: Lesson)
    fun deleteTask(tid: Int, id: Int)
    fun deleteMemo(lesson: Lesson)
    fun insertTask(task: Task)
    fun insertTask(tasks: List<Task>)
    fun insertMemo(memo: Memo)
    fun insertMemo(memo: List<Memo>)
}