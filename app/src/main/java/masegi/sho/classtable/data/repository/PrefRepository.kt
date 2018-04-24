package masegi.sho.classtable.data.repository

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.model.PrefEntity

/**
 * Created by masegi on 2018/04/17.
 */

interface PrefRepository {

    val prefs: Flowable<List<PrefEntity>>

    fun getPref(tid: Int): Maybe<PrefEntity>
    fun insert(pref: PrefEntity)
    fun delete(pref: PrefEntity)
}
