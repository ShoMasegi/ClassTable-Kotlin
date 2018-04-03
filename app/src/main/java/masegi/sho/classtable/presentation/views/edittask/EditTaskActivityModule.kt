package masegi.sho.classtable.presentation.views.edittask

import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module

/**
 * Created by masegi on 2018/04/03.
 */

@Module
interface EditTaskActivityModule {

    @Binds
    fun providesAppCompatActivity(activity: EditTaskActivity): AppCompatActivity

}
