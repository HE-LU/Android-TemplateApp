package cz.helu.core.utility

import android.content.res.ColorStateList

object ColorUtility {
    fun colorStateListOf(vararg mapping: Pair<IntArray, Int>): ColorStateList {
        val (states, colors) = mapping.unzip()
        return ColorStateList(states.toTypedArray(), colors.toIntArray())
    }
}