package cz.helu.core.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

inline fun <reified T : Fragment> FragmentManager.instantiateFragment(classLoader: ClassLoader): T =
    fragmentFactory.instantiate(classLoader, T::class.java.name) as T
