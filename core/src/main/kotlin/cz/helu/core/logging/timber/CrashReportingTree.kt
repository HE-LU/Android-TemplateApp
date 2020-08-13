package cz.helu.core.logging.timber

import cz.helu.core.CoreConfig
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

private const val CALL_STACK_INDEX = 5

/** A tree which logs important information for crash reporting.  */
class CrashReportingTree : Timber.Tree() {
    @SuppressWarnings("SpreadOperator")
    override fun formatMessage(message: String, args: Array<Any>): String {
        return String.format(Locale.US, message, *args)
    }

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        val stackTrace = Throwable().stackTrace
        val customTag = if (stackTrace.size > CALL_STACK_INDEX) {
            val element = stackTrace[CALL_STACK_INDEX]
            "${CoreConfig.APP_TAG}:(${element.fileName}:${element.lineNumber})#${element.methodName}"
        } else {
            tag
        }

        val priorityString = logPriorityToString(priority)

        @Suppress("UNUSED_VARIABLE")
        val messageWithPrefix = "| $priorityString/$customTag: $message"

        // TODO: Sentry.addBreadcrumb(messageWithPrefix)

        if (throwable != null) {
            logCrashlyticsError(throwable)
        }
    }

    /**
     * Do not report some of the errors as non-fatal but only log them
     */
    private fun logCrashlyticsError(error: Throwable) {
        if (error.isIgnored || error.cause.isIgnored) {
            error.message?.let {
                // ToDo: LOG MESSAGE
            }
        } else {
            // ToDo: LOG EXCEPTION
        }
    }

    private val Throwable?.isIgnored
        get() = when (this) {
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException -> true
            else -> false
        }

    private fun logPriorityToString(priority: Int): String? {
        return when (priority) {
            2 -> "V"
            3 -> "D"
            4 -> "I"
            5 -> "W"
            6 -> "E"
            7 -> "A"
            else -> "?"
        }
    }
}