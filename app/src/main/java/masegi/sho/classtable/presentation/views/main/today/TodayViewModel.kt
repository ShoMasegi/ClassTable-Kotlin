package masegi.sho.classtable.presentation.views.main.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.data.model.Memo
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

class TodayViewModel @Inject constructor(
        private val prefRepository: PrefRepository
) : ViewModel() {


    // MARK: - Property

    internal val pref: LiveData<Result<PrefEntity>> by lazy {

        prefRepository.prefs
                .map { it.firstOrNull { it.tid == Prefs.tid } ?: PrefEntity() }
                .subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }
}