package masegi.sho.classtable.data.db

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.db.dao.PrefDao
import masegi.sho.classtable.data.model.Pref
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/10.
 */

class PrefRoomDatabase @Inject constructor(
        private val prefDao: PrefDao
) : PrefDatabase {

    override fun getAll(): Flowable<List<Pref>> = prefDao.getAll()

    override fun get(tid: Int): Maybe<Pref> = prefDao.get(tid)

    override fun deleteAll() = prefDao.deleteAll()

    override fun delete(tid: Int) = prefDao.delete(tid)

    override fun insert(pref: Pref) = prefDao.insert(pref)

    override fun insert(prefs: List<Pref>) = prefDao.insert(prefs)
}
