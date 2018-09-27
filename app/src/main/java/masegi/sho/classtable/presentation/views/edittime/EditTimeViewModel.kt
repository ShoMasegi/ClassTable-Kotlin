package masegi.sho.classtable.presentation.views.edittime

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import masegi.sho.classtable.data.repository.PrefRepository
import masegi.sho.classtable.kotlin.data.model.Time
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.mapper.toResult
import masegi.sho.classtable.utli.ext.toLiveData
import javax.inject.Inject

/**
 * Created by masegi on 2018/04/26.
 */

class EditTimeViewModel @Inject constructor(
        private val repository: PrefRepository
) : ViewModel() {


    // MARK: - Property

    internal val times: LiveData<Result<Map<Int, Time>>> by lazy {

        repository.times
                .map { ts ->

                    val map: MutableMap<Int, Time> = mutableMapOf()
                    ts.forEach { map[it.periodNum] = it }
                    map.toMap()
                }
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
    }


    // MARK: - Internal

    internal fun save(map: Map<Int, Time>) {

        repository.insert(map.values.toList())
    }
}