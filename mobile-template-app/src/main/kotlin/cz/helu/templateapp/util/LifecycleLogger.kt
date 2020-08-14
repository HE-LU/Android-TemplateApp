package cz.helu.templateapp.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class ActivityLifecycleLoggerCallbacks(
    val logger: (tag: String, method: String) -> Unit
) : Application.ActivityLifecycleCallbacks {

    private val fragmentCallbacks: FragmentLifecycleLoggerCallbacks = FragmentLifecycleLoggerCallbacks(logger)

    override fun onActivityCreated(a: Activity, savedInstanceState: Bundle?) {
        logger(a.javaClass.simpleName, "onCreate")

        (a as? FragmentActivity)
            ?.supportFragmentManager
            ?.registerFragmentLifecycleCallbacks(fragmentCallbacks, true)
    }

    override fun onActivityStarted(a: Activity) {
        logger(a.javaClass.simpleName, "onStart")
    }

    override fun onActivityResumed(a: Activity) {
        logger(a.javaClass.simpleName, "onResume")
    }

    override fun onActivityPaused(a: Activity) {
        logger(a.javaClass.simpleName, "onPause")
    }

    override fun onActivityStopped(a: Activity) {
        logger(a.javaClass.simpleName, "onStop")
    }

    override fun onActivityDestroyed(a: Activity) {
        logger(a.javaClass.simpleName, "onDestroy")
        (a as? FragmentActivity)
            ?.supportFragmentManager
            ?.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    override fun onActivitySaveInstanceState(a: Activity, savedInstanceState: Bundle?) {
        logger(a.javaClass.simpleName, "onSaveInstanceState")
    }
}

class FragmentLifecycleLoggerCallbacks(
    val logger: (tag: String, method: String) -> Unit
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        logger(f.javaClass.simpleName, "onAttach")
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        logger(f.javaClass.simpleName, "onCreate")
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
        logger(f.javaClass.simpleName, "onCreateView")
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState)
        logger(f.javaClass.simpleName, "onActivityCreated")
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        super.onFragmentStarted(fm, f)
        logger(f.javaClass.simpleName, "onStart")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        logger(f.javaClass.simpleName, "onResume")
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        logger(f.javaClass.simpleName, "onPause")
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        super.onFragmentStopped(fm, f)
        logger(f.javaClass.simpleName, "onStop")
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)
        logger(f.javaClass.simpleName, "onDestroyView")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        logger(f.javaClass.simpleName, "onDestroy")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fm, f)
        logger(f.javaClass.simpleName, "onDetach")
    }
}
