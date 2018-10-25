package com.example.kirillstoianov.batteryview

import android.content.Context
import android.graphics.*
import android.view.View
import android.os.Build
import android.annotation.TargetApi



/**
 * Created by Kirill Stoianov on 25.10.18.
 */
//class BatteryView(context: Context, attributeSet: AttributeSet?, deffStyle: Int) : View(context, attributeSet, deffStyle) {
class BatteryView(context: Context) : View(context) {

    val backgroundPaint: Paint
    var backgroundPath: Path

    init {
        backgroundPaint = Paint()
        backgroundPaint.color = Color.RED

        backgroundPath = Path()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        initBackgroundPath()

        canvas?.drawPath(backgroundPath, backgroundPaint)
    }

    private fun initBackgroundPath() {


//        backgroundPath = roundedRect(50f, 50f, 150f, 450f, 45f, 45f, false)
        backgroundPath = RoundedRect(50f, 50f, 150f, 450f, 14f, 14f, false)
//        backgroundPath = getSquare()
//        backgroundPath = getSquareWithArc()
    }

    fun getSquareWithArc(): Path {
        backgroundPath.reset()
        backgroundPath.moveTo(10f, 10f)
        backgroundPath.lineTo(10f, 60f)


        backgroundPath.lineTo(60f, 60f)
        backgroundPath.lineTo(60f, 10f)
        backgroundPath.lineTo(10f, 10f)

        backgroundPath.close()

        return backgroundPath
    }

    fun getSquare(): Path {
        backgroundPath.reset()
        backgroundPath.moveTo(10f, 10f)
        backgroundPath.lineTo(10f, 60f)

        backgroundPath.lineTo(60f, 60f)
        backgroundPath.lineTo(60f, 10f)
        backgroundPath.lineTo(10f, 10f)

        backgroundPath.close()

        return backgroundPath
    }

    //https://stackoverflow.com/questions/5896234/how-to-use-android-canvas-to-draw-a-rectangle-with-only-topleft-and-topright-cor
    fun roundedRect(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, conformToOriginalPost: Boolean): Path {
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
        path.rQuadTo(0f, -ry, -rx, -ry)//top-right corner
        path.rLineTo(-widthMinusCorners, 0f)
        path.rQuadTo(-rx, 0f, -rx, ry) //top-left corner
        path.rLineTo(0f, heightMinusCorners)

        if (conformToOriginalPost) {
            path.rLineTo(0f, ry)
            path.rLineTo(width, 0f)
            path.rLineTo(0f, -ry)
        } else {
            path.rQuadTo(0f, ry, rx, ry)//bottom-left corner
            path.rLineTo(widthMinusCorners, 0f)
            path.rQuadTo(rx, 0f, rx, -ry) //bottom-right corner
        }

        path.rLineTo(0f, -heightMinusCorners)

        path.close()//Given close, last lineto can be removed.

        return path
    }

    //http://qaru.site/questions/191948/how-to-use-android-canvas-to-draw-a-rectangle-with-only-topleft-and-topright-corners-round
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun RoundedRect(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, conformToOriginalPost: Boolean): Path {
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

        path.close()//Given close, last lineto can be removed.
        return path
    }
}
