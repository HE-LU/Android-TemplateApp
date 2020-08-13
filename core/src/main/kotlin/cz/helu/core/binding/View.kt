package cz.helu.core.binding

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.transition.TransitionManager
import cz.helu.core.extension.doOnApplyWindowInsets

@BindingAdapter("show", "animate", requireAll = false)
fun View.setShow(show: Boolean, animate: Boolean?) {
    if (animate != null && animate && parent is ViewGroup)
        TransitionManager.beginDelayedTransition(parent as ViewGroup)
    visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("visible", "animate", requireAll = false)
fun View.setVisible(show: Boolean, animate: Boolean?) {
    if (animate != null && animate && parent is ViewGroup)
        TransitionManager.beginDelayedTransition(parent as ViewGroup)
    visibility = if (show) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("invisible", "animate", requireAll = false)
fun View.setInvisible(invisible: Boolean, animate: Boolean?) {
    if (animate != null && animate && parent is ViewGroup)
        TransitionManager.beginDelayedTransition(parent as ViewGroup)
    visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter(
    "leftSystemWindowInsets",
    "topSystemWindowInsets",
    "rightSystemWindowInsets",
    "bottomSystemWindowInsets",
    requireAll = false
)
fun View.fitSystemWindowInset(applyLeft: Boolean, applyTop: Boolean, applyRight: Boolean, applyBottom: Boolean) {
    doOnApplyWindowInsets { view, insets, padding ->
        val left = if (applyLeft) insets.systemWindowInsetLeft else 0
        val top = if (applyTop) insets.systemWindowInsetTop else 0
        val right = if (applyRight) insets.systemWindowInsetRight else 0
        val bottom = if (applyBottom) insets.systemWindowInsetBottom else 0

        view.setPadding(padding.left + left, padding.top + top, padding.right + right, padding.bottom + bottom)
    }
}

@BindingAdapter("fitSystemWindowInsets")
fun View.fitSystemWindowInset(fit: Boolean) {
    fitSystemWindowInset(fit, fit, fit, fit)
}

@BindingAdapter("backgroundColor")
fun View.setBackgroundColorBinding(@ColorRes color: Int) {
    setBackgroundColor(ContextCompat.getColor(context, color))
}

@BindingAdapter("backgroundTint")
fun View.setBackgroundTint(@ColorRes color: Int) {
    backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, color))
}
