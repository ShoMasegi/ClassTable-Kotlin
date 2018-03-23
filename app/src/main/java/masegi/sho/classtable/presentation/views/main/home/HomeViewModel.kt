package masegi.sho.classtable.presentation.views.main.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.mapper.toResult
import masegi.sho.classtable.utli.ext.toLiveData
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/12.
 */

class HomeViewModel @Inject constructor(
        private val repository: LessonRepository
) : ViewModel() {


    // MARK: - Property

    internal val lessons: LiveData<Result<List<Lesson>>> by lazy {

        repository.lessons
                .subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }


    // MARK: - Internal

    internal fun delete(lesson: Lesson) {

        repository.delete(lesson)
    }
}