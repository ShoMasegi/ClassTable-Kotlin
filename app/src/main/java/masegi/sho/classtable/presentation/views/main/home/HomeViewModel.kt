package masegi.sho.classtable.presentation.views.main.home

import androidx.lifecycle.*
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.data.repository.PrefRepository
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.mapper.toResult
import masegi.sho.classtable.utli.ResettableLazy
import masegi.sho.classtable.utli.ResettableLazyManager
import masegi.sho.classtable.utli.ext.toLiveData
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/12.
 */

class HomeViewModel @Inject constructor(
        private val repository: LessonRepository,
        private val prefRepository: PrefRepository
) : ViewModel(), LifecycleObserver {


    // MARK: - Property

    private val manager = ResettableLazyManager()
    private val compositeDisposable = CompositeDisposable()
    internal val lessons: LiveData<Result<List<Lesson>>> by ResettableLazy(manager) {

        repository.lessons
                .map { ls -> ls.filter { it.tid == Prefs.tid } }
                .subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }
    private val prefUpdated: Flowable<Boolean> by lazy {

        prefRepository.prefs
                .map { true }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


    // MARK: - Internal

    internal fun delete(lesson: Lesson) {

        launch(CommonPool) { repository.delete(lesson) }
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

    override fun onCleared() {

        super.onCleared()
        compositeDisposable.clear()
    }
}