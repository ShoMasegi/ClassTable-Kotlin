package masegi.sho.classtable.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by masegi on 2018/03/12.
 */

@MustBeDocumented
@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey internal annotation class ViewModelKey(val value: KClass<out ViewModel>)