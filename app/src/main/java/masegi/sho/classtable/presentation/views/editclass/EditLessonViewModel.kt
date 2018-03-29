package masegi.sho.classtable.presentation.views.editclass

import android.arch.lifecycle.ViewModel
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.kotlin.data.model.Lesson
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/20.
 */


class EditLessonViewModel @Inject constructor(
        private val repository: LessonRepository
) : ViewModel() {


    // MARK: - Property

    internal lateinit var lesson: Lesson


    // MARK: - Internal

    internal fun save(lesson: Lesson) = repository.save(lesson)
}
