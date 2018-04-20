package masegi.sho.classtable.data.db

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.model.PrefEntity

/**
 * Created by masegi on 2018/03/10.
 */

interface PrefDatabase {

    fun getAll(): Flowable<List<PrefEntity>>
    fun get(tid: Int): Maybe<PrefEntity>
    fun deleteAll()
    fun delete(tid: Int)
    fun insert(pref: PrefEntity)
    fun insert(prefs: List<PrefEntity>)
}