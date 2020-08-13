package cz.helu.core.extension

import timber.log.Timber

fun Throwable.logError() {
    Timber.e(this)
}