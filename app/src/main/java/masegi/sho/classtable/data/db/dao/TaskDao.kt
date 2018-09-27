package masegi.sho.classtable.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.annotation.CheckResult
import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.kotlin.data.model.Task

/**
 * Created by masegi on 2018/03/09.
 */

@Dao
abstract class TaskDao {

    @CheckResult
    @Query("SELECT * FROM task")
    abstract fun getAllTask(): Flowable<List<Task>>

    @CheckResult
    @Query("SELECT * FROM task WHERE tid = :tid")
    abstract fun getAllTask(tid: Int): Flowable<List<Task>>

    @CheckResult
    @Query("SELECT * FROM task WHERE tid = :tid AND id = :id")
    abstract fun getTask(tid: Int, id: Int): Maybe<Task>

    @CheckResult
    @Query("SELECT * FROM task WHERE tid = :tid AND lid = :lid")
    abstract fun getTasks(tid: Int, lid: Int): Maybe<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(tasks: List<Task>)

    @Query("DELETE FROM task")
    abstract fun deleteAll()

    @Query("DELETE FROM task WHERE tid = :tid")
    abstract fun deleteAll(tid: Int)

    @Query("DELETE FROM task WHERE tid = :tid AND id = :id")
    abstract fun delete(tid: Int, id: Int)
}
