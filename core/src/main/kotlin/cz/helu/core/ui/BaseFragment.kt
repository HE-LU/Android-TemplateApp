package cz.helu.core.arch

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import cz.helu.core.ui.BaseUIScreen

@Suppress("VariableNaming")
abstract class BaseFragment : Fragment(), BaseUIScreen {
    override val baseActivity: BaseActivity
        get() = activity as? BaseActivity ?: throw IllegalStateException("No activity in this fragment")

    override var lastSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        dismissLastSnackbar()
        super.onDestroyView()
    }

    /**
     * Serves only as shortcut to activity.finish()
     * WARNING: has to be final, otherwise children fragments could override and think that the code will be called on finish
     */
    final override fun finish() = super.finish()
}
