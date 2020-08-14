package cz.helu.debugtools.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.helu.core.arch.BaseViewModel

class DebugToolsViewModel : BaseViewModel() {
    private val _kappaImageUrl = MutableLiveData<String>("")
    val kappaImageUrl: LiveData<String> = _kappaImageUrl

    init {
        _kappaImageUrl.value =
            "https://img.pngio.com/hd-transparent-kappa-oc-kappa-kappa-transparent-background-768_768.png"
    }
}
