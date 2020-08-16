package cz.helu.core.ui

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import cz.helu.core.arch.BaseActivity
import cz.helu.core.arch.BaseView

@Suppress("TooManyFunctions", "LongParameterList")
interface BaseUIScreen : BaseView {
    val baseActivity: BaseActivity
    val currentFragmentManager: FragmentManager
    var lastSnackbar: Snackbar?

    fun getResources(): Resources
    fun getExtras(): Bundle? = baseActivity.intent.extras
    fun finish() = baseActivity.finish()

    fun showToast(@StringRes stringRes: Int) {
        showToast(baseActivity.getString(stringRes))
    }

    fun showToast(message: String) {
        Toast.makeText(baseActivity, message, Toast.LENGTH_LONG).show()
    }

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
