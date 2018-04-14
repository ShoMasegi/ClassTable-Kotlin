package masegi.sho.classtable.presentation.views.main.todo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.kotlin.data.model.Task
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.mapper.toResult
import masegi.sho.classtable.utli.ext.toLiveData
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/12.
 */

class TodoViewModel @Inject constructor(
        private val repository: LessonRepository
) : ViewModel() {


    // MARK: - Property

    internal val tasks: LiveData<Result<List<Task>>> by lazy {

        repository.tasks
                .subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }


    // MARK: - Internal

    internal fun remove(task: Task) {

        launch(CommonPool) { repository.delete(task) }
    }
}