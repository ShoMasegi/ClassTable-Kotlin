package masegi.sho.classtable.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.support.annotation.CheckResult
import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.model.Pref

/**
 * Created by masegi on 2018/03/10.
 */

@Dao
abstract class PrefDao {

    @CheckResult
    @Query("SELECT * FROM pref")
    abstract fun getAll(): Flowable<List<Pref>>

    @CheckResult
    @Query("SELECT * FROM pref WHERE tid = :tid")
    abstract fun get(tid: Int): Maybe<Pref>

    @Query("DELETE FROM pref")
    abstract fun deleteAll()

    @Query("DELETE FROM pref WHERE tid = :tid")
    abstract fun delete(tid: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(pref: Pref)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(prefs: List<Pref>)
}