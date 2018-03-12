package masegi.sho.classtable.data.db

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.model.Pref

/**
 * Created by masegi on 2018/03/10.
 */

interface PrefDatabase {

    fun getAll(): Flowable<List<Pref>>
    fun get(tid: Int): Maybe<Pref>
    fun deleteAll()
    fun delete(tid: Int)
    fun insert(pref: Pref)
    fun insert(prefs: List<Pref>)
}