package masegi.sho.classtable.presentation.views.editclass

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.StringRes
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import masegi.sho.classtable.R
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.SaveResult
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/20.
 */


class EditLessonViewModel @Inject constructor(
        private val repository: LessonRepository
) : ViewModel() {


    // MARK: - Property

    internal lateinit var lesson: Lesson
    internal var isSaveSuccess: MutableLiveData<SaveResult> = MutableLiveData()


    // MARK: - Internal

    internal fun save() {

        val end = lesson.start + lesson.section - 1

        when {

            end > Prefs.dayLessonCount -> {

                isSaveSuccess.value = SaveResult.failure(R.string.error_overflow)
                return
            }

            lesson.name.isNullOrEmpty() -> {

                isSaveSuccess.value = SaveResult.failure(R.string.error_no_classname)
                return
            }

            else -> isSaveSuccess.value = SaveResult.inProgress()
        }

        repository.getLessons(lesson.week, lesson.start, end)
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onSuccess = {

                            if (it.isNotEmpty()) {

                                it.forEach {

                                    if (it.id != lesson.id) {

                                        isSaveSuccess.postValue(SaveResult.failure(R.string.error_already_exist))
                                        return@subscribeBy
                                    }
                                }
                                repository.insert(lesson)
                                isSaveSuccess.postValue(SaveResult.success(lesson))
                            }
                            else {

                                repository.insert(lesson)
                                isSaveSuccess.postValue(SaveResult.success(lesson))
                            }
                        },
                        onError = {

                            isSaveSuccess.postValue(SaveResult.failure(R.string.error_default))
                        },
                        onComplete = {

                            repository.insert(lesson)
                            isSaveSuccess.postValue(SaveResult.success(lesson))
                        }
                )
    }
}
