package cz.helu.core.extension

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar

inline fun Toolbar.menu(@MenuRes resource: Int, crossinline action: Menu.() -> Unit) {
    menu.clear()
    inflateMenu(resource)
    menu.action()
}

inline fun Menu.item(@IdRes id: Int, action: MenuItem.() -> Unit): MenuItem = findItem(id).apply(action)

fun Menu.onItemClick(@IdRes id: Int, action: (MenuItem) -> Unit) =
    findItem(id).setOnMenuItemClickListener { action(it); true }