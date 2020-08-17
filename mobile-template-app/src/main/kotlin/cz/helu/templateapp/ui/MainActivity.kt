package cz.helu.templateapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import cz.helu.templateapp.R

class MainActivity : AppCompatActivity() {
    private val toolbar get() = findViewById<Toolbar?>(R.id.toolbar)
    private val navigationBar get() = findViewById<BottomNavigationView?>(R.id.nav_view)
    private val navigationHostView get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupNavigation()
    }

    fun changeToolbarVisiblity(visible: Boolean) {
        if (visible)
            supportActionBar?.show()
        else
            supportActionBar?.hide()
    }

    fun changeNavigationBarVisiblity(visible: Boolean) {
        if (visible)
            navigationBar?.setVisibility(View.VISIBLE);
        else
            navigationBar?.setVisibility(View.GONE);
    }

    private fun setupNavigation() {
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_debug_tools
            )
        )

        setupActionBarWithNavController(navigationHostView.navController, appBarConfiguration)
        navigationBar?.setupWithNavController(navigationHostView.navController)
    }
}
