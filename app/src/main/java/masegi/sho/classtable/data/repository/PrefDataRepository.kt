package masegi.sho.classtable.data.repository

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.db.PrefDatabase
import masegi.sho.classtable.data.model.PrefEntity
import masegi.sho.classtable.kotlin.data.model.Time
import javax.inject.Inject

/**
 * Created by masegi on 2018/04/17.
 */

class PrefDataRepository @Inject constructor(
        private val database: PrefDatabase
) : PrefRepository {

    override val prefs: Flowable<List<PrefEntity>> = database.getAll()

    override val times: Flowable<List<Time>> = database.getAllTimes()

    override fun getPref(tid: Int): Maybe<PrefEntity> = database.get(tid)

    override fun insert(pref: PrefEntity) = database.insert(pref)

    override fun delete(pref: PrefEntity) = database.delete(pref.tid)

    override fun insert(time: Time) = database.insertTime(time)

    override fun insert(times: List<Time>) = database.insertTimes(times)

    override fun delete(tid: Int) = database.deleteTimes(tid)
}