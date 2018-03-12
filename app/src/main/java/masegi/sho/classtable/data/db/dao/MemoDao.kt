package masegi.sho.classtable.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.support.annotation.CheckResult
import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.model.Memo

/**
 * Created by masegi on 2018/03/10.
 */

@Dao
abstract class MemoDao {


    @CheckResult
    @Query("SELECT * FROM memo")
    abstract fun getAllMemo(): Flowable<List<Memo>>

    @CheckResult
    @Query("SELECT * FROM memo WHERE tid = :tid")
    abstract fun getAllMemo(tid: Int): Flowable<List<Memo>>

    @CheckResult
    @Query("SELECT * FROM memo WHERE tid = :tid AND lid = :lid")
    abstract fun getMemo(tid: Int, lid: Int): Maybe<Memo>

    @Query("DELETE FROM memo")
    abstract fun deleteAll()

    @Query("DELETE FROM memo WHERE tid = :tid")
    abstract fun deleteAll(tid: Int)

    @Query("DELETE FROM memo WHERE tid = :tid AND lid = :lid")
    abstract fun delete(tid: Int, lid: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(memo: Memo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(memo: List<Memo>)
}
