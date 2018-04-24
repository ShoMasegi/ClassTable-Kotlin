package masegi.sho.classtable.utli

import kotlin.collections.ArrayList
import kotlin.reflect.KProperty

/**
 * Created by masegi on 2018/04/24.
 */

class ResettableLazyManager {

    private val delegates = ArrayList<Resettable>()

    internal fun register(managed: Resettable) {

        synchronized(delegates) { delegates.add(managed) }
    }

    internal fun reset() {

        synchronized(delegates) {

            delegates.forEach { it.reset() }
            delegates.clear()
        }
    }
}

interface Resettable {
    fun reset()
}

class ResettableLazy<T>(
        private val manager: ResettableLazyManager,
        private val init: () -> T
) : Resettable {

    @Volatile var lazyHolder = makeInitBlock()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {

        return lazyHolder.value
    }

    override fun reset() {

        lazyHolder = makeInitBlock()
    }

    private fun makeInitBlock(): Lazy<T> {

        return lazy {
            manager.register(this)
            init()
        }
    }
}
