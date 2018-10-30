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


    var maxSectionCount: Int = 6
        set(value) {
            field = value
            invalidate()
        }

    var fillSectionCount: Int = 3
        set(value) {
            field = if (value > maxSectionCount) {
                maxSectionCount
            } else {
                value
            }
            invalidate()
        }


    //battery
    private var backgroundPath: Path
    private val backgroundPaint: Paint

    //not filled grey body
    private var bodyPath: Path
    private var bodyPaint: Paint

    //filled color body
    private var bodyFilledPath: Path
    private var bodyGreenPaint: Paint
    private var bodyYellowPaint: Paint
    private var bodyRedPaint: Paint

    //grey rect
    private var darkGreyPath:Path
    private var darkGreyPathPaint:Paint

    //section
    private var sectionPath: Path
    private var sectionPaint: Paint

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE,null)

        backgroundPath = Path()
        backgroundPaint = Paint()
        backgroundPaint.color = Color.parseColor("#eaeaea")

        //not filled grey body
        bodyPath = Path()
        bodyPaint = Paint()
        bodyPaint.color = Color.WHITE
        bodyPaint.alpha = 80

        //filled color body
        bodyFilledPath = Path()
        bodyGreenPaint = Paint()
        bodyGreenPaint.color = Color.parseColor("#0adbac")
        bodyYellowPaint = Paint()
        bodyYellowPaint.color = Color.parseColor("#ffb300")
        bodyRedPaint = Paint()
        bodyRedPaint.color = Color.parseColor("#ff2222")

        //dark grey
        darkGreyPath = Path()
        darkGreyPathPaint = Paint()
        darkGreyPathPaint.color = Color.parseColor("#8d8d8d")

        //section
        sectionPath = Path()
        sectionPaint = Paint().apply {                                                              //TODO: Problem with clearing all layers below
            alpha =0xFF //transparent
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            isAntiAlias = true
//            style = Paint.Style.FILL
        }


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawBattery()

        canvas?.drawPath(backgroundPath, backgroundPaint)
        canvas?.drawPath(bodyPath, bodyPaint)
        canvas?.drawPath(bodyFilledPath, bodyYellowPaint)                                            //fixme change destination color
        canvas?.drawPath(darkGreyPath,darkGreyPathPaint)
        canvas?.drawPath(sectionPath, sectionPaint)

        canvas?.apply { drawLightning(this) }
    }

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


        //draw non-filled body
        val sectionTop = top + outerCornerRadius - outerCornerRadius / 3
        val sectionBodyLeft = left + innerCornerRadius
        val sectionBodiTop = sectionTop + innerCornerRadius
        val sectionBodyRight = right - innerCornerRadius
        val sectiobBodyBottom = bottom - innerCornerRadius
        bodyPath = drawRoundReact(sectionBodyLeft, sectionBodiTop, sectionBodyRight, sectiobBodyBottom, innerCornerRadius, innerCornerRadius, false)

        //draw filled body TODO: adjust height and remove corners
        bodyFilledPath= drawRoundReact(sectionBodyLeft, sectionBodiTop, sectionBodyRight, sectiobBodyBottom, innerCornerRadius, innerCornerRadius, false)


        //draw dark grey rect
       darkGreyPath= drawRoundReact(sectionBodyLeft, sectionBodiTop, sectionBodyRight, sectiobBodyBottom, innerCornerRadius, innerCornerRadius, false)

        //draw sections
        val partHeight = ((sectiobBodyBottom - sectionBodiTop) - partCornerRadius * (maxSectionCount + 1)) / maxSectionCount
        val partLeft = sectionBodyLeft + partCornerRadius
        val partRight = sectionBodyRight - partCornerRadius

        var start = maxSectionCount - fillSectionCount
        IntRange(1, maxSectionCount).forEach { index ->

            when (index) {
                1 -> {
                    val partTop = sectionBodiTop + partCornerRadius
                    val partBottom = sectionBodiTop + partCornerRadius + partHeight
                    sectionPath.addPath(drawRoundReact(partLeft, partTop, partRight, partBottom, partCornerRadius, partCornerRadius, false))
                }

                else -> {
                    val partTop = sectionBodiTop + partCornerRadius + ((partHeight + partCornerRadius) * (index - 1))
                    val partBottom = sectionBodiTop + partCornerRadius + ((partHeight + partCornerRadius) * (index - 1)) + partHeight
                    sectionPath.addPath(drawRoundReact(partLeft, partTop, partRight, partBottom, partCornerRadius, partCornerRadius, false))
                }
            }
        }
//
//
//        sectionFilledPath = Path()//FIXME tmp solution for clear path
//
//        var start = maxSectionCount - fillSectionCount
//        IntRange(start, maxSectionCount).reversed().forEach { index ->
//
//            when (index) {
//                1 -> {
//                    val partTop = sectionBodiTop + partCornerRadius
//                    val partBottom = sectionBodiTop + partCornerRadius + partHeight
//                    sectionFilledPath.addPath(drawRoundReact(partLeft, partTop, partRight, partBottom, partCornerRadius, partCornerRadius, false))
//                }
//
//                else -> {
//                    val partTop = sectionBodiTop + partCornerRadius + ((partHeight + partCornerRadius) * (index - 1))
//                    val partBottom = sectionBodiTop + partCornerRadius + ((partHeight + partCornerRadius) * (index - 1)) + partHeight
//                    sectionFilledPath.addPath(drawRoundReact(partLeft, partTop, partRight, partBottom, partCornerRadius, partCornerRadius, false))
//                }
//            }
//        }
    }

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
}
