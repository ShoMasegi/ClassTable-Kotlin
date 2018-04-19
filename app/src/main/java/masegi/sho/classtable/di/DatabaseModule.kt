package masegi.sho.classtable.di

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import dagger.Module
import dagger.Provides
import masegi.sho.classtable.data.db.*
import masegi.sho.classtable.data.db.dao.LessonDao
import masegi.sho.classtable.data.db.dao.MemoDao
import masegi.sho.classtable.data.db.dao.PrefDao
import masegi.sho.classtable.data.db.dao.TaskDao
import masegi.sho.classtable.data.model.Memo
import javax.inject.Singleton

/**
 * Created by masegi on 2018/03/07.
 */

@Module open class DatabaseModule {

    companion object {

        val instance = DatabaseModule()
    }

    @Singleton @Provides
    fun provideLessonDatabase(
            appDatabase: AppDatabase,
            lessonDao: LessonDao
    ): LessonDatabase = LessonRoomDatabase(lessonDao)

    @Singleton @Provides
    fun provideRestoreDatabase(
            taskDao: TaskDao,
            memoDao: MemoDao
    ): RestoreDatabase = RestoreRoomDatabase(taskDao, memoDao)

    @Singleton @Provides
    fun providePrefDatabase(
            prefDao: PrefDao
    ): PrefDatabase = PrefRoomDatabase(prefDao)

    @Singleton @Provides
    fun provideDb(app: Application): AppDatabase =
            Room.databaseBuilder(app, AppDatabase::class.java, "classtable.db")
                .fallbackToDestructiveMigration()
                .build()

    @Singleton @Provides
    fun provideLessonDao(db: AppDatabase): LessonDao = db.lessonDao()

    @Singleton @Provides
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()

    @Singleton @Provides
    fun provideMemoDao(db: AppDatabase): MemoDao = db.memoDao()

    @Singleton @Provides
    fun providePrefDao(db: AppDatabase): PrefDao = db.prefDao()
}