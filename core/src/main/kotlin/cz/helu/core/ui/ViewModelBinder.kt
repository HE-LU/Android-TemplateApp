package cz.helu.core.ui

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import cz.helu.core.BR
import cz.helu.core.arch.BaseViewModel
import cz.helu.core.liveevent.Event
import cz.helu.core.liveevent.ShowError

interface ViewModelBinder<V : BaseViewModel, B : ViewDataBinding> : LifecycleOwner {
    val binding: B
    val viewModel: V

    fun getViewLifecycleOwner(): LifecycleOwner
    fun requireContext(): Context

    fun observe()

    fun setupBinding(layoutInflater: LayoutInflater, @LayoutRes layoutResId: Int): B =
        DataBindingUtil.inflate<B>(layoutInflater, layoutResId, null, false).apply {
            lifecycleOwner = getViewLifecycleOwner()
            setVariable(BR.viewModel, viewModel)
        }

    @CallSuper
    fun observeBaseEvents() {
        observeShowEvents()
    }

    fun showEvent(
        message: String,
        length: Int,
        config: (Snackbar.() -> Unit)
    )

    fun observeShowEvents() {
        observeEvent<ShowError> { errorEvent ->
            val message = when {
                errorEvent.message != null -> errorEvent.message
                errorEvent.messageResId != null -> requireContext().getString(errorEvent.messageResId)
                else -> throw IllegalArgumentException("One of 'message' or 'messageResId is required!")
            }

            showEvent(message, errorEvent.length) {
                errorEvent.action?.let { (actionName, action) ->
                    setAction(actionName) { action() }
                }
            }
        }
    }
}

/**
 * This way because interface can't have inline function :(
 * @param lifecycleOwner to specify whether using whole Lifecycle owner or ViewLifecycleOwner
 */
inline fun <reified T : Event> ViewModelBinder<*, *>.observeEvent(
    lifecycleOwner: LifecycleOwner = getViewLifecycleOwner(),
    crossinline action: (T) -> Unit
) {
    viewModel.observeEvent(lifecycleOwner, T::class.java, Observer { action.invoke(it) })
}
