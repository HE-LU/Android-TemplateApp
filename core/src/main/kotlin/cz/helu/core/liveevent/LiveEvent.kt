package cz.helu.core.liveevent

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Rewritten to Kotlin from STRV Alfonz library (arch module):
 * https://github.com/petrnohejl/Alfonz/tree/master/alfonz-arch
 */
class LiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    fun observeEvents(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        // observe the internal MutableLiveData
        super.observe(lifecycleOwner, Observer<T> { value ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(value)
            }
        })
    }

    @MainThread
    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }

    @MainThread
    fun call() {
        value = null
    }
}