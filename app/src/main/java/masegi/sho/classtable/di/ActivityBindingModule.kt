package masegi.sho.classtable.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import masegi.sho.classtable.presentation.views.detail.DetailActivity
import masegi.sho.classtable.presentation.views.detail.DetailActivityModule
import masegi.sho.classtable.presentation.views.editlesson.EditLessonActivity
import masegi.sho.classtable.presentation.views.editlesson.EditLessonActivityModule
import masegi.sho.classtable.presentation.views.editmemo.EditMemoActivity
import masegi.sho.classtable.presentation.views.editmemo.EditMemoActivityModule
import masegi.sho.classtable.presentation.views.edittable.EditTableActivity
import masegi.sho.classtable.presentation.views.edittable.EditTableActivityModule
import masegi.sho.classtable.presentation.views.edittask.EditTaskActivity
import masegi.sho.classtable.presentation.views.edittask.EditTaskActivityModule
import masegi.sho.classtable.presentation.views.main.MainActivity
import masegi.sho.classtable.presentation.views.main.MainActivityModule
import masegi.sho.classtable.presentation.views.setting.SettingActivity
import masegi.sho.classtable.presentation.views.setting.SettingActivityModule

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

    @ContributesAndroidInjector(modules = [EditTaskActivityModule::class])
    fun contributeEditTaskActivity(): EditTaskActivity

    @ContributesAndroidInjector(modules = [EditMemoActivityModule::class])
    fun contributeEditMemoActivity(): EditMemoActivity

    @ContributesAndroidInjector(modules = [SettingActivityModule::class])
    fun contributeSettingActivity(): SettingActivity

    @ContributesAndroidInjector(modules = [EditTableActivityModule::class])
    fun contributeEditTableActivity(): EditTableActivity
}