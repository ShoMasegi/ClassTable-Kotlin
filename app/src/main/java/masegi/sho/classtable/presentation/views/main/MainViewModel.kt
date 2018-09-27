package masegi.sho.classtable.presentation.views.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.data.model.PrefEntity
import masegi.sho.classtable.data.repository.PrefRepository
import masegi.sho.classtable.presentation.common.KotPrefs
import javax.inject.Inject

/**
 * Created by masegi on 2018/04/19.
 */

class MainViewModel @Inject constructor(
        private val prefRepository: PrefRepository
) : ViewModel() {


    init {

        if (KotPrefs.tid == 0) {

            launch(CommonPool) { prefRepository.insert(PrefEntity(tid = 1)) }
            KotPrefs.tid = 1
            Prefs.sync(PrefEntity())
        }
    }
}