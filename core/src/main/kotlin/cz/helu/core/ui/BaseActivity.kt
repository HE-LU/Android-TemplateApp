package cz.helu.core.arch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import cz.helu.core.R
import cz.helu.core.extension.doOnApplyWindowInsets
import cz.helu.core.extension.getRootView
import cz.helu.core.extension.menu
import cz.helu.core.ui.BaseUIScreen
import timber.log.Timber

abstract class BaseActivity(
    @LayoutRes internal val layoutResId: Int
) : AppCompatActivity(), BaseUIScreen {
    override val baseActivity: BaseActivity get() = this
    override val currentFragmentManager: FragmentManager get() = supportFragmentManager
    override var lastSnackbar: Snackbar? = null

    var latestSystemWindowInsetLeft = 0
        private set
    var latestSystemWindowInsetTop = 0
        private set
    var latestSystemWindowInsetRight = 0
        private set
    var latestSystemWindowInsetBottom = 0
        private set

    private val toolbar get() = findViewById<Toolbar?>(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.v(this.javaClass.name)

        super.onCreate(savedInstanceState)

        setupView()
        observeWindowInsets()
    }

    override fun onNewIntent(intent: Intent) {
        Timber.v(this.javaClass.name)
        super.onNewIntent(intent)
    }

    override fun onDestroy() {
        Timber.v(this.javaClass.name)

        dismissLastSnackbar()

        super.onDestroy()
    }

    override fun finish() {
        super<AppCompatActivity>.finish()
    }

    internal open fun setupView() {
        setContentView(layoutResId)
        setSupportActionBar(toolbar)
    }

    fun showToolbarMenu(@MenuRes resource: Int, action: Menu.() -> Unit) {
        toolbar?.menu(resource, action)
    }

    fun showDialog(tag: String, dialog: () -> DialogFragment) {
        // Remove this dialog if exists
        removeDialog(tag)

        // Show dialog
        dialog().show(supportFragmentManager, tag)
    }

    fun removeDialog(tag: String) {
        supportFragmentManager.findFragmentByTag(tag)?.run {
            supportFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
        }
    }

    fun openUrl(url: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

    private fun observeWindowInsets() {
        getRootView().doOnApplyWindowInsets { _, insets, _ ->
            latestSystemWindowInsetLeft = insets.systemWindowInsetLeft
            latestSystemWindowInsetTop = insets.systemWindowInsetTop
            latestSystemWindowInsetRight = insets.systemWindowInsetRight
            latestSystemWindowInsetBottom = insets.systemWindowInsetBottom
        }
    }
}
