package masegi.sho.classtable.presentation.views.editclass

import android.arch.lifecycle.ViewModel
import masegi.sho.classtable.data.repository.LessonRepository
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/20.
 */


class EditLessonViewModel @Inject constructor(
        private val repository: LessonRepository
) : ViewModel() {

}
