package masegi.sho.classtable.presentation.common.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import masegi.sho.classtable.R


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
        content: NotificationContent
) {

    val notificationManager = NotificationManagerCompat.from(this)
    notificationManager.notify(
            0,
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
    )
    notificationManager.notify(
            content.lesson.id.hashCode(),
            NotificationCompat.Builder(this, content.channelType.id)
                    .setStyle(
                            NotificationCompat.BigTextStyle()
                                    .setBigContentTitle("Big text style")
                                    .bigText(content.lesson.name)
                    )
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setGroup(content.channelType.id)
                    .build()
    )
    Log.e("Notification", "showNotification")
}