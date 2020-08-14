package cz.helu.core.extension

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.materialDialog(setup: MaterialAlertDialogBuilder.() -> Unit): AlertDialog =
    MaterialAlertDialogBuilder(this).apply { setup() }.create()

inline var MaterialAlertDialogBuilder.title: String
    get() = throw UnsupportedOperationException("")
    set(value) {
        setTitle(value)
    }

inline var MaterialAlertDialogBuilder.message: String
    get() = throw UnsupportedOperationException("")
    set(value) {
        setMessage(value)
    }

inline var MaterialAlertDialogBuilder.view: View
    get() = throw UnsupportedOperationException("")
    set(value) {
        setView(value)
    }

inline fun MaterialAlertDialogBuilder.positiveButton(show: Boolean = true, setup: PositiveButton.() -> Unit) {
    if (show) {
        val positiveButton = PositiveButton().apply(setup)
        setPositiveButton(positiveButton.text, positiveButton.listener)
    }
}

inline fun MaterialAlertDialogBuilder.negativeButton(show: Boolean = true, setup: NegativeButton.() -> Unit) {
    if (show) {
        val negativeButton = NegativeButton().apply(setup)
        setNegativeButton(negativeButton.text, negativeButton.listener)
    }
}

inline fun MaterialAlertDialogBuilder.neutralButton(show: Boolean = true, setup: NeutralButton.() -> Unit) {
    if (show) {
        val neutralButton = NeutralButton().apply(setup)
        setNeutralButton(neutralButton.text, neutralButton.listener)
    }
}

class PositiveButton(
    @StringRes var text: Int = 0,
    var listener: DialogInterface.OnClickListener? = null
)

class NegativeButton(
    @StringRes var text: Int = 0,
    var listener: DialogInterface.OnClickListener? = null
)

class NeutralButton(
    @StringRes var text: Int = 0,
    var listener: DialogInterface.OnClickListener? = null
)

fun DialogFragment.hideKeyboard(view: View) =
    (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run {
        hideSoftInputFromWindow(view.windowToken, 0)
    }
