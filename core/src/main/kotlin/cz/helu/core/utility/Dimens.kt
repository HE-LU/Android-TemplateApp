package cz.helu.core.utility

import android.content.res.Resources

val Int.fromPxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.fromDpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
