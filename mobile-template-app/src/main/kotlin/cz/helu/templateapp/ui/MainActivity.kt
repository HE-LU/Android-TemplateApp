package cz.helu.templateapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import cz.helu.templateapp.R

class MainActivity : AppCompatActivity() {
    private val navigationBar: BottomNavigationView get() = findViewById(R.id.nav_view)
    private val navigationHostView get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TemplateApp)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        navigationBar.setupWithNavController(navigationHostView.navController)
    }

    fun changeNavigationBarVisiblity(visible: Boolean) {
        if (visible)
            navigationBar.setVisibility(View.VISIBLE)
        else
            navigationBar.setVisibility(View.GONE)
    }
}
