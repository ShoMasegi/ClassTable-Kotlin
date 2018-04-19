package masegi.sho.classtable.data.repository

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.model.Pref

/**
 * Created by masegi on 2018/04/17.
 */

interface PrefRepository {

    val prefs: Flowable<List<Pref>>

    fun getPref(tid: Int): Maybe<Pref>

    fun insert(pref: Pref)
}
