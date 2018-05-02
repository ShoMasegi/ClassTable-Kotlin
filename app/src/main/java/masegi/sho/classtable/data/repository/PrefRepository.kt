package masegi.sho.classtable.data.repository

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.model.PrefEntity
import masegi.sho.classtable.kotlin.data.model.Time

/**
 * Created by masegi on 2018/04/17.
 */

interface PrefRepository {

    val prefs: Flowable<List<PrefEntity>>
    val times: Flowable<List<Time>>

    fun getPref(tid: Int): Maybe<PrefEntity>
    fun insert(pref: PrefEntity)
    fun delete(pref: PrefEntity)

    fun insert(time: Time)
    fun insert(times: List<Time>)
    fun delete(tid: Int)
}
