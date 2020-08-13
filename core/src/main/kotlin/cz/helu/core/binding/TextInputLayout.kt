package cz.helu.core.binding

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun TextInputLayout.setErrorMessage(errorMessage: String?) {
    if (errorMessage == null) {
        this.error = null
    } else {
        this.error = errorMessage
    }
}

@BindingAdapter("errorRes")
fun TextInputLayout.setErrorResource(@StringRes errorResource: Int?) {
    if (errorResource == null) {
        this.error = null
    } else {
        this.error = this.resources.getText(errorResource)
    }
}