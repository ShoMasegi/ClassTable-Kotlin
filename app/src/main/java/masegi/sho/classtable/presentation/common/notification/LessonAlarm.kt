package masegi.sho.classtable.presentation.common.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.util.Log
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.common.location.LocationService
import java.util.*
import java.util.concurrent.TimeUnit


class LessonAlarm constructor(val context: Context) {

    internal fun toggleRegister(lesson: Lesson) {

        if (lesson.id == 0) unregister(lesson) else register(lesson)
    }

    private fun register(lesson: Lesson) {

        val noticeTime = Calendar.getInstance()
        noticeTime.add(Calendar.MINUTE, 11)
        val time = noticeTime.timeInMillis - NOTIFICATION_TIME_BEFORE_START_MILLS

        if (System.currentTimeMillis() < time) {

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        time,
                        createAlarmIntent(context, lesson)
                )
            }
            else {

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, createAlarmIntent(context, lesson))
            }
            Log.e("LessonAlarm", "register()")
        }
    }

    private fun unregister(lesson: Lesson) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(createAlarmIntent(context, lesson))
    }

    private fun createAlarmIntent(context: Context, lesson: Lesson): PendingIntent {

        val notificationContent = NotificationContent.OnlyNotifyNotification(lesson)
        val intent = LocationService.createIntent(
                context,
                notificationContent
        )
        return PendingIntent.getService(
                context,
                lesson.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    companion object {

        private val NOTIFICATION_TIME_BEFORE_START_MILLS = TimeUnit.MINUTES.toMillis(10)
    }
}