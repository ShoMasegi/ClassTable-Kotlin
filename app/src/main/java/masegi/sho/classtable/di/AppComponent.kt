package masegi.sho.classtable.kotlin.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import masegi.sho.classtable.di.ActivityBindingModule
import masegi.sho.classtable.di.AppModule
import masegi.sho.classtable.di.DatabaseModule
import masegi.sho.classtable.di.ViewModelModule
import masegi.sho.classtable.kotlin.App
import javax.inject.Singleton

/**
 * Created by masegi on 2018/03/06.
 */

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    DatabaseModule::class,
    ViewModelModule::class,
    ActivityBindingModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun databaseModule(databaseModule: DatabaseModule): Builder
        fun build(): AppComponent

    }

    override fun inject(app: App)
}
