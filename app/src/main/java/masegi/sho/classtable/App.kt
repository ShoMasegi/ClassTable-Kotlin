package masegi.sho.classtable.kotlin

import com.chibatching.kotpref.Kotpref
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import masegi.sho.classtable.di.DatabaseModule
import masegi.sho.classtable.kotlin.di.DaggerAppComponent

/**
 * Created by masegi on 2018/03/06.
 */

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
