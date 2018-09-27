package masegi.sho.classtable.presentation.views.editmemo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import masegi.sho.classtable.data.model.Memo
import masegi.sho.classtable.data.repository.LessonRepository
import javax.inject.Inject

/**
 * Created by masegi on 2018/04/11.
 */

class EditMemoViewModel @Inject constructor(
        private val repository: LessonRepository
): ViewModel() {

    internal fun save(memo: Memo) = launch(CommonPool) { repository.save(memo) }
}
