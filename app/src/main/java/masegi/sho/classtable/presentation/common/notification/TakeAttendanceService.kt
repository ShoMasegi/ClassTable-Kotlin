package masegi.sho.classtable.presentation.common.notification

import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import masegi.sho.classtable.data.model.AttendType
import masegi.sho.classtable.kotlin.data.model.Lesson


class TaskAttendanceService: IntentService("TakeAttendanceService") {

    override fun onHandleIntent(intent: Intent?) {

        intent?.let {

            val ordinal = it.getIntExtra(EXTRA_ATTEND_TYPE, 0)
            val lid = it.getIntExtra(EXTRA_LESSON_ID, 0)
            val type: AttendType = AttendType.values()[ordinal]
            Log.e("TaskAttendanceService", "AttendType : $type")
            dismissNotification(lid)
        }
        Log.e("TaskAttendanceService", "onHandleIntent")
    }

    companion object {

        private const val EXTRA_LESSON_ID = "EXTRA_LESSON_ID"
        private const val EXTRA_ATTEND_TYPE = "EXTRA_ATTEND_TYPE"

        private fun createIntent(context: Context): Intent {

            return Intent(context, TaskAttendanceService::class.java)
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
