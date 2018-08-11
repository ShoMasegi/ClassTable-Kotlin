package masegi.sho.classtable.presentation.common.notification

import android.content.Intent
import android.os.Bundle
import masegi.sho.classtable.kotlin.data.model.Lesson
import org.parceler.Parcels


sealed class NotificationContent(
        open val lesson: Lesson,
        val channelType: NotificationChannelType
) {

    enum class NotificationType {
        ONLY_NOTIFY,
        ACTION,
        GPS_ERROR_AND_ACTION
    }


    data class OnlyNotifyNotification(
            override val lesson: Lesson
    ) : NotificationContent(
            lesson,
            NotificationChannelType.CHECKING_ATTENDANCE
    ) {

        override fun putExtrasTo(intent: Intent) {

            intent.apply {

                val bundle = Bundle().apply { putParcelable(EXTRA_LESSON, Parcels.wrap(lesson)) }
                putExtra(EXTRA_LESSON_BUNDLE, bundle)
            }
        }

        companion object {

            fun parse(intent: Intent): OnlyNotifyNotification {
                return OnlyNotifyNotification(
                        Parcels.unwrap(intent.getBundleExtra(EXTRA_LESSON_BUNDLE).getParcelable(EXTRA_LESSON))
                )
            }
        }
    }

    data class ActionNotification(
            override val lesson: Lesson
    ) : NotificationContent(
            lesson,
            NotificationChannelType.CHECKING_ATTENDANCE
    ) {

        override fun putExtrasTo(intent: Intent) {

            intent.apply {

                val bundle = Bundle().apply { putParcelable(EXTRA_LESSON, Parcels.wrap(lesson)) }
                putExtra(EXTRA_LESSON_BUNDLE, bundle)
            }
        }

        companion object {

            fun parse(intent: Intent): ActionNotification {
                return ActionNotification(
                        Parcels.unwrap(intent.getBundleExtra(EXTRA_LESSON_BUNDLE).getParcelable(EXTRA_LESSON))
                )
            }
        }
    }

    abstract fun putExtrasTo(intent: Intent)

    companion object {

        const val EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID"
        private const val EXTRA_LESSON_BUNDLE = "EXTRA_LESSON_BUNDLE"
        private const val EXTRA_LESSON = "EXTRA_LESSON"

        fun parse(intent: Intent): NotificationContent {

            return when (intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0)) {
                NotificationType.ONLY_NOTIFY.ordinal -> {
                    OnlyNotifyNotification.parse(intent)
                }
                else -> throw NotImplementedError()
            }
        }
    }
}