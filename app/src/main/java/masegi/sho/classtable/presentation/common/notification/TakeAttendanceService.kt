package masegi.sho.classtable.presentation.common.notification

import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import masegi.sho.classtable.data.model.AttendType
import masegi.sho.classtable.data.repository.LessonRepository
import masegi.sho.classtable.kotlin.data.model.Lesson
import javax.inject.Inject


class TakeAttendanceService: IntentService("TakeAttendanceService") {

    @Inject lateinit var lessonRepository: LessonRepository
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {

        intent?.let {

            val ordinal = it.getIntExtra(EXTRA_ATTEND_TYPE, 0)
            val lid = it.getIntExtra(EXTRA_LESSON_ID, 0)
            val type: AttendType = AttendType.values()[ordinal]
            lessonRepository.getLesson(lid)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onSuccess = {

                                if (it != null) {

                                    when (type) {
                                        AttendType.ATTEND -> it.attendance.attend++
                                        AttendType.LATE -> it.attendance.late++
                                        AttendType.ABSENT -> it.attendance.absence++
                                    }
                                    lessonRepository.insert(it)
                                    Log.e("TakeAttendanceService", "take attendance")
                                }
                            },
                            onError = {
                                Log.e("TakeAttendanceService", "onError")
                            }
                    )
                    .addTo(compositeDisposable)
            Log.e("TaskAttendanceService", "AttendType : $type")
            dismissNotification(lid)
        }
        Log.e("TaskAttendanceService", "onHandleIntent")
    }

    override fun onDestroy() {

        compositeDisposable.dispose()
        super.onDestroy()
    }

    companion object {

        private const val EXTRA_LESSON_ID = "EXTRA_LESSON_ID"
        private const val EXTRA_ATTEND_TYPE = "EXTRA_ATTEND_TYPE"

        private fun createIntent(context: Context): Intent {

            return Intent(context, TakeAttendanceService::class.java)
        }

        fun createPendingIntent(context: Context, lesson: Lesson, type: AttendType): PendingIntent {

            val intent = createIntent(context).apply {

                putExtra(EXTRA_ATTEND_TYPE, type.ordinal)
                putExtra(EXTRA_LESSON_ID, lesson.id)
            }
            return PendingIntent.getService(
                    context,
                    lesson.id.hashCode() + type.ordinal,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }
}
