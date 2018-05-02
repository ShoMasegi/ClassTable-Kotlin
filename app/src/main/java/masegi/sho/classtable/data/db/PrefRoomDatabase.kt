package masegi.sho.classtable.data.db

import io.reactivex.Flowable
import io.reactivex.Maybe
import masegi.sho.classtable.data.db.dao.PrefDao
import masegi.sho.classtable.data.db.dao.TimeDao
import masegi.sho.classtable.data.model.PrefEntity
import masegi.sho.classtable.kotlin.data.model.Time
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/10.
 */

class PrefRoomDatabase @Inject constructor(
        private val prefDao: PrefDao,
        private val timeDao: TimeDao
) : PrefDatabase {

    override fun getAllTimes(): Flowable<List<Time>> = timeDao.getAll()

    override fun deleteTimes() = timeDao.deleteAll()

    override fun deleteTimes(tid: Int) = timeDao.delete(tid)

    override fun insertTime(time: Time) = timeDao.insert(time)

    override fun insertTimes(times: List<Time>) = timeDao.insert(times)

    override fun getAll(): Flowable<List<PrefEntity>> = prefDao.getAll()

    override fun get(tid: Int): Maybe<PrefEntity> = prefDao.get(tid)

    override fun deleteAll() = prefDao.deleteAll()

    override fun delete(tid: Int) = prefDao.delete(tid)

    override fun insert(pref: PrefEntity) = prefDao.insert(pref)

    override fun insert(prefs: List<PrefEntity>) = prefDao.insert(prefs)

}
