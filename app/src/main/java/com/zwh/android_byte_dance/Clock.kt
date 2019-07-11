package com.zwh.android_byte_dance

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.StaticLayout
import android.text.TextPaint
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.view.View

import java.util.Calendar
import java.util.Locale

class Clock : View {

    private var mWidth: Int = 0
    private var mCenterX: Int = 0
    private var mCenterY: Int = 0
    private var mRadius: Int = 0

    /**
     * properties
     */
    private var centerInnerColor: Int = 0
    private var centerOuterColor: Int = 0

    private var secondsNeedleColor: Int = 0
    private var hoursNeedleColor: Int = 0
    private var minutesNeedleColor: Int = 0

    private var degreesColor: Int = 0

    private var hoursValuesColor: Int = 0

    private var numbersColor: Int = 0

    var isShowAnalog = true
        set(showAnalog) {
            field = showAnalog
            invalidate()
        }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val size: Int
        val width = measuredWidth
        val height = measuredHeight
        val widthWithoutPadding = width - paddingLeft - paddingRight
        val heightWithoutPadding = height - paddingTop - paddingBottom

        size = Math.min(widthWithoutPadding, heightWithoutPadding)
        setMeasuredDimension(size + paddingLeft + paddingRight, size + paddingTop + paddingBottom)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        this.centerInnerColor = Color.LTGRAY
        this.centerOuterColor = DEFAULT_PRIMARY_COLOR

        this.secondsNeedleColor = DEFAULT_SECONDARY_COLOR
        this.hoursNeedleColor = DEFAULT_PRIMARY_COLOR
        this.minutesNeedleColor = DEFAULT_PRIMARY_COLOR

        this.degreesColor = DEFAULT_PRIMARY_COLOR

        this.hoursValuesColor = DEFAULT_PRIMARY_COLOR

        numbersColor = Color.WHITE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mWidth = Math.min(width, height)

        val halfWidth = mWidth / 2
        mCenterX = halfWidth
        mCenterY = halfWidth
        mRadius = halfWidth

        if (isShowAnalog) {
            drawDegrees(canvas)
            drawHoursValues(canvas)
            drawNeedles(canvas)
            drawCenter(canvas)
        } else {
            drawNumbers(canvas)
        }
        postInvalidateDelayed(1000)
    }

    private fun drawDegrees(canvas: Canvas) {

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = mWidth * DEFAULT_DEGREE_STROKE_WIDTH
        paint.color = degreesColor

        val rPadded = mCenterX - (mWidth * 0.01f).toInt()
        val rEnd = mCenterX - (mWidth * 0.05f).toInt()

        for (i in 0 until FULL_ANGLE step 6) {

            if (i % RIGHT_ANGLE != 0 && i % 15 != 0)
                paint.alpha = CUSTOM_ALPHA
            else {
                paint.alpha = FULL_ALPHA
            }

            val startX = (mCenterX + rPadded * Math.cos(Math.toRadians(i.toDouble()))).toInt()
            val startY = (mCenterX - rPadded * Math.sin(Math.toRadians(i.toDouble()))).toInt()

            val stopX = (mCenterX + rEnd * Math.cos(Math.toRadians(i.toDouble()))).toInt()
            val stopY = (mCenterX - rEnd * Math.sin(Math.toRadians(i.toDouble()))).toInt()

            canvas.drawLine(startX.toFloat(), startY.toFloat(), stopX.toFloat(), stopY.toFloat(), paint)
        }
    }

    /**
     * @param canvas
     */
    private fun drawNumbers(canvas: Canvas) {

        val textPaint = TextPaint()
        textPaint.textSize = mWidth * 0.2f
        textPaint.color = numbersColor
        textPaint.isAntiAlias = true

        val calendar = Calendar.getInstance()

        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        val amPm = calendar.get(Calendar.AM_PM)

        val time = String.format(
            "%s:%s:%s%s",
            String.format(Locale.getDefault(), "%02d", hour),
            String.format(Locale.getDefault(), "%02d", minute),
            String.format(Locale.getDefault(), "%02d", second),
            if (amPm == AM) "AM" else "PM"
        )

        val spannableString = SpannableStringBuilder(time)
        spannableString.setSpan(
            RelativeSizeSpan(0.3f),
            spannableString.toString().length - 2,
            spannableString.toString().length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // se superscript percent

        val layout = StaticLayout(spannableString, textPaint, canvas.width, Layout.Alignment.ALIGN_CENTER, 1f, 1f, true)
        canvas.translate(mCenterX - layout.width / 2f, mCenterY - layout.height / 2f)
        layout.draw(canvas)
    }

    /**
     * Draw Hour Text Values, such as 1 2 3 ...
     *
     * @param canvas
     */
    private fun drawHoursValues(canvas: Canvas) {
        // Default Color:
        // - hoursValuesColor
        val textPaint = TextPaint()
        textPaint.textSize = mWidth * 0.05f
        textPaint.color = hoursValuesColor
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER

        val r = mCenterX - (mWidth * 0.1f).toInt()

        for (i in 1..12) {

            val x = (mCenterX + r * Math.sin(Math.toRadians(i.toDouble() * 30))).toInt()
            val y = (mCenterX - r * Math.cos(Math.toRadians(i.toDouble() * 30))).toInt()

            val hoursValues = String.format("%d", i)

            var rect = Rect()
            textPaint.getTextBounds(hoursValues, 0, hoursValues.length, rect)

            canvas.drawText(hoursValues, x.toFloat(), (y.toFloat() + rect.height() / 2), textPaint)
//            val spannableString = SpannableStringBuilder(hoursValues)
//            spannableString.setSpan(
//                RelativeSizeSpan(0.3f),
//                0,
//                spannableString.toString().length,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//            ) // se superscript percent
//
//            val layout =
//                StaticLayout(spannableString, textPaint, canvas.width, Layout.Alignment.ALIGN_CENTER, 1f, 1f, true)
//            canvas.translate(x - layout.width / 2f, y - layout.height / 2f)
//            layout.draw(canvas)
        }
    }

    /**
     * Draw hours, minutes needles
     * Draw progress that indicates hours needle disposition.
     *
     * @param canvas
     */
    private fun drawNeedles(canvas: Canvas) {
        // Default Color:
        // - secondsNeedleColor
        // - hoursNeedleColor
        // - minutesNeedleColor
        val calendar = Calendar.getInstance()

        var hour = calendar.get(Calendar.HOUR).toFloat()
        var minute = calendar.get(Calendar.MINUTE).toFloat()
        var second = calendar.get(Calendar.SECOND).toFloat()
        val amPm = calendar.get(Calendar.AM_PM)

        second /= 60
        drawNeedle(canvas, secondsNeedleColor, 0.1f, second)

        minute += second
        minute /= 60
        drawNeedle(canvas, minutesNeedleColor, 0.15f, minute)

        hour += minute
        hour /= 24
        if (amPm != AM) hour += 0.5f
        drawNeedle(canvas, hoursValuesColor, 0.2f, hour)
    }

    /**
     * Draw Center Dot
     *
     * @param canvas
     */
    private fun drawCenter(canvas: Canvas) {
        // Default Color:
        // - centerInnerColor
        // - centerOuterColor
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = mWidth * DEFAULT_DEGREE_STROKE_WIDTH
        paint.alpha = FULL_ALPHA
        paint.color = centerOuterColor

        canvas.drawCircle(mCenterX.toFloat(), mCenterY.toFloat(), mWidth * 0.015f, paint)

        paint.color = centerInnerColor

        canvas.drawCircle(mCenterX.toFloat(), mCenterY.toFloat(), mWidth * 0.01f, paint)
    }

    private fun drawNeedle(canvas: Canvas, color: Int, start: Float, deg: Float) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = mWidth * DEFAULT_DEGREE_STROKE_WIDTH
        paint.alpha = FULL_ALPHA
        paint.color = color

        val rPadded = mCenterX - (mWidth * start).toInt()
        val rEnd = mCenterX - (mWidth * 0.485f).toInt()

        val degree = deg * 360

        val startX = (mCenterX + rPadded * Math.sin(Math.toRadians(degree.toDouble()))).toInt()
        val startY = (mCenterX - rPadded * Math.cos(Math.toRadians(degree.toDouble()))).toInt()

        val stopX = (mCenterX + rEnd * Math.sin(Math.toRadians(degree.toDouble()))).toInt()
        val stopY = (mCenterX - rEnd * Math.cos(Math.toRadians(degree.toDouble()))).toInt()

        canvas.drawLine(startX.toFloat(), startY.toFloat(), stopX.toFloat(), stopY.toFloat(), paint)
    }

    companion object {

        private val TAG = Clock::class.java.simpleName

        private val FULL_ANGLE = 360

        private val CUSTOM_ALPHA = 140
        private val FULL_ALPHA = 255

        private val DEFAULT_PRIMARY_COLOR = Color.WHITE
        private val DEFAULT_SECONDARY_COLOR = Color.LTGRAY

        private val DEFAULT_DEGREE_STROKE_WIDTH = 0.010f

        val AM = 0

        private val RIGHT_ANGLE = 90


        private fun drawTextCenter() {}
    }

}