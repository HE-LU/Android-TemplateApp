package cz.helu.core.extension

import android.text.TextWatcher
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

// @OptIn(ExperimentalCoroutinesApi::class)
fun TextInputEditText.textInputAsFlow() = callbackFlow {
    val watcher: TextWatcher = doOnTextChanged { textInput: CharSequence?, _, _, _ ->
        offer(textInput)
    }
    awaitClose { this@textInputAsFlow.removeTextChangedListener(watcher) }
}
