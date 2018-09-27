package masegi.sho.classtable.presentation.views.edittable

import androidx.lifecycle.ViewModel
import androidx.appcompat.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import masegi.sho.classtable.di.ViewModelKey

/**
 * Created by masegi on 2018/04/24.
 */

@Module
interface EditTableActivityModule {

    @Binds
    fun provideAppCompatActivity(activity: EditTableActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeEditTableFrgagment(): EditTableFragment

    @Binds @IntoMap @ViewModelKey(EditTableViewModel::class)
    fun bindEditTableViewModel(editTableViewModel: EditTableViewModel): ViewModel
}
