package cz.helu.core.arch

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import cz.helu.core.R
import cz.helu.core.ui.BaseUIScreen

@Suppress("VariableNaming")
abstract class BaseFragment : Fragment(), BaseUIScreen {
    override var lastSnackbar: Snackbar? = null
    val requireToolbar: Toolbar get() = view?.findViewById(R.id.toolbar)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        dismissLastSnackbar()
        super.onDestroyView()
    }

    fun showDialog(tag: String, dialog: () -> DialogFragment) {
        // Remove this dialog if exists
        removeDialog(tag)

        // Show dialog
        dialog().show(childFragmentManager, tag)
    }

    fun removeDialog(tag: String) {
        childFragmentManager.findFragmentByTag(tag)?.run {
            childFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
        }
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
