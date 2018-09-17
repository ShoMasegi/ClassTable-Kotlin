package masegi.sho.classtable.presentation.views.editmemo

import androidx.lifecycle.ViewModel
import androidx.appcompat.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import masegi.sho.classtable.di.ViewModelKey

/**
 * Created by masegi on 2018/04/03.
 */

@Module
interface EditMemoActivityModule {

    @Binds
    fun providesAppCompatActivity(activity: EditMemoActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeEditMemoFragment(): EditMemoFragment

    @Binds @IntoMap @ViewModelKey(EditMemoViewModel::class)
    fun bindEditMemoViewModel(editMemoViewModel: EditMemoViewModel): ViewModel
}
