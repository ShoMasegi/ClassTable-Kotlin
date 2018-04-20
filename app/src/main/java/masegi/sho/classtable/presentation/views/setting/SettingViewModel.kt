package masegi.sho.classtable.presentation.views.setting

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import masegi.sho.classtable.data.model.PrefEntity
import masegi.sho.classtable.data.repository.PrefRepository
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.mapper.toResult
import masegi.sho.classtable.utli.ext.toLiveData
import javax.inject.Inject

/**
 * Created by masegi on 2018/04/16.
 */

class SettingViewModel @Inject constructor(
        private val repository: PrefRepository
) : ViewModel() {

    internal val prefs: LiveData<Result<List<PrefEntity>>> by lazy {

        repository.prefs
                .subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }

    internal fun insert(pref: PrefEntity) {

        launch(CommonPool) { repository.insert(pref) }
    }
}
