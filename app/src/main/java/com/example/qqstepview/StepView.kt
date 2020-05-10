package com.example.qqstepview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class StepView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val outerColor: Int
    private val innerColor: Int
    private val borderWidth: Int
    private val stepTextSize: Int

    private val outerPaint: Paint
    private val innerPaint: Paint
    private val textPaint: Paint

    private val rect = RectF()
    private val rectText = Rect()

    private var maxStep: Int = 1000
    private var currentStep: Int = 200

    init {
        val array: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.StepView)
        outerColor = array.getColor(R.styleable.StepView_outerColor, Color.RED)
        innerColor = array.getColor(R.styleable.StepView_innerColor, Color.BLUE)
        borderWidth = array.getDimensionPixelSize(R.styleable.StepView_borderWidth, 15)
        stepTextSize = array.getDimensionPixelSize(R.styleable.StepView_stepTextSize, 15)
        array.recycle()

        outerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = outerColor
            strokeWidth = borderWidth.toFloat()
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }


        innerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = innerColor
            strokeWidth = borderWidth.toFloat()
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = innerColor
            textSize = stepTextSize.toFloat()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val size = min(height, width)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawArcs(canvas)
        drawText(canvas)
    }

    private fun drawText(canvas: Canvas) {
        val text = currentStep.toString()
        textPaint.getTextBounds(text, 0, text.length, rectText)

        val fontMetrics = textPaint.fontMetricsInt
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseLine = height / 2 + dy

        canvas.drawText(
            currentStep.toString(),
            width / 2f - rectText.width() / 2f,
            baseLine.toFloat(), textPaint
        )
    }

    private fun drawArcs(canvas: Canvas) {
        rect.set(
            borderWidth / 2f,
            borderWidth / 2f,
            width.toFloat() - borderWidth / 2f,
            height.toFloat() - borderWidth / 2f
        )
        canvas.drawArc(rect, 135f, 270f, false, outerPaint)
        canvas.drawArc(rect, 135f, 270f * currentStep / maxStep, false, innerPaint)
    }

    fun setStepMax(max: Int) {
        maxStep = max
    }

    fun setCurrentStep(step: Int) {
        this.currentStep = step
        invalidate()
    }

    fun getCurrentStep(): Int {
        return currentStep
    }
}