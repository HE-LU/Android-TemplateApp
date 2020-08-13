package cz.helu.core.utility

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.TypefaceSpan
import android.view.View

class CustomClickableSpan(val context: Context, val onClick: () -> Unit, val textColor: Int? = null) : ClickableSpan() {
    override fun onClick(p0: View) {
        onClick.invoke()
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.apply {
            textColor?.let { textColor ->
                color = textColor
            }

            isUnderlineText = true
        }
    }
}

class CustomTypefaceSpan(private val newTypeFace: Typeface) : TypefaceSpan("") {
    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, newTypeFace)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newTypeFace)
    }

    private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
        paint.typeface = tf
    }
}