package cz.helu.core.ui

//import android.view.LayoutInflater
//import android.view.View
//import androidx.annotation.CallSuper
//import androidx.annotation.LayoutRes
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.Observer
//import cz.helu.core.arch.BaseViewModel
//import cz.helu.core.extension.hideKeyboard
//import cz.helu.core.liveevent.Event
//import cz.helu.core.BR
//
//interface ViewModelBinder<V : BaseViewModel, B : ViewDataBinding> : LifecycleOwner, BaseUIScreen {
//    val binding: B
//    val viewModel: V
//
//    fun getViewLifecycleOwner(): LifecycleOwner
//
//    fun observe()
//
//    fun setupBinding(layoutInflater: LayoutInflater, @LayoutRes layoutResId: Int): B {
//        val resultBinding: B = DataBindingUtil.inflate(layoutInflater, layoutResId, null, false)
//
//        resultBinding.apply {
//            lifecycleOwner = getViewLifecycleOwner()
//            setVariable(BR.view, this@ViewModelBinder)
//            setVariable(BR.viewModel, viewModel)
//        }
//
//        return resultBinding
//    }
//
//    @CallSuper
//    fun observeBaseEvents() {
//        observeEvent<HideKeyboard> { baseActivity.hideKeyboard() }
//        observeEvent<Finish> { finish() }
//        observeShowEvents(binding.root)
//
//        observeEvent<ShowProgress> { baseActivity.showProgress() }
//        observeEvent<HideProgress> { baseActivity.hideProgress() }
//    }
//
//    /**
//     * Specified this way, so that children may override which view will be used to show the snackbar
//     * @param view to show snackbar
//     */
//    fun observeShowEvents(view: View) {
//        observeEvent<ShowError> { errorEvent ->
//            val message = when {
//                errorEvent.message != null -> errorEvent.message
//                errorEvent.messageResId != null -> view.context.getString(errorEvent.messageResId)
//                else -> throw IllegalArgumentException("One of 'message' or 'messageResId is required!")
//            }
//
//            showSnackbar(view, message, errorEvent.length) {
//                errorEvent.action?.let { (actionName, action) ->
//                    this@showSnackbar.setAction(actionName) { action(); lastSnackbar = null }
//                }
//            }
//        }
//    }
//}
//
///**
// * This way because interface can't have inline function :(
// * @param lifecycleOwner to specify whether using whole Lifecycle owner or ViewLifecycleOwner
// */
//inline fun <reified T : Event> ViewModelBinder<*, *>.observeEvent(
//    lifecycleOwner: LifecycleOwner = getViewLifecycleOwner(),
//    crossinline action: (T) -> Unit
//) {
//    viewModel.observeEvent(lifecycleOwner, T::class.java, Observer { action.invoke(it) })
//}