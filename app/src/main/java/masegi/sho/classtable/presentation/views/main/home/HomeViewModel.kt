package masegi.sho.classtable.presentation.views.main.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Flowables
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.data.model.PrefEntity
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.data.repository.PrefRepository
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.mapper.toResult
import masegi.sho.classtable.utli.ext.toLiveData
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/12.
 */

class HomeViewModel @Inject constructor(
        private val repository: LessonRepository,
        private val prefRepository: PrefRepository
) : ViewModel() {


    // MARK: - Property

    internal val data: LiveData<Result<Pair<List<Lesson>, PrefEntity>>> by lazy {

        Flowables.zip(
                repository.lessons,
                prefRepository.prefs.map { it.firstOrNull { it.tid == Prefs.tid} ?: PrefEntity() }
        ).subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }


    // MARK: - Internal

    internal fun delete(lesson: Lesson) {

        launch(CommonPool) {

            repository.delete(lesson)
        }
    }
}