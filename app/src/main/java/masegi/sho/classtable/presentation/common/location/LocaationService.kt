package masegi.sho.classtable.presentation.common.location

import android.app.Service
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import masegi.sho.classtable.R
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.data.repository.PrefRepository
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.mapper.toResult
import masegi.sho.classtable.presentation.common.notification.LessonAlarm
import masegi.sho.classtable.utli.ext.observe
import masegi.sho.classtable.utli.ext.toLiveData
import javax.inject.Inject


class LocationService: Service(), LifecycleOwner {

    @Inject lateinit var lessonRepository: LessonRepository
    @Inject lateinit var prefRepository: PrefRepository
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    override fun onCreate() {
        super.onCreate()
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
        AndroidInjection.inject(this)
        val notification = NotificationCompat.Builder(this).apply {
            setContentTitle("Title")
            setContentText("Content of notification")
            setSmallIcon(R.mipmap.ic_launcher)
        }.build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("Service", "LocationService.onStartCommand")
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
        observeValue()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.e("Service", "LocationService.onDestroy")
        lifecycleRegistry.markState(Lifecycle.State.DESTROYED)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }


    // MARK - Private

    private fun observeValue() {

        lessonRepository.lessons
                .map { ls -> ls.filter { it.tid == Prefs.tid } }
                .subscribeOn(Schedulers.io())
                .toResult(AndroidSchedulers.mainThread())
                .toLiveData()
                .observe(this) { result ->

                    when(result) {

                        is Result.Success -> {

                            Log.e("Service", "LocationService.Success: lesson - ${result.data.count()}")
                            LessonAlarm(this).toggleRegister(result.data.first())
                            stopSelf()
                        }
                        is Result.Failure -> {

                            Log.e("Service", "LocationService.Error")
                            stopSelf()
                        }
                    }
                }
    }

    companion object {

        fun createIntent(
                context: Context
        ): Intent {

            return Intent(context, LocationService::class.java)
        }
    }
}