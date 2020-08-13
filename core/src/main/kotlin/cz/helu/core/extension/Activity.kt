package cz.helu.core.extension

import android.app.Activity
import android.graphics.Rect
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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