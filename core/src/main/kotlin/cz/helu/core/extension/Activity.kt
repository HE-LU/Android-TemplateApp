package cz.helu.core.extension

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import cz.helu.core.utility.fromDpToPx

fun Activity.getRootView(): View {
    return findViewById(android.R.id.content)
}

fun AppCompatActivity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    val marginOfError = 50.fromDpToPx
    return heightDiff > marginOfError
}

fun AppCompatActivity.getKeyboardTop(): Int {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    return visibleBounds.bottom
}

fun Activity.showKeyboard() {
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

fun Activity.hideKeyboard() {
    inputMethodManager.hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
}

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, observer: (T) -> Unit) =
    liveData.observe(this, Observer(observer))

fun AppCompatActivity.showDialog(tag: String, dialog: () -> DialogFragment) {
    // Remove this dialog if exists
    removeDialog(tag)

    // Show dialog
    dialog().show(supportFragmentManager, tag)
}

fun AppCompatActivity.removeDialog(tag: String) {
    supportFragmentManager.findFragmentByTag(tag)?.run {
        supportFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
    }
}
