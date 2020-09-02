package com.example.archv1.data.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.example.archv1.R

class TimelineView(context: Context, attrs: AttributeSet?) : LinearLayoutCompat(context, attrs) {

    private var stepQuantity = 0
    private var paddingHorizontal = 0
    private var outlineWidth = 0
    private var fillColor = 0
    private var textColorUnselected = 0
    private var textColorSelected = 0
    private var currentStep: Int = 0

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.TimelineView, 0, 0).apply {
            try {
                stepQuantity = getInteger(R.styleable.TimelineView_steps_quantity, 0)
                paddingHorizontal = getDimensionPixelSize(R.styleable.TimelineView_padding_horizontal, 0)
                outlineWidth = getDimensionPixelSize(R.styleable.TimelineView_outline_width, 0)
                fillColor = getColor(R.styleable.TimelineView_fill_color, 0)
                textColorUnselected = getColor(R.styleable.TimelineView_text_color_unselected, 0)
                textColorSelected = getColor(R.styleable.TimelineView_text_color_selected, 0)
            } finally {
                recycle()
            }
        }
    }

    private val linePaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = outlineWidth.toFloat()
        color = fillColor
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        orientation = HORIZONTAL
        setWillNotDraw(false)
        setStepsQuantity()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 0 until stepQuantity) {
            if (i + 1 < stepQuantity) {
                val pLeft = getChildAt(i)
                val pRight = getChildAt(i + 1)
                drawHorizontalLine(
                    canvas = canvas,
                    xFirst = pLeft.x + pLeft.width,
                    xLast = pRight.x,
                    yCenter = pLeft.pivotY
                )
            }
            getChildAt(i).background = if (currentStep >= i) {
                ContextCompat.getDrawable(context, R.drawable.timeline_shape_selected)
            } else {
                ContextCompat.getDrawable(context, R.drawable.timeline_shape_unselected)
            }
            getChildAt(i).findViewById<TextView>(R.id.tv_step).apply {
                text = (i + 1).toString()
                setTextColor(if (currentStep >= i) {
                    textColorSelected
                } else {
                    textColorUnselected
                })
            }
        }
    }

    private fun drawHorizontalLine(canvas: Canvas, xFirst: Float, xLast: Float, yCenter: Float) {
        canvas.save()
        canvas.drawLine(xFirst, yCenter, xLast, yCenter, linePaint)
        canvas.restore()
    }

    private fun createView() = View.inflate(context, R.layout.view_timeline_item, null)

    private fun addViewToThis(view: View) {
        addView(view.apply {
            layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT).apply {
                marginStart = paddingHorizontal
                marginEnd = paddingHorizontal
            }
        })
    }

    private fun setStepsQuantity() {
        removeAllViews()
        for (i in 0 until stepQuantity) {
            addViewToThis(createView())
        }
        invalidate()
    }

    fun setCurrentStep(step: Int) {
        currentStep = step
        invalidate()
    }
}
