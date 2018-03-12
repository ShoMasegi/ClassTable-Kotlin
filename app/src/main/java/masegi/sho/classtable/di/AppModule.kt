package masegi.sho.classtable.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by masegi on 2018/03/06.
 */

@Module
internal object AppModule {

    @Singleton
    @Provides @JvmStatic
    fun provideContext(application: Application): Context = application
}