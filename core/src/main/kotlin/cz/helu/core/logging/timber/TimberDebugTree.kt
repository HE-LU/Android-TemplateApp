package cz.helu.core.logging.timber

import cz.helu.core.CoreConfig
import timber.log.Timber

class TimberDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return "${CoreConfig.APP_TAG}:(${element.fileName}:${element.lineNumber})#${element.methodName}"
    }
}
