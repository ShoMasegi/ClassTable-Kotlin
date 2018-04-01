package masegi.sho.classtable.presentation.views.detail

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import masegi.sho.classtable.di.ViewModelKey

/**
 * Created by masegi on 2018/04/02.
 */

@Module
interface DetailActivityModule {

    @Binds
    fun providesAppCompatActivity(activity: DetailActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeDetailFragment(): DetailFragment

    @Binds @IntoMap @ViewModelKey(DetailViewModel::class)
    fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel
}
