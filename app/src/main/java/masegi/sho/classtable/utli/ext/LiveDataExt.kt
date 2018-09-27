package masegi.sho.classtable.utli.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by masegi on 2018/03/21.
 */

inline fun <T> LiveData<T>.observe(
        owner: LifecycleOwner,
        crossinline observer: (T?) -> Unit
)
{

    observe(owner, Observer<T> { v -> observer(v) })
}

inline fun <T> LiveData<T>.observeNonNull(
        owner: LifecycleOwner,
        crossinline observer: (T) -> Unit
)
{

    this.observe(owner, Observer {

        if (it != null) { observer(it) }
    })
}