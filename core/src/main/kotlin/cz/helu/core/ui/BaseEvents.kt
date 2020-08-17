package cz.helu.core.ui

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import cz.helu.core.liveevent.Event

class ShowError(
    val message: String? = null,
    @StringRes val messageResId: Int? = null,
    val action: (Pair<Int, () -> Unit>)? = null,
    val length: Int = Snackbar.LENGTH_LONG
) : Event()
