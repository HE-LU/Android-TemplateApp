package cz.helu.core.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

/**
 * Return true if this [Context] is available.
 * Availability is defined as the following:
 * + [Context] is not null
 * + [Context] is not destroyed (tested with [FragmentActivity.isDestroyed] or [Activity.isDestroyed])
 * + [Context] is not finishing (tested with [FragmentActivity.isFinishing] or [Activity.isFinishing])
 */
fun Context?.isAvailable(): Boolean {
    return if (this == null) {
        false
    } else if (this !is Application) {
        if (this is FragmentActivity) {
            !(this.isDestroyed || this.isFinishing)
        } else if (this is Activity) {
            !(this.isDestroyed || this.isFinishing)
        } else true
    } else true
}

@SuppressLint("HardwareIds")
fun Context.deviceID(): String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID) ?: "NoAndroidId"

val Context.notificationManager: NotificationManager get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

inline val Context.inputMethodManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
