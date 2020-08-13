package cz.helu.core.binding

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import cz.helu.core.utility.CustomClickableSpan

@BindingAdapter("textRes")
fun TextView.setTextRes(stringRes: Int?) {
    val newText = if (stringRes != null && stringRes != 0) context.getString(stringRes) else ""
    val oldText = text

    // save layout passes, should apply to all other
    if (haveContentsChanged(newText, oldText)) {
        text = newText
    }
}

@BindingAdapter(value = ["spannableText", "links"], requireAll = true)
fun TextView.setSpannableLinks(spannable: String, links: HashMap<Int, () -> Unit>) {
    val span = SpannableStringBuilder(spannable)

    for ((link, callback) in links) {
        val customSpan = CustomClickableSpan(context, callback, currentTextColor)
        val linkSpan = resources.getString(link)
        val startLink = spannable.indexOf(linkSpan)
        val endLink = startLink + linkSpan.length

        if (startLink >= 0) span.setSpan(customSpan, startLink, endLink, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    text = span
}

@BindingAdapter(value = ["scrollableText", "scrollableTextDelayMs"], requireAll = false)
fun TextView.setScrollableText(text: String? = null, delayMs: Long? = null) {
    setText(text)

    setSingleLine()
    ellipsize = TextUtils.TruncateAt.MARQUEE
    marqueeRepeatLimit = -1 // Forever
    setHorizontallyScrolling(true)

    // View must be selected for marquee to work
    if (delayMs == null) {
        isSelected = true
    } else {
        // Let's use attach listener for this, as the view can be part of recyclerview,
        // and can be swiped away from the screen. This solves those cases.
        isSelected = false
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            val runnable = Runnable { this@setScrollableText.isSelected = true }

            override fun onViewDetachedFromWindow(v: View?) {
                removeCallbacks(runnable)
            }

            override fun onViewAttachedToWindow(v: View?) {
                isSelected = false
                postDelayed(runnable, delayMs)
            }
        })
    }
}

@SuppressWarnings("ReturnCount")
private fun haveContentsChanged(str1: CharSequence?, str2: CharSequence?): Boolean {
    if (str1 == null != (str2 == null)) {
        return true
    } else if (str1 == null) {
        return false
    }
    val length = str1.length
    if (length != str2!!.length) {
        return true
    }
    for (i in 0 until length) {
        if (str1[i] != str2[i]) {
            return true
        }
    }
    return false
}
