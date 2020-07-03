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

class ExpandableTextView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    private val TRIM_LENGTH = 120
    private val SHOW_MORE = context.getString(R.string.show_more)
    private val SHOW_LESS = context.getString(R.string.show_less)
    private var originalText: CharSequence
    private var isExpanded = false
    private var spannableStringMore: SpannableString
    private var spannableStringLess: SpannableString

    private val isExpandable: Boolean
        get() = originalText.length > TRIM_LENGTH

    private val fullText: CharSequence
        get() = if (isExpandable) {
            SpannableStringBuilder(originalText).append("\n$SHOW_LESS")
        } else {
            originalText
        }

    private val trimmedText: CharSequence
        get() = if (isExpandable) {
            SpannableStringBuilder(originalText, 0, TRIM_LENGTH).append("\n$SHOW_MORE")
        } else {
            originalText
        }

    init {
        originalText = text
        spannableStringMore = buildSpannableString(trimmedText, true)
        spannableStringLess = buildSpannableString(fullText, false)
        reloadTextView()
    }

    fun setTextView(text: CharSequence) {
        originalText = text
        spannableStringMore = buildSpannableString(trimmedText, true)
        spannableStringLess = buildSpannableString(fullText, false)
        isExpanded = false
        reloadTextView()
    }

    private fun reloadTextView() {
        val spannableString = if (isExpanded) spannableStringLess else spannableStringMore
        setText(spannableString, BufferType.SPANNABLE)
        isExpanded = isExpanded.not()
    }

    private fun buildSpannableString(text: CharSequence, isTrimmed: Boolean): SpannableString {
        return SpannableString(text).apply {
            if (isExpandable) {
                val link = if (isTrimmed) SHOW_MORE else SHOW_LESS
                val startLink = text.length - link.length
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(view: View) {
                        reloadTextView()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }
                }
                setSpan(
                    clickableSpan,
                    startLink,
                    text.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, R.color.blue_500)),
                    startLink,
                    text.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    startLink,
                    text.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }
}
