package masegi.sho.classtable.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import masegi.sho.classtable.presentation.common.notification.NotificationAttendanceService
import masegi.sho.classtable.presentation.common.notification.TakeAttendanceService

@Module
interface ServiceBuilder {
    @ContributesAndroidInjector()
    fun contributeService(): NotificationAttendanceService

    @ContributesAndroidInjector()
    fun contributeAttendanceService(): TakeAttendanceService
}
