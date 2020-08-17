package cz.helu.core.ui

import android.content.res.Resources
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import cz.helu.core.arch.BaseView

@Suppress("TooManyFunctions", "LongParameterList")
interface BaseUIScreen : BaseView {
    var lastSnackbar: Snackbar?

    fun getResources(): Resources

    fun showSnackbar(
        view: View,
        @StringRes stringRes: Int,
        length: Int = Snackbar.LENGTH_LONG,
        anchor: View? = null,
        config: (Snackbar.() -> Unit)? = null
    ) {
        showSnackbar(view, view.context.getString(stringRes), length, anchor, config)
    }

    fun showSnackbar(
        view: View,
        message: String,
        length: Int = Snackbar.LENGTH_LONG,
        anchor: View? = null,
        config: (Snackbar.() -> Unit)? = null
    ) {
        Snackbar.make(view, message, length).apply { config?.invoke(this) }.apply {
            lastSnackbar?.dismiss()
            lastSnackbar = this

            if (anchor != null) {
                anchorView = anchor
            }

            show()
        }
    }

    fun dismissLastSnackbar() {
        lastSnackbar?.dismiss()
        lastSnackbar = null
    }
}
