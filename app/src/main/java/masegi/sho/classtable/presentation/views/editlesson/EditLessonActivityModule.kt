package masegi.sho.classtable.presentation.views.editlesson

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import masegi.sho.classtable.di.ViewModelKey

/**
 * Created by masegi on 2018/03/20.
 */

@Module
interface EditLessonActivityModule {

    @Binds
    fun providesAppCompatActivity(activity: EditLessonActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeEditLessonFragment(): EditLessonFragment

    @Binds @IntoMap @ViewModelKey(EditLessonViewModel::class)
    fun bindEditLessonViewMoel(editLessonViewModel: EditLessonViewModel): ViewModel
}
