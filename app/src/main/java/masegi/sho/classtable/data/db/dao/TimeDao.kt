package masegi.sho.classtable.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.annotation.CheckResult
import io.reactivex.Flowable
import masegi.sho.classtable.kotlin.data.model.Time

/**
 * Created by masegi on 2018/04/26.
 */

@Dao
abstract class TimeDao {

    @CheckResult
    @Query("SELECT * FROM time")
    abstract fun getAll(): Flowable<List<Time>>

    @Query("DELETE FROM time")
    abstract fun deleteAll()

    @Query("DELETE FROM time WHERE tid = :tid")
    abstract fun delete(tid: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(time: Time)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(prefs: List<Time>)
}
