package masegi.sho.classtable.data.repository

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.db.PrefDatabase
import masegi.sho.classtable.data.model.Pref
import javax.inject.Inject

/**
 * Created by masegi on 2018/04/17.
 */

class PrefDataRepository @Inject constructor(
        private val database: PrefDatabase
) : PrefRepository {

    override val prefs: Flowable<List<Pref>> = database.getAll()

    override fun getPref(tid: Int): Maybe<Pref> = database.get(tid)

    override fun insert(pref: Pref) = database.insert(pref)
}