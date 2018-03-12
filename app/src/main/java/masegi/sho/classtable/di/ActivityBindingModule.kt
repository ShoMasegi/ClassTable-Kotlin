package masegi.sho.classtable.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import masegi.sho.classtable.presentation.views.main.MainActivity
import masegi.sho.classtable.presentation.views.main.MainActivityModule

/**
 * Created by masegi on 2018/03/12.
 */

@Module
interface ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun contributeMainActivity(): MainActivity
}