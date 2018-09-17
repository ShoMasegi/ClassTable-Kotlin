package masegi.sho.classtable.presentation.views.edittable

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import masegi.sho.classtable.data.model.PrefEntity
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.data.repository.PrefRepository
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.mapper.toResult
import masegi.sho.classtable.utli.ext.toLiveData
import javax.inject.Inject

/**
 * Created by masegi on 2018/04/24.
 */

class EditTableViewModel @Inject constructor(
        private val prefRepository: PrefRepository,
        private val lessonRepository: LessonRepository
) : ViewModel() {

    internal val prefs: LiveData<Result<List<PrefEntity>>> by lazy {

        prefRepository.prefs
                .subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }

    internal fun insert(pref: PrefEntity) {

        launch(CommonPool) { prefRepository.insert(pref) }
    }

    internal fun delete(pref: PrefEntity) {

        launch(CommonPool) {

            prefRepository.delete(pref)
            lessonRepository.delete(pref.tid)
        }
    }
}
