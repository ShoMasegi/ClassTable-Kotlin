package masegi.sho.classtable.presentation.views.editmemo

import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module

/**
 * Created by masegi on 2018/04/03.
 */

@Module
interface EditMemoActivityModule {

    @Binds
    fun providesAppCompatActivity(activity: EditMemoActivity): AppCompatActivity
}
