package com.example.kirillstoianov.batteryview

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.view.View
import kotlin.math.roundToInt


/**
 * Created by Kirill Stoianov on 25.10.18.
 */
//class BatteryView(context: Context, attributeSet: AttributeSet?, deffStyle: Int) : View(context, attributeSet, deffStyle) {
class BatteryView(context: Context) : View(context) {

    companion object {
        val TAG: String = BatteryView::class.java.simpleName
    }

    /**
     * Minimum section count for non-filled battery.
     */
    private val minSectionCount = 0

    /**
     * Max section count in battery.
     */
    var maxSectionCount: Int = 6
        set(value) {
            field = value
            invalidate()
        }

    /**
     * Default filled section count.
     * NOTE: set 0 for empty battery.
     */
    var fillSectionCount: Int = 0
        set(value) {
            field = when {
                value > maxSectionCount -> {
                    maxSectionCount
                }
                value <= minSectionCount -> minSectionCount
                else -> value
            }
            invalidate()
        }

    /**
     * Battery silhouette path.
     */
    private var backgroundPath: Path
    private val backgroundPaint: Paint

    /**
     * Battery body path.
     */
    private var bodyPath: Path
    private var bodyPaint: Paint

    /**
     * Battery sections background path.
     */
    private var sectionBackgroundPath: Path
    private var sectionBackgroundPaintTransparent: Paint

    /**
     * Battery filled sections path.
     */
    private var sectionFilledPath: Path
    private var sectionPaintGreen: Paint
    private var sectionPaintYellow: Paint
    private var sectionPaintRed: Paint

    init {
        backgroundPath = Path()
        backgroundPaint = Paint()
        backgroundPaint.color = Color.parseColor("#eaeaea")

        bodyPath = Path()
        bodyPaint = Paint()
        bodyPaint.color = Color.parseColor("#8d8d8d")

        sectionBackgroundPath = Path()
        sectionBackgroundPaintTransparent = Paint()
        sectionBackgroundPaintTransparent.color = Color.WHITE
        sectionBackgroundPaintTransparent.alpha = 80

        sectionFilledPath = Path()
        sectionPaintGreen = Paint()
        sectionPaintGreen.color = Color.parseColor("#0adbac")
        sectionPaintYellow = Paint()
        sectionPaintYellow.color = Color.parseColor("#ffb300")
        sectionPaintRed = Paint()
        sectionPaintRed.color = Color.parseColor("#ff2222")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawBattery()

        canvas?.drawPath(backgroundPath, backgroundPaint)
        canvas?.drawPath(bodyPath, bodyPaint)
        canvas?.drawPath(sectionBackgroundPath, sectionBackgroundPaintTransparent)
        canvas?.drawPath(sectionFilledPath, getFillSectionsPaint(calculateFilledSectionsPercent()))

        canvas?.apply { drawLightning(this) }
    }

    /**
     * Increase filled sections count.
     *
     * @return increased count.
     */
    fun inscrease(): Int {
        fillSectionCount += 1
        return fillSectionCount
    }

    /**
     * Decrease filled sections count.
     *
     * @return decreased count.
     */
    fun decrease(): Int {
        fillSectionCount -= 1
        return fillSectionCount
    }

    /**
     * Init all path's related to battery.
     */
    private fun drawBattery() {

        val outerCornerRadius = width / 12f
        val innerCornerRadius = width / 24f
        val partCornerRadius = width / 48f

        val tipPadding = outerCornerRadius - outerCornerRadius / 3
        val bodyTop = tipPadding

        backgroundPath = drawRoundReact(left.toFloat(), bodyTop, right.toFloat(), bottom.toFloat(), outerCornerRadius, outerCornerRadius, false)


        //draw tip
        val tipLeft = left + (width / 4)
        val tipRight = left + (width / 2 + width / 4)
        val tipTop = top
        val tipBottom = top + 2 * outerCornerRadius

        val tipPath = drawRoundReact(tipLeft.toFloat(), tipTop.toFloat(), tipRight.toFloat(), tipBottom, outerCornerRadius, outerCornerRadius, false)
        backgroundPath.addPath(tipPath)


        //draw body
        val sectionTop = top + outerCornerRadius - outerCornerRadius / 3
        val sectionBodyLeft = left + innerCornerRadius
        val sectionBodiTop = sectionTop + innerCornerRadius
        val sectionBodyRight = right - innerCornerRadius
        val sectiobBodyBottom = bottom - innerCornerRadius

        bodyPath = drawRoundReact(sectionBodyLeft, sectionBodiTop, sectionBodyRight, sectiobBodyBottom, innerCornerRadius, innerCornerRadius, false)

        //draw sections
        val partHeight = ((sectiobBodyBottom - sectionBodiTop) - partCornerRadius * (maxSectionCount + 1)) / maxSectionCount
        val partLeft = sectionBodyLeft + partCornerRadius
        val partRight = sectionBodyRight - partCornerRadius

        IntRange(1, maxSectionCount).forEach { index ->

            when (index) {
                1 -> {
                    val partTop = sectionBodiTop + partCornerRadius
                    val partBottom = sectionBodiTop + partCornerRadius + partHeight
                    sectionBackgroundPath.addPath(drawRoundReact(partLeft, partTop, partRight, partBottom, partCornerRadius, partCornerRadius, false))
                }

                else -> {
                    val partTop = sectionBodiTop + partCornerRadius + ((partHeight + partCornerRadius) * (index - 1))
                    val partBottom = sectionBodiTop + partCornerRadius + ((partHeight + partCornerRadius) * (index - 1)) + partHeight
                    sectionBackgroundPath.addPath(drawRoundReact(partLeft, partTop, partRight, partBottom, partCornerRadius, partCornerRadius, false))
                }
            }
        }


        sectionFilledPath.reset()

        val start = maxSectionCount - (fillSectionCount - 1)

        if (fillSectionCount < 0 || start == maxSectionCount && fillSectionCount == minSectionCount) {
            sectionFilledPath.reset()
            return
        }

        IntRange(start, maxSectionCount).reversed().forEach { index ->

            when (index) {
                0 -> {
                    val partTop = sectionBodiTop + partCornerRadius
                    val partBottom = sectionBodiTop + partCornerRadius + partHeight
                    sectionFilledPath.addPath(drawRoundReact(partLeft, partTop, partRight, partBottom, partCornerRadius, partCornerRadius, false))
                }

                else -> {
                    val partTop = sectionBodiTop + partCornerRadius + ((partHeight + partCornerRadius) * (index - 1))
                    val partBottom = sectionBodiTop + partCornerRadius + ((partHeight + partCornerRadius) * (index - 1)) + partHeight
                    sectionFilledPath.addPath(drawRoundReact(partLeft, partTop, partRight, partBottom, partCornerRadius, partCornerRadius, false))
                }
            }
        }
    }

    /**
     * Draw battery lightning drawable.
     */
    private fun drawLightning(canvas: Canvas) {
        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_lightning)
        val lightningWidth: Int = (width / 2f).roundToInt()
        val lightningHeight: Int = (height / 2f).roundToInt()

        val drawableLeft: Int = width / 2 - lightningWidth / 2
        val drawableRight: Int = width / 2 + lightningWidth / 2
        val drawableTop: Int = height / 2 - lightningHeight / 2
        val drawableBottom: Int = height / 2 + lightningHeight / 2

        drawable?.setBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
        drawable?.draw(canvas)
    }

    /**
     * Util function for draw rect with rounded corners.
     */
    private fun drawRoundReact(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, conformToOriginalPost: Boolean): Path {
        var rx = rx
        var ry = ry
        val path = Path()
        if (rx < 0) rx = 0f
        if (ry < 0) ry = 0f
        val width = right - left
        val height = bottom - top
        if (rx > width / 2) rx = width / 2
        if (ry > height / 2) ry = height / 2
        val widthMinusCorners = width - 2 * rx
        val heightMinusCorners = height - 2 * ry

        path.moveTo(right, top + ry)
        path.arcTo(RectF(right - 2 * rx, top, right, top + 2 * ry), 0f, -90f, false) //top-right-corner
        path.rLineTo(-widthMinusCorners, 0f)
        path.arcTo(RectF(left, top, left + 2 * rx, top + 2 * ry), 270f, -90f, false)//top-left corner.
        path.rLineTo(0f, heightMinusCorners)
        if (conformToOriginalPost) {
            path.rLineTo(0f, ry)
            path.rLineTo(width, 0f)
            path.rLineTo(0f, -ry)
        } else {
            path.arcTo(RectF(left, bottom - 2 * ry, left + 2 * rx, bottom), 180f, -90f, false) //bottom-left corner
            path.rLineTo(widthMinusCorners, 0f)
            path.arcTo(RectF(right - 2 * rx, bottom - 2 * ry, right, bottom), 90f, -90f, false) //bottom-right corner
        }

        path.rLineTo(0f, -heightMinusCorners)

        path.close()
        return path
    }

    /**
     * Get filled sections in percent.
     */
    private fun calculateFilledSectionsPercent(): Float = fillSectionCount * 100.0f / maxSectionCount

    /**
     * Get [Paint] for filled sections.
     */
    private fun getFillSectionsPaint(percent: Float): Paint {
        return when (percent) {
            in 0f..17f -> sectionPaintRed
            in 18f..50f -> sectionPaintYellow
            else -> sectionPaintGreen
        }
    }
}
