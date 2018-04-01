package masegi.sho.classtable.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import masegi.sho.classtable.presentation.views.detail.DetailActivity
import masegi.sho.classtable.presentation.views.detail.DetailActivityModule
import masegi.sho.classtable.presentation.views.editlesson.EditLessonActivity
import masegi.sho.classtable.presentation.views.editlesson.EditLessonActivityModule
import masegi.sho.classtable.presentation.views.main.MainActivity
import masegi.sho.classtable.presentation.views.main.MainActivityModule

/**
 * Created by masegi on 2018/03/12.
 */

@Module
interface ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [EditLessonActivityModule::class])
    fun contributeEditLessonActivity(): EditLessonActivity

    @ContributesAndroidInjector(modules = [DetailActivityModule::class])
    fun contributeDetailActivity(): DetailActivity

}