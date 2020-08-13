package cz.helu.core.arch

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import cz.helu.core.liveevent.Event
import cz.helu.core.liveevent.LiveBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private val liveEventsBus = LiveBus()
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        Timber.v(this.javaClass.name)
    }

    override fun onCleared() {
        job.cancel()
        Timber.v(this.javaClass.name)
        super.onCleared()
    }

    fun sendEvent(event: Event) {
        liveEventsBus.send(event)
    }

    fun <T : Event> observeEvent(owner: LifecycleOwner, eventClass: Class<T>, observer: Observer<T>) {
        liveEventsBus.observe(owner, eventClass, observer)
    }
}