package masegi.sho.classtable.presentation.views.main.today

import androidx.lifecycle.*
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.data.model.Memo
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.data.repository.PrefRepository
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.kotlin.data.model.Time
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.KotPrefs
import masegi.sho.classtable.presentation.common.mapper.toResult
import masegi.sho.classtable.utli.ResettableLazy
import masegi.sho.classtable.utli.ResettableLazyManager
import masegi.sho.classtable.utli.ext.toLiveData
import javax.inject.Inject

/**
 * Created by masegi on 2018/04/15.
 */

class LessonListViewModel @Inject constructor(
        private val repository: LessonRepository,
        private val prefRepository: PrefRepository
) : ViewModel(), LifecycleObserver {


    // MARK: - Property

    private val manager = ResettableLazyManager()
    private val compositeDisposable = CompositeDisposable()

    internal lateinit var day: DayOfWeek

    internal val data: LiveData<Result<List<Pair<Lesson, Memo?>>>> by ResettableLazy(manager) {

        Flowables.zip( // TODO: zipする必要ない...?
                repository.lessons.map { ls ->

                    ls.filter { it.tid == Prefs.tid && it.week == day }
                },
                repository.memos.map { m -> m.filter { it.tid == Prefs.tid } }
        ) { lessons, memos ->

            val data: MutableList<Pair<Lesson, Memo?>> = mutableListOf()
            lessons.forEach { l ->
                data.add(l to memos.firstOrNull { it.lid == l.id })
            }
            data.sortedBy { it.first.start }
        }.subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }

    internal val times: LiveData<Result<Map<Int, Time>>> by lazy {

        prefRepository.times
                .map { ts ->

                    val map = mutableMapOf<Int, Time>()
                    ts.filter { it.tid == KotPrefs.tid }
                      .map { map[it.periodNum] = it }
                    map.toMap()
                }
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }

    private val prefUpdated: Flowable<Boolean> by lazy {

        prefRepository.prefs
                .map { true }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


    // MARK: - Private

    private fun observePref() {

        prefUpdated
                .subscribe { manager.reset() }
                .addTo(compositeDisposable)
    }


    // MARK: - Lifecycle

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() { observePref() }
}
