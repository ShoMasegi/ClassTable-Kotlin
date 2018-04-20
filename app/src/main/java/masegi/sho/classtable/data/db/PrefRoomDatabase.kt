package masegi.sho.classtable.data.db

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.db.dao.PrefDao
import masegi.sho.classtable.data.model.PrefEntity
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/10.
 */

class PrefRoomDatabase @Inject constructor(
        private val prefDao: PrefDao
) : PrefDatabase {

    override fun getAll(): Flowable<List<PrefEntity>> = prefDao.getAll()

    override fun get(tid: Int): Maybe<PrefEntity> = prefDao.get(tid)

    override fun deleteAll() = prefDao.deleteAll()

    override fun delete(tid: Int) = prefDao.delete(tid)

    override fun insert(pref: PrefEntity) = prefDao.insert(pref)

    override fun insert(prefs: List<PrefEntity>) = prefDao.insert(prefs)
}
