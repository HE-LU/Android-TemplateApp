package cz.helu.templateapp

import android.app.Application
import cz.helu.core.CoreConfig
import cz.helu.core.logging.timber.CrashReportingTree
import cz.helu.core.logging.timber.TimberDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TemplateAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    @Suppress("ConstantConditionIf")
    private fun initTimber() {
        if (CoreConfig.RELEASE_BUILD_TYPE) {
            // Report crashes for productionRelease
            Timber.plant(CrashReportingTree())

            if (!CoreConfig.PRODUCTION_FLAVOR) {
                // Log to console for all the flavors except Production
                Timber.plant(TimberDebugTree())
            }
        } else {
            // Log to console for debug builds
            Timber.plant(TimberDebugTree())
        }
    }
}
