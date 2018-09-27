package masegi.sho.classtable.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.annotation.CheckResult
import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.model.PrefEntity

/**
 * Created by masegi on 2018/03/10.
 */

@Dao
abstract class PrefDao {

    @CheckResult
    @Query("SELECT * FROM pref")
    abstract fun getAll(): Flowable<List<PrefEntity>>

    @CheckResult
    @Query("SELECT * FROM pref WHERE tid = :tid")
    abstract fun get(tid: Int): Maybe<PrefEntity>

    @Query("DELETE FROM pref")
    abstract fun deleteAll()

    @Query("DELETE FROM pref WHERE tid = :tid")
    abstract fun delete(tid: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(pref: PrefEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(prefs: List<PrefEntity>)
}