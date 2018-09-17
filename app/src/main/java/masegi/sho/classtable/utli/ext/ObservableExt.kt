package masegi.sho.classtable.utli.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.disposables.Disposable


/**
 * Created by masegi on 2018/03/21.
 */

fun <T> Observable<T>.toLiveData() : LiveData<T> {

    return object : MutableLiveData<T>() {

        var disposable: Disposable? = null

        override fun onActive() {

            super.onActive()
            disposable = this@toLiveData.subscribe({ this.postValue(it) })
        }

        override fun onInactive() {

            disposable?.dispose()
            super.onInactive()
        }
    }
}