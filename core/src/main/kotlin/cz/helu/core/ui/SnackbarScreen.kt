package cz.helu.core.ui

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * Interface for screens that are capable of showing and dismissing snackbar.
 * Implementation stores lastSnackbar as a variable so we can access it and dismiss.
 */
interface SnackbarScreenDelegate {

    fun showSnackbar(
        view: View,
        @StringRes stringRes: Int,
        length: Int = Snackbar.LENGTH_LONG,
        anchor: View? = null,
        config: (Snackbar.() -> Unit)? = null
    )

    fun showSnackbar(
        view: View,
        message: String,
        length: Int = Snackbar.LENGTH_LONG,
        anchor: View? = null,
        config: (Snackbar.() -> Unit)? = null
    )

    fun dismissLastSnackbar()
}

class SnackbarScreen : SnackbarScreenDelegate {

    private var lastSnackbar: Snackbar? = null

    override fun showSnackbar(
        view: View,
        @StringRes stringRes: Int,
        length: Int,
        anchor: View?,
        config: (Snackbar.() -> Unit)?
    ) = showSnackbar(view, view.context.getString(stringRes), length, anchor, config)

    override fun showSnackbar(
        view: View,
        message: String,
        length: Int,
        anchor: View?,
        config: (Snackbar.() -> Unit)?
    ) {
        Snackbar.make(view, message, length).apply { config?.invoke(this) }.apply {
            lastSnackbar?.dismiss()
            lastSnackbar = this
            anchorView = anchor
        }.show()
    }

    override fun dismissLastSnackbar() {
        lastSnackbar?.dismiss()
        lastSnackbar = null
    }
}
