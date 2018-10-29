package com.example.kirillstoianov.batteryview

import android.content.Context
import android.graphics.*
import android.view.View


/**
 * Created by Kirill Stoianov on 25.10.18.
 */
//class BatteryView(context: Context, attributeSet: AttributeSet?, deffStyle: Int) : View(context, attributeSet, deffStyle) {
class BatteryView(context: Context) : View(context) {

    companion object {
        val TAG: String = BatteryView::class.java.simpleName
    }

    var backgroundPath: Path
    val backgroundPaint: Paint

    var bodyPath: Path
    var bodyPaint: Paint

    var sectionBackgroundPath : Path
    var sectionBackgroundPaintTransparent: Paint

    var sectionFilledPath:Path
    var sectionPaintGreen: Paint

    val sectionCount = 6

    init {
        backgroundPath = Path()
        backgroundPaint = Paint()
//        backgroundPaint.color = Color.parseColor("#eaeaea")
        backgroundPaint.color = Color.BLUE

        bodyPath = Path()
        bodyPaint = Paint()
        bodyPaint.color = Color.parseColor("#8d8d8d")

        sectionBackgroundPath = Path()
        sectionBackgroundPaintTransparent = Paint()
        sectionBackgroundPaintTransparent.color = Color.WHITE
        sectionBackgroundPaintTransparent.alpha = 80

        sectionFilledPath = Path()
        sectionPaintGreen = Paint()
        sectionPaintGreen.color = Color.GREEN
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        initPaths()
        drawBattery()


        canvas?.drawPath(backgroundPath, backgroundPaint)
        canvas?.drawPath(bodyPath, bodyPaint)
        canvas?.drawPath(sectionBackgroundPath , sectionBackgroundPaintTransparent)
        canvas?.drawPath(sectionFilledPath, sectionPaintGreen)

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


        //draw body
        val sectionTop = top + outerCornerRadius - outerCornerRadius / 3
        val sectionBodyLeft = left + innerCornerRadius
        val sectionBodiTop = sectionTop + innerCornerRadius
        val sectionBodyRight = right - innerCornerRadius
        val sectiobBodyBottom = bottom - innerCornerRadius

        bodyPath = drawRoundReact(sectionBodyLeft, sectionBodiTop, sectionBodyRight, sectiobBodyBottom, innerCornerRadius, innerCornerRadius, false)

        //draw sections
        val partHeight = ((sectiobBodyBottom - sectionBodiTop) - partCornerRadius * (sectionCount + 1)) / sectionCount
        val partLeft = sectionBodyLeft + partCornerRadius
        val partRight = sectionBodyRight - partCornerRadius

        IntRange(1, sectionCount).forEach { index ->

            when (index) {
                1 -> {
                    val partTop = sectionBodiTop + partCornerRadius
                    val partBottom = sectionBodiTop + partCornerRadius + partHeight
                    sectionBackgroundPath .addPath(drawRoundReact(partLeft, partTop, partRight, partBottom, partCornerRadius, partCornerRadius, false))
                }

                else -> {
                    val partTop = sectionBodiTop + partCornerRadius + ((partHeight + partCornerRadius) * (index - 1))
                    val partBottom = sectionBodiTop + partCornerRadius + ((partHeight + partCornerRadius) * (index - 1))+partHeight
                    sectionBackgroundPath .addPath(drawRoundReact(partLeft, partTop, partRight, partBottom, partCornerRadius, partCornerRadius, false))
                }
            }
        }



        //TODO it is mock battery filling
        IntRange(1, sectionCount).forEach { index ->

            when (index) {
                5,6-> {
                    val partTop = sectionBodiTop + partCornerRadius + ((partHeight + partCornerRadius) * (index - 1))
                    val partBottom = sectionBodiTop + partCornerRadius + ((partHeight + partCornerRadius) * (index - 1))+partHeight
                    sectionFilledPath .addPath(drawRoundReact(partLeft, partTop, partRight, partBottom, partCornerRadius, partCornerRadius, false))
                }
            }
        }
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
