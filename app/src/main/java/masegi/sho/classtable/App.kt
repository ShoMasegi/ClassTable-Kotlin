package masegi.sho.classtable.kotlin

import com.chibatching.kotpref.Kotpref
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import masegi.sho.classtable.data.parceler.ThemeConverter
import masegi.sho.classtable.di.DatabaseModule
import masegi.sho.classtable.kotlin.data.model.ThemeColor
import masegi.sho.classtable.kotlin.di.DaggerAppComponent
import org.parceler.Parcel
import org.parceler.ParcelClass
import org.parceler.ParcelClasses

/**
 * Created by masegi on 2018/03/06.
 */

@ParcelClasses(value = [
    ParcelClass(value = ThemeColor::class, annotation = Parcel(converter = ThemeConverter::class))
])
open class App : DaggerApplication() {

    override fun onCreate() {

        super.onCreate()
        Kotpref.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        return DaggerAppComponent
                .builder()
                .application(this)
                .databaseModule(DatabaseModule.instance)
                .build()
    }
}
