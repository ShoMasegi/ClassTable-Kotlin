package masegi.sho.classtable.presentation.views.edittask

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.kotlin.data.model.Task
import javax.inject.Inject

/**
 * Created by masegi on 2018/04/03.
 */

class EditTaskViewModel @Inject constructor(
        private val repository: LessonRepository
): ViewModel() {

    internal fun save(task: Task) = launch(CommonPool) { repository.save(task) }
}