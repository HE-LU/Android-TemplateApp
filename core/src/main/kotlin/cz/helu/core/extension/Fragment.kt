package cz.helu.core.extension

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

fun Fragment.showDialog(tag: String, dialog: () -> DialogFragment) {
    // Remove this dialog if exists
    removeDialog(tag)

    // Show dialog
    dialog().show(childFragmentManager, tag)
}

fun Fragment.removeDialog(tag: String) {
    childFragmentManager.findFragmentByTag(tag)?.run {
        childFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
    }
}
