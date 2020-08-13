package cz.helu.core.extension

import com.google.android.material.tabs.TabLayout

fun TabLayout.setTabSelectListener(
    onTabReselected: ((tab: TabLayout.Tab) -> Unit)? = null,
    onTabUnselected: ((tab: TabLayout.Tab) -> Unit)? = null,
    onTabSelected: ((tab: TabLayout.Tab) -> Unit)? = null
) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            if (tab != null) {
                onTabReselected?.invoke(tab)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            if (tab != null) {
                onTabUnselected?.invoke(tab)
            }
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (tab != null) {
                onTabSelected?.invoke(tab)
            }
        }
    })
}
