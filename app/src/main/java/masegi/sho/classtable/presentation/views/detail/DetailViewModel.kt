package masegi.sho.classtable.presentation.views.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import masegi.sho.classtable.data.model.Memo
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.kotlin.data.model.Task
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.mapper.toResult
import masegi.sho.classtable.utli.ext.toLiveData
import javax.inject.Inject

/**
 * Created by masegi on 2018/04/02.
 */

class DetailViewModel @Inject constructor(
        private val repository: LessonRepository
): ViewModel() {


    // MARK: - Property

    internal lateinit var lesson: Lesson
    internal val tasks: LiveData<Result<List<Task>>> by lazy {

        repository.getTasks(lesson)
                .subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }
    internal val memo: LiveData<Result<Memo>> by lazy {

        repository.getMemo(lesson)
                .subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }
}
