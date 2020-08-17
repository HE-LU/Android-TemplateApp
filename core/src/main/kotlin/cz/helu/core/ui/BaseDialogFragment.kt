package cz.helu.core.arch

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import cz.helu.core.ui.SnackbarScreen
import cz.helu.core.ui.SnackbarScreenDelegate

@Suppress("VariableNaming")
abstract class BaseDialogFragment : DialogFragment(), SnackbarScreenDelegate by SnackbarScreen() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        dismissLastSnackbar()
        super.onDestroyView()
    }

}
