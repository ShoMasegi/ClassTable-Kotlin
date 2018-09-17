package masegi.sho.classtable.presentation.views.setting

import androidx.lifecycle.ViewModel
import androidx.appcompat.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import masegi.sho.classtable.di.ViewModelKey

/**
 * Created by masegi on 2018/04/16.
 */

@Module
interface SettingActivityModule {

    @Binds
    fun providesAppCompatActivity(activity: SettingActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeSettingFragment(): SettingFragment

    @Binds @IntoMap @ViewModelKey(SettingViewModel::class)
    fun bindSettingViewmodel(settingViewModel: SettingViewModel): ViewModel
}
