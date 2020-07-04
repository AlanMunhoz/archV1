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

    private val trimLength = 120
    private val showMore = context.getString(R.string.show_more)
    private val showLess = context.getString(R.string.show_less)
    private var isExpanded = false
    private lateinit var originalText: CharSequence
    private lateinit var spannableStringMore: SpannableString
    private lateinit var spannableStringLess: SpannableString

    private val isExpandable: Boolean
        get() = originalText.length > trimLength

    private val fullText: CharSequence
        get() = if (isExpandable) {
            SpannableStringBuilder(originalText).append("\n$showLess")
        } else {
            originalText
        }

    private val trimmedText: CharSequence
        get() = if (isExpandable) {
            SpannableStringBuilder(originalText, 0, trimLength).append("\n$showMore")
        } else {
            originalText
        }

    private val clickableSpan = object : ClickableSpan() {
        override fun onClick(view: View) {
            reloadTextView()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }
    }

    init {
        setTextView(text)
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
        isExpanded = isExpanded.not()
        setText(spannableString, BufferType.SPANNABLE)
    }

    private fun buildSpannableString(text: CharSequence, isTrimmed: Boolean): SpannableString {
        return SpannableString(text).apply {
            if (isExpandable) {
                val link = if (isTrimmed) showMore else showLess
                val startLink = text.length - link.length
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
