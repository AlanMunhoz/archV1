package com.example.archv1.data.util

import android.content.Context
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.archv1.R

class ExpandedTextView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    private var fullText: CharSequence? = null
    private var trimmedText: CharSequence? = null
    private var isExpanded = false
    private val DEFAULT_TRIM_LENGTH = 120
    private val SHOW_MORE = "Ver mais"
    private val SHOW_LESS = "Ver menos"
    private val spannableStringMore : SpannableString by lazy {
        val textToShow  = currentText ?: ""
        val link = if (isExpanded) SHOW_LESS else SHOW_MORE
        val startLink = textToShow.length - link.length
        val spannableString = SpannableString(textToShow)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Log.d("03072020", "click more")
                isExpanded = isExpanded.not()
                setText(spannableStringLess, BufferType.SPANNABLE)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        spannableString.setSpan(clickableSpan, startLink, startLink + link.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.blue_500)), startLink, startLink + link.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(StyleSpan(android.graphics.Typeface.BOLD), startLink, textToShow.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        movementMethod = LinkMovementMethod.getInstance()
        spannableString
    }

    private val spannableStringLess : SpannableString by lazy {
        val textToShow  = currentText ?: ""
        val link = if (isExpanded) SHOW_LESS else SHOW_MORE
        val startLink = textToShow.length - link.length
        val spannableString = SpannableString(textToShow)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Log.d("03072020", "click less")
                isExpanded = isExpanded.not()
                setText(spannableStringMore, BufferType.SPANNABLE)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        spannableString.setSpan(clickableSpan, startLink, startLink + link.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.blue_500)), startLink, startLink + link.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(StyleSpan(android.graphics.Typeface.BOLD), startLink, textToShow.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        movementMethod = LinkMovementMethod.getInstance()
        spannableString
    }

    private val currentText: CharSequence?
        get() = if (isExpanded) fullText else trimmedText

    init {

        fullText = getFullText(text)
        trimmedText = getTrimmedText(text)
//        setText()
        setText(spannableStringLess, BufferType.SPANNABLE)

//        setOnClickListener {
//            isExpanded = isExpanded.not()
//            setText()
//        }
    }

    private fun setText() {
        currentText?.let { textToShow ->
            val link = if(isExpanded) SHOW_LESS else SHOW_MORE
            val startLink = textToShow.length - link.length
            with(SpannableString(textToShow)) {
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(view: View) {
                        Log.d("03072020", "click")
                    }
                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }
                }
                setSpan(clickableSpan, startLink, startLink + link.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.blue_500)), startLink, startLink + link.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(StyleSpan(android.graphics.Typeface.BOLD), startLink, textToShow.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                movementMethod = LinkMovementMethod.getInstance()
                setText(this, BufferType.SPANNABLE)
            }
        }
    }

    private fun getTrimmedText(text: CharSequence) = if (text.length > DEFAULT_TRIM_LENGTH) {
        SpannableStringBuilder(text, 0, DEFAULT_TRIM_LENGTH).append("\n$SHOW_MORE")
    } else {
        text
    }

    private fun getFullText(text: CharSequence) = if (text.length > DEFAULT_TRIM_LENGTH) {
        SpannableStringBuilder(text).append("\n$SHOW_LESS")
    } else {
        text
    }
}
