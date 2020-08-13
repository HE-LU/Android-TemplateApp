package cz.helu.core.liveevent

import android.util.ArrayMap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * Rewritten to Kotlin from STRV Alfonz library (arch module):
 * https://github.com/petrnohejl/Alfonz/tree/master/alfonz-arch
 */
class LiveBus {
    private val eventMap: MutableMap<Class<out Event>, LiveEvent<out Event>> = ArrayMap()

    fun <T : Event> observe(owner: LifecycleOwner, eventClass: Class<T>, observer: Observer<T>) {
        val liveEvent = eventMap.getOrPut(eventClass) { LiveEvent<T>() } as LiveEvent<T>

        liveEvent.observeEvents(owner, observer)
    }

    fun <T : Event> send(event: T) {
        val liveEvent = eventMap.getOrPut(event.javaClass) { LiveEvent<T>() }

        liveEvent.value = event
    }
}