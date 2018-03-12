package masegi.sho.classtable.di

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * Created by masegi on 2018/03/12.
 */

@Module
interface ViewModelModule {

    @Binds fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
