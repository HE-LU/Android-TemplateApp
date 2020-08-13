package cz.helu.core.arch

//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.pm.ActivityInfo
//import android.content.res.Configuration
//import android.graphics.Color
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.Settings
//import android.view.Menu
//import android.view.OrientationEventListener
//import android.view.View
//import androidx.annotation.ColorRes
//import androidx.annotation.LayoutRes
//import androidx.annotation.MenuRes
//import androidx.annotation.StringRes
//import androidx.annotation.StyleRes
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.content.res.AppCompatResources
//import androidx.appcompat.widget.Toolbar
//import androidx.core.content.ContextCompat
//import androidx.core.graphics.drawable.DrawableCompat
//import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentFactory
//import androidx.fragment.app.FragmentManager
//import com.google.android.material.snackbar.Snackbar
//import cz.helu.core.CoreConfig.ROTATION_THRESHOLD_ANGLE
//import cz.helu.core.database.AppPreferences
//import cz.helu.core.extension.doOnApplyWindowInsets
//import cz.helu.core.extension.getRootView
//import cz.helu.core.extension.instantiateFragment
//import cz.helu.core.extension.menu
//import cz.helu.core.theme.AccentColor
//import cz.helu.core.theme.AppThemeHandler
//import timber.log.Timber
//import java.util.Date
//import javax.inject.Inject
//
//const val ARG_INITIALIZER = "initializer"
//const val ARG_RESULT = "result"
//
//private const val DIALOG_PROGRESS = "dialog_progress"
//
//enum class NavigationType { NONE, UP, CLOSE }
//enum class NavigationBarStyle { WHITE, BLACK }
//enum class StatusBarStyle { WHITE, TRANSPARENT }
//
//@SuppressWarnings("TooManyFunctions")
//abstract class BaseActivity(
//    @LayoutRes internal val layoutResId: Int,
//    private val navigation: NavigationType = NavigationType.NONE,
//    private val statusBarStyle: StatusBarStyle = StatusBarStyle.WHITE,
//    private val navigationBarStyle: NavigationBarStyle = NavigationBarStyle.WHITE,
//    private val ignoreAccentTheme: Boolean = false
//) : AppCompatActivity(), BaseUIScreen {
//
//    override val baseActivity: BaseActivity get() = this
//    override val currentFragmentManager: FragmentManager get() = supportFragmentManager
//    override var lastSnackbar: Snackbar? = null
//
//    @Inject
//    lateinit var preferences: AppPreferences
//
//    @Inject
//    lateinit var customFragmentFactory: FragmentFactory
//
//    @Inject
//    lateinit var appThemeHandler: AppThemeHandler
//
//    private val toolbar get() = findViewById<Toolbar?>(R.id.toolbar)
//
//    private var preferredToolbarTitle: String? = null
//
//    private val orientationEventListener by lazy {
//        // For some retarded reason android implementation has SETTLE_TIME_MAX_MS = 200 and SETTLE_TIME_MAX_MS = 500 ms when rotating the device.
//        // Because of that we actually have to wait before changing the orientation back to Sensor at least for 200ms
//        object : OrientationEventListener(this) {
//            var enteredOrientationTimestamp = 0L
//
//            override fun onOrientationChanged(orientation: Int) {
//                val isInOrientation = when (requestedOrientation) {
//                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT -> {
//                        orientation < ROTATION_THRESHOLD_ANGLE ||
//                            orientation in (180 - ROTATION_THRESHOLD_ANGLE)..(180 + ROTATION_THRESHOLD_ANGLE) ||
//                            orientation > 360 - ROTATION_THRESHOLD_ANGLE
//                    }
//                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE -> {
//                        orientation in (90 - ROTATION_THRESHOLD_ANGLE)..(90 + ROTATION_THRESHOLD_ANGLE) ||
//                            orientation in (270 - ROTATION_THRESHOLD_ANGLE)..(270 + ROTATION_THRESHOLD_ANGLE)
//                    }
//                    else -> {
//                        disable()
//                        return
//                    }
//                }
//
//                when {
//                    !isInOrientation -> enteredOrientationTimestamp = 0L
//                    isInOrientation && enteredOrientationTimestamp == 0L -> enteredOrientationTimestamp = Date().time
//                    isInOrientation && Date().time - enteredOrientationTimestamp > 500 -> {
//                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
//                        disable()
//                    }
//                }
//            }
//        }
//    }
//
//    // Broadcast to recreate activity when theme gets changed.
//    private val themeBroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            recreate()
//        }
//    }
//
//    var latestSystemWindowInsetLeft = 0
//        private set
//    var latestSystemWindowInsetTop = 0
//        private set
//    var latestSystemWindowInsetRight = 0
//        private set
//    var latestSystemWindowInsetBottom = 0
//        private set
//
//    override fun setTheme(resId: Int) {
//        super.setTheme(resId)
//        setupWindow(resId)
//    }
//
//    public override fun onCreate(savedInstanceState: Bundle?) {
//        Timber.v(this.javaClass.name)
//
//        currentFragmentManager.fragmentFactory = customFragmentFactory
//
//        // Set preferred theme
//        if (!ignoreAccentTheme) setTheme(appThemeHandler.getAccentColor().theme)
//
//        super.onCreate(savedInstanceState)
//
//        appThemeHandler.registerReceiver(this, themeBroadcastReceiver)
//
//        setupView()
//        observeWindowInsets()
//    }
//
//    public override fun onNewIntent(intent: Intent) {
//        Timber.v(this.javaClass.name)
//        super.onNewIntent(intent)
//    }
//
//    public override fun onDestroy() {
//        Timber.v(this.javaClass.name)
//
//        dismissLastSnackbar()
//
//        appThemeHandler.unregisterReceiver(this, themeBroadcastReceiver)
//
//        super.onDestroy()
//    }
//
//    override fun finish() {
//        super<AppCompatActivity>.finish()
//    }
//
//    internal open fun setupView() {
//        setContentView(layoutResId)
//        setupToolbar()
//    }
//
//    fun ignoreAccentColorChange() {
//        appThemeHandler.unregisterReceiver(this, themeBroadcastReceiver)
//    }
//
//    fun updateToolbarTitle(toolbarTitle: String) {
//        this.preferredToolbarTitle = toolbarTitle
//        updateToolbarTitle()
//    }
//
//    fun updateToolbarTitle(@StringRes toolbarTitleRes: Int) {
//        this.preferredToolbarTitle = resources.getString(toolbarTitleRes)
//        updateToolbarTitle()
//    }
//
//    fun showToolbarMenu(@MenuRes resource: Int, action: Menu.() -> Unit) {
//        toolbar?.menu(resource, action)
//    }
//
//    fun showDialog(tag: String, dialog: () -> DialogFragment) {
//        // Remove this dialog if exists
//        removeDialog(tag)
//
//        // Show dialog
//        dialog().show(supportFragmentManager, tag)
//    }
//
//    fun showProgress() {
//        showDialog(DIALOG_PROGRESS) {
//            Dialog.progressDialog()
//        }
//    }
//
//    fun hideProgress() {
//        removeDialog(DIALOG_PROGRESS)
//    }
//
//    fun removeDialog(tag: String) {
//        supportFragmentManager.findFragmentByTag(tag)?.run {
//            supportFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
//        }
//    }
//
//    fun setNavigationBarStyle(color: NavigationBarStyle) {
//        when (color) {
//            NavigationBarStyle.BLACK -> setNavigationBarColor(R.color.color_navigation_bar_black)
//            NavigationBarStyle.WHITE -> setNavigationBarColor(R.color.color_navigation_bar)
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
//            if (isNightModeEnabled() || color == NavigationBarStyle.BLACK) {
//                clearDecorSystemUiVisibilityFlag(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
//            } else {
//                addDecorSystemUiVisibilityFlag(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
//            }
//        }
//    }
//
//    fun setStatusBarStyle(style: StatusBarStyle) {
//        when (style) {
//            StatusBarStyle.WHITE -> {
//                clearDecorSystemUiVisibilityFlag(getFullScreenFlags())
//                setStatusBarColor(R.color.color_status_bar)
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (isNightModeEnabled()) {
//                        clearDecorSystemUiVisibilityFlag(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//                    } else {
//                        addDecorSystemUiVisibilityFlag(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//                    }
//                }
//            }
//            StatusBarStyle.TRANSPARENT -> {
//                addDecorSystemUiVisibilityFlag(getFullScreenFlags())
//                setStatusBarColor(android.R.color.transparent)
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    clearDecorSystemUiVisibilityFlag(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//                }
//            }
//        }
//    }
//
//    fun setStatusBarAccentColor() = setStatusBarColor(appThemeHandler.getAccentColor())
//
//    fun setStatusBarColor(accentColor: AccentColor) {
//        setStatusBarColor(accentColor.toolbarColor)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            clearDecorSystemUiVisibilityFlag(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//        }
//    }
//
//    fun goLandscapeUntilRotated() {
//        changeOrientationUntillRotated(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
//    }
//
//    fun goPortraitUntilRotated() {
//        changeOrientationUntillRotated(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//    }
//
//    fun openUrl(url: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
//
//    inline fun <reified T : Fragment> instantiateFragment(): T = currentFragmentManager.instantiateFragment(classLoader)
//
//    @SuppressWarnings("ComplexMethod")
//    internal fun setupToolbar() {
//        val toolbarTitle = preferredToolbarTitle
//        if (toolbarTitle == null) {
//            toolbar?.title = packageManager.getActivityInfo(componentName, 0).loadLabel(packageManager)
//        } else {
//            toolbar?.title = toolbarTitle
//        }
//
//        val tintColor = when (statusBarStyle) {
//            StatusBarStyle.WHITE -> ContextCompat.getColor(this@BaseActivity, R.color.color_primary)
//            StatusBarStyle.TRANSPARENT -> Color.WHITE
//        }
//
//        when (navigation) {
//            NavigationType.NONE -> {
//            }
//            NavigationType.UP -> {
//                toolbar?.run {
//                    val drawable = AppCompatResources.getDrawable(this@BaseActivity, R.drawable.abc_ic_ab_back_material)
//                    if (drawable != null) {
//                        DrawableCompat.setTint(drawable, tintColor)
//                        navigationIcon = drawable
//                        setNavigationOnClickListener { onBackPressed() }
//                    }
//                }
//            }
//            NavigationType.CLOSE -> {
//                toolbar?.run {
//                    val drawable = AppCompatResources.getDrawable(this@BaseActivity, R.drawable.abc_ic_clear_material)
//                    if (drawable != null) {
//                        DrawableCompat.setTint(drawable, tintColor)
//                        navigationIcon = drawable
//                        setNavigationOnClickListener { onBackPressed() }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun updateToolbarTitle() {
//        toolbar?.title = preferredToolbarTitle
//    }
//
//    private fun setupWindow(@StyleRes themeId: Int) {
//        // Do nothing for Launcher theme. It would break it.
//        if (themeId != R.style.Cinnamon_Launcher) {
//            setNavigationBarStyle(navigationBarStyle)
//            setStatusBarStyle(statusBarStyle)
//        }
//    }
//
//    private fun isNightModeEnabled() =
//        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
//            Configuration.UI_MODE_NIGHT_YES -> true
//            Configuration.UI_MODE_NIGHT_NO -> false
//            else -> false
//        }
//
//    private fun addDecorSystemUiVisibilityFlag(add: Int) = setDecorSystemUiVisibilityFlag(add, add)
//
//    private fun clearDecorSystemUiVisibilityFlag(add: Int) = setDecorSystemUiVisibilityFlag(0, add)
//
//    private fun setDecorSystemUiVisibilityFlag(flags: Int, mask: Int) {
//        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and mask.inv() or (flags and mask)
//    }
//
//    private fun setNavigationBarColor(@ColorRes color: Int) {
//        window.apply {
//            navigationBarColor = ContextCompat.getColor(context, color)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                navigationBarDividerColor = ContextCompat.getColor(context, color)
//            }
//        }
//    }
//
//    private fun getFullScreenFlags() = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//
//    private fun setStatusBarColor(@ColorRes color: Int) {
//        window.statusBarColor = ContextCompat.getColor(this, color)
//    }
//
//    private fun changeOrientationUntillRotated(screenOrientation: Int) {
//        requestedOrientation = screenOrientation
//
//        try {
//            val autoRotate = Settings.System.getInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION) == 1
//            if (autoRotate || screenOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
//                orientationEventListener.enable()
//            }
//        } catch (e: Settings.SettingNotFoundException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun observeWindowInsets() {
//        getRootView().doOnApplyWindowInsets { _, insets, _ ->
//            latestSystemWindowInsetLeft = insets.systemWindowInsetLeft
//            latestSystemWindowInsetTop = insets.systemWindowInsetTop
//            latestSystemWindowInsetRight = insets.systemWindowInsetRight
//            latestSystemWindowInsetBottom = insets.systemWindowInsetBottom
//        }
//    }
//}