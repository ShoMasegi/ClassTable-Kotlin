package masegi.sho.classtable.presentation.views.main.today

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Flowables
import io.reactivex.schedulers.Schedulers
import masegi.sho.classtable.data.model.Memo
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.mapper.toResult
import masegi.sho.classtable.utli.ext.toLiveData
import javax.inject.Inject

/**
 * Created by masegi on 2018/04/15.
 */

class LessonListViewModel @Inject constructor(
        private val repository: LessonRepository
) : ViewModel() {


    // MARK: - Property

    internal lateinit var day: DayOfWeek

    internal val data: LiveData<Result<List<Pair<Lesson, Memo?>>>> by lazy {

        Flowables.zip(
                repository.lessons.map { ls -> ls.filter { it.week == day } },
                repository.memos,
                { lessons, memos ->

                    val data: MutableList<Pair<Lesson, Memo?>> = mutableListOf()
                    lessons.forEach { l -> data.add(l to memos.firstOrNull { it.lid == l.id }) }
                    data.sortedBy { it.first.start }
                }
        ).subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }
}