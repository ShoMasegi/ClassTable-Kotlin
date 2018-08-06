package masegi.sho.classtable.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import masegi.sho.classtable.presentation.common.location.LocationService

@Module
interface ServiceBuilder {
    @ContributesAndroidInjector()
    fun contributeService(): LocationService
}
