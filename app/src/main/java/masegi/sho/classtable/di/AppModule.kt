package masegi.sho.classtable.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import masegi.sho.classtable.data.db.LessonDatabase
import masegi.sho.classtable.data.db.RestoreDatabase
import masegi.sho.classtable.data.repository.LessonDataRepository
import masegi.sho.classtable.data.repository.LessonRepository
import javax.inject.Singleton

/**
 * Created by masegi on 2018/03/06.
 */

@Module
internal object AppModule {

    @Singleton @Provides @JvmStatic
    fun provideContext(application: Application): Context = application

    @Singleton @Provides @JvmStatic
    fun provideLessonRepository(
            lessonDatabase: LessonDatabase,
            restoreDatabase: RestoreDatabase
    ): LessonRepository =
            LessonDataRepository(lessonDatabase, restoreDatabase)
}