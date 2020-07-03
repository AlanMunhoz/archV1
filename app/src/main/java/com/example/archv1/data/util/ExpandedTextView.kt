package com.example.archv1.data.util

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.archv1.R

class ExpandedTextView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    private val DEFAULT_TRIM_LENGTH = 120
    private val SHOW_MORE = "Ver mais"
    private val SHOW_LESS = "Ver menos"
    private var fullText: CharSequence
    private var trimmedText: CharSequence
    private var isExpanded = false
    private val spannableStringMore: SpannableString by lazy { buildSpannableString() }
    private val spannableStringLess: SpannableString by lazy { buildSpannableString() }
    private val currentText: CharSequence
        get() = if (isExpanded) fullText else trimmedText

    init {
        fullText = getFullText(text)
        trimmedText = getTrimmedText(text)
        setTextView()
    }

    private fun setTextView() {
        val spannableString = if (isExpanded) spannableStringLess else spannableStringMore
        setText(spannableString, BufferType.SPANNABLE)
        isExpanded = isExpanded.not()
    }

    private fun isExpandable(text: CharSequence) = text.length > DEFAULT_TRIM_LENGTH

    private fun getTrimmedText(text: CharSequence) = if (isExpandable(text)) {
        SpannableStringBuilder(text, 0, DEFAULT_TRIM_LENGTH).append("\n$SHOW_MORE")
    } else {
        text
    }

    private fun getFullText(text: CharSequence) = if (isExpandable(text)) {
        SpannableStringBuilder(text).append("\n$SHOW_LESS")
    } else {
        text
    }

    private fun buildSpannableString(): SpannableString {
        return SpannableString(currentText).apply {
            if (isExpandable(currentText)) {
                val link = if (isExpanded) SHOW_LESS else SHOW_MORE
                val startLink = currentText.length - link.length
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(view: View) {
                        setTextView()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }
                }
                setSpan(
                    clickableSpan,
                    startLink,
                    startLink + link.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, R.color.blue_500)),
                    startLink,
                    startLink + link.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    startLink,
                    currentText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }
}
