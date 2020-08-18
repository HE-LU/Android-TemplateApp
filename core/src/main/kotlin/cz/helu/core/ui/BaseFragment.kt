package cz.helu.core.arch

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cz.helu.core.R
import cz.helu.core.ui.SnackbarScreen
import cz.helu.core.ui.SnackbarScreenDelegate

abstract class BaseFragment : Fragment(), SnackbarScreenDelegate by SnackbarScreen() {
    val requireToolbar: Toolbar get() = view?.findViewById(R.id.toolbar)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        dismissLastSnackbar()
        super.onDestroyView()
    }

    fun setupToolbar(@StringRes titleId: Int, homeAsUpEnabled: Boolean = false) {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(requireToolbar)
            if (homeAsUpEnabled)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        requireToolbar.apply {
            title = getString(titleId)
            setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }
    }
}
