package masegi.sho.classtable.kotlin

import com.chibatching.kotpref.Kotpref
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import masegi.sho.classtable.data.parceler.CalendarParcelConverter
import masegi.sho.classtable.data.parceler.ThemeParcelConverter
import masegi.sho.classtable.di.DatabaseModule
import masegi.sho.classtable.kotlin.data.model.ThemeColor
import masegi.sho.classtable.kotlin.di.DaggerAppComponent
import masegi.sho.classtable.presentation.common.notification.initNotificationChannel
import masegi.sho.classtable.presentation.views.main.today.PreviousDayPrefs
import org.parceler.Parcel
import org.parceler.ParcelClass
import org.parceler.ParcelClasses
import java.util.*

/**
 * Created by masegi on 2018/03/06.
 */

@ParcelClasses(value = [
    ParcelClass(ThemeColor::class, annotation = Parcel(converter = ThemeParcelConverter::class)),
    ParcelClass(Calendar::class, annotation = Parcel(converter = CalendarParcelConverter::class))
])
open class App : DaggerApplication() {

    override fun onCreate() {

        super.onCreate()
        Kotpref.init(this)
        setupNotification()
    }

    override fun onTerminate() {

        PreviousDayPrefs.previousDayTab = null
        super.onTerminate()
    }

    private fun setupNotification() {
        initNotificationChannel()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        return DaggerAppComponent
                .builder()
                .application(this)
                .databaseModule(DatabaseModule.instance)
                .build()
    }
}
