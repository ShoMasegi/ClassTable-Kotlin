package masegi.sho.classtable.presentation.common.mapper

import androidx.annotation.CheckResult
import io.reactivex.*
import masegi.sho.classtable.presentation.Result

/**
 * Created by masegi on 2018/03/21.
 */

@CheckResult
fun <T> Flowable<T>.toResult(scheduler: Scheduler): Flowable<Result<T>> {

    return compose { item ->

        item.map { Result.success(it) }
                .onErrorReturn { e -> Result.failure(e.message ?: "unknown", e) }
                .observeOn(scheduler)
                .startWith(Result.inProgress())
    }
}

@CheckResult
fun <T> Observable<T>.toResult(scheduler: Scheduler): Observable<Result<T>> {

    return compose { item ->

        item.map { Result.success(it) }
                .onErrorReturn { e -> Result.failure(e.message ?: "unknown", e) }
                .observeOn(scheduler)
                .startWith(Result.inProgress())
    }
}

@CheckResult
fun <T> Single<T>.toResult(scheduler: Scheduler): Observable<Result<T>> {

    return toObservable().toResult(scheduler)
}

@CheckResult
fun <T> Completable.toResult(scheduler: Scheduler): Observable<Result<T>> {

    return toObservable<T>().toResult(scheduler)
}

@CheckResult
fun <T> Maybe<T>.toResult(scheduler: Scheduler): Observable<Result<T>> {

    return toObservable().toResult(scheduler)
}