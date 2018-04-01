package masegi.sho.classtable.presentation.views.detail

import android.arch.lifecycle.ViewModel
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.kotlin.data.model.Lesson
import javax.inject.Inject

/**
 * Created by masegi on 2018/04/02.
 */

class DetailViewModel @Inject constructor(
        private val repository: LessonRepository
): ViewModel() {


    // MARK: - Property

    internal lateinit var lesson: Lesson
}
