package masegi.sho.classtable.presentation.common.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import android.util.Log
import masegi.sho.classtable.R
import masegi.sho.classtable.data.model.AttendType


enum class NotificationChannelType(
        val id: String,
        @StringRes val nameRes: Int,
        val importance: Int
) {

    CHECKING_ATTENDANCE(
            "checking_attendance_channel",
            R.string.notification_channel_name_checking_attendance,
            NotificationManager.IMPORTANCE_HIGH
    )
}

fun Context.initNotificationChannel() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannelType.values().forEach(::createNotificationChannel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun Context.createNotificationChannel(channelType: NotificationChannelType) {
    (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(NotificationChannel(
                    channelType.id,
                    getString(channelType.nameRes),
                    channelType.importance
            ))
}

fun Context.showNotification(
        content: NotificationContent,
        pendingIntentsMap: Map<AttendType, PendingIntent> = mapOf()
) {

    val notificationManager = NotificationManagerCompat.from(this)
    val summaryNotification =
            NotificationCompat.Builder(this, content.channelType.id)
                    .setContentTitle("Group content title")
                    .setContentText("Group content text")
                    .setStyle(NotificationCompat.BigTextStyle()
                            .setSummaryText("SummaryTex")
                    )
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setGroup(content.channelType.id)
                    .setGroupSummary(true)
                    .build()
    val notification =
            NotificationCompat.Builder(this, content.channelType.id).apply {
                setStyle(
                        NotificationCompat.BigTextStyle()
                                .setBigContentTitle("Big text style")
                                .bigText(content.lesson.name)
                )
                setAutoCancel(true)
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setGroup(content.channelType.id)
                color = ContextCompat.getColor(this@showNotification, R.color.colorPrimary)
                AttendType.values().forEach { type ->

                    val pendingIntent: PendingIntent? = pendingIntentsMap[type]
                    pendingIntent?.let {

                        when (type) {

                            AttendType.ATTEND -> addAction(0, getString(R.string.attend), it)
                            AttendType.LATE -> addAction(0, getString(R.string.late), it)
                            AttendType.ABSENT -> addAction(0, getString(R.string.absent), it)
                        }
                    }
                }
            }.build()
    notification.flags = NotificationCompat.FLAG_NO_CLEAR
    notificationManager.notify(0, summaryNotification)
    notificationManager.notify(content.lesson.id.hashCode(), notification)
    Log.e("Notification", "showNotification")
}

fun Context.dismissNotification(id: Int) {

    NotificationManagerCompat.from(this).cancel(id)
}
