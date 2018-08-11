package masegi.sho.classtable.presentation.common.notification

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import masegi.sho.classtable.R
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.data.model.AttendType
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.data.repository.PrefRepository
import javax.inject.Inject


class NotificationAttendanceService: Service() {

    @Inject lateinit var lessonRepository: LessonRepository
    @Inject lateinit var prefRepository: PrefRepository
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
        val notification = NotificationCompat.Builder(this).apply {
            setContentTitle("ClassTable")
            setContentText("Checking attendance ...")
            setSmallIcon(R.mipmap.ic_launcher)
        }.build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("Service", "LocationService.onStartCommand")
        observeValue(intent)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.e("Service", "LocationService.onDestroy")
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    // MARK - Private

    private fun observeValue(intent: Intent?) {

        Log.e("Service", "LocationService.observeValue")
        lessonRepository.lessons
                .map { ls -> ls.filter { it.tid == Prefs.tid } }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { lessons ->

                            Log.e("Service", "LocationService.Success: lesson - ${lessons.count()}")
                            intent?.let {

                                val actionIntents: MutableMap<AttendType, PendingIntent> = mutableMapOf()
                                AttendType.values().forEach {

                                    actionIntents[it] = TakeAttendanceService.createPendingIntent(this, lessons.first(), it)
                                }
                                showNotification(NotificationContent.parse(intent), actionIntents)
                            }
                            LessonAlarm(this).toggleRegister(lessons.first())
                            stopSelf()
                        },
                        onError = {

                            Log.e("Service", "LocationService.Error")
                            stopSelf()
                        }
                )
                .addTo(compositeDisposable)
    }

    companion object {

        fun createIntent(
                context: Context,
                notificationContent: NotificationContent
        ): Intent {

            return Intent(context, NotificationAttendanceService::class.java).apply {
                notificationContent.putExtrasTo(this)
            }
        }
    }
}