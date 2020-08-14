package cz.helu.templateapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.helu.core.arch.BaseViewModel

class HomeViewModel : BaseViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}
