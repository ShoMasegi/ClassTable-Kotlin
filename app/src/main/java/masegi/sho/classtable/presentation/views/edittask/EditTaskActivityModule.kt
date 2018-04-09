package masegi.sho.classtable.presentation.views.edittask

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import masegi.sho.classtable.di.ViewModelKey

/**
 * Created by masegi on 2018/04/03.
 */

@Module
interface EditTaskActivityModule {

    @Binds
    fun providesAppCompatActivity(activity: EditTaskActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeEditTaskFragment(): EditTaskFragment

    @Binds @IntoMap @ViewModelKey(EditTaskViewModel::class)
    fun bindEditViewModel(editTaskViewModel: EditTaskViewModel): ViewModel
}
