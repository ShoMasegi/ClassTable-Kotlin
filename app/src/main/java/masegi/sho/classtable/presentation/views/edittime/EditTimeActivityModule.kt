package masegi.sho.classtable.presentation.views.edittime

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import masegi.sho.classtable.di.ViewModelKey

/**
 * Created by masegi on 2018/04/26.
 */

@Module
interface EditTimeActivityModule {

    @Binds
    fun providesAppCompatActivity(activity: EditTimeActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeEditTimeFragment(): EditTimeFragment

    @Binds @IntoMap @ViewModelKey(EditTimeViewModel::class)
    fun bindEditTimeViewMoel(editTimeViewModel: EditTimeViewModel): ViewModel
}