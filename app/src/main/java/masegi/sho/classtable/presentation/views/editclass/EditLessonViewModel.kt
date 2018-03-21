package masegi.sho.classtable.presentation.views.editclass

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.mapper.toResult
import masegi.sho.classtable.utli.ext.toLiveData
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/20.
 */


class EditLessonViewModel @Inject constructor(
        private val repository: LessonRepository
) : ViewModel() {


    // MARK: - Property

    internal lateinit var day: DayOfWeek
    internal var start: Int = 0
    internal val lesson: LiveData<Result<Lesson>> by lazy {

        repository.getLesson(day, start)
                .subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }

    internal fun save(lesson: Lesson) {

        repository.save(lesson)
    }
}
