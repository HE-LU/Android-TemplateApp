package cz.helu.core.liveevent

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

class ShowError(
    val message: String? = null,
    @StringRes val messageResId: Int? = null,
    val action: (Pair<Int, () -> Unit>)? = null,
    val length: Int = Snackbar.LENGTH_LONG
) : Event()
