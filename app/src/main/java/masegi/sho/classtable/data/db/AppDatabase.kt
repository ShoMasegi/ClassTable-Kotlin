package masegi.sho.classtable.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import masegi.sho.classtable.data.db.dao.LessonDao
import masegi.sho.classtable.data.db.dao.MemoDao
import masegi.sho.classtable.data.db.dao.PrefDao
import masegi.sho.classtable.data.db.dao.TaskDao
import masegi.sho.classtable.data.db.mapper.Converters
import masegi.sho.classtable.data.model.Memo
import masegi.sho.classtable.data.model.PrefEntity
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.kotlin.data.model.Task

/**
 * Created by masegi on 2018/03/08.
 */

@Database(
        entities = [
            Lesson::class,
            PrefEntity::class,
            Task::class,
            Memo::class
        ],
        version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lessonDao(): LessonDao
    abstract fun memoDao(): MemoDao
    abstract fun prefDao(): PrefDao
    abstract fun taskDao(): TaskDao
}

