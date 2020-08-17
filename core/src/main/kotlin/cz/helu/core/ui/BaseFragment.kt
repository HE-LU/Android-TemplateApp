package cz.helu.core.arch

import android.os.Bundle
import androidx.fragment.app.Fragment
import cz.helu.core.ui.SnackbarScreen
import cz.helu.core.ui.SnackbarScreenDelegate

abstract class BaseFragment : Fragment(), SnackbarScreenDelegate by SnackbarScreen() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        dismissLastSnackbar()
        super.onDestroyView()
    }

}
