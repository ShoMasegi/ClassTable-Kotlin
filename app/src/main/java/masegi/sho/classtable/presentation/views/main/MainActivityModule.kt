package masegi.sho.classtable.presentation.views.main

import androidx.lifecycle.ViewModel
import androidx.appcompat.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import masegi.sho.classtable.di.ViewModelKey
import masegi.sho.classtable.presentation.views.main.home.HomeFragment
import masegi.sho.classtable.presentation.views.main.home.HomeViewModel
import masegi.sho.classtable.presentation.views.main.today.LessonListFragment
import masegi.sho.classtable.presentation.views.main.today.LessonListViewModel
import masegi.sho.classtable.presentation.views.main.today.TodayFragment
import masegi.sho.classtable.presentation.views.main.today.TodayViewModel
import masegi.sho.classtable.presentation.views.main.todo.TodoFragment
import masegi.sho.classtable.presentation.views.main.todo.TodoViewModel

/**
 * Created by masegi on 2018/03/12.
 */

@Module
interface MainActivityModule {

    @Binds
    fun providesAppCompatActivity(activity: MainActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    fun contributeTodoFragment(): TodoFragment

    @ContributesAndroidInjector
    fun contributeTodayFragment(): TodayFragment

    @ContributesAndroidInjector
    fun contributeLessonListFragment(): LessonListFragment

    @Binds @IntoMap @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(TodayViewModel::class)
    fun bindTodayViewModel(todayViewModel: TodayViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(TodoViewModel::class)
    fun bindTodoViewmodel(todoViewModel: TodoViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(LessonListViewModel::class)
    fun bindLessonListViewModel(lessonListViewModel: LessonListViewModel): ViewModel
}
