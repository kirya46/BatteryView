package com.example.kirillstoianov.batteryview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import com.example.kirillstoianov.batteryview.util.Util


/**
 * Created by Kirill Stoianov on 25.10.18.
 */
//class BatteryView(context: Context, attributeSet: AttributeSet?, deffStyle: Int) : View(context, attributeSet, deffStyle) {
class BatteryView(context: Context) : View(context) {

    var backgroundPath: Path
    val backgroundPaint: Paint

    var bodyPath: Path
    var bodyPaint: Paint

    var sectionPath: Path
    var sectionPaintGreen: Paint
    var sectionPaintTransparent: Paint

    init {
        backgroundPath = Path()
        backgroundPaint = Paint()
//        backgroundPaint.color = Color.parseColor("#eaeaea")
        backgroundPaint.color = Color.CYAN

        bodyPath = Path()
        bodyPaint = Paint()
        bodyPaint.color = Color.parseColor("#8d8d8d")

        sectionPath = Path()
        sectionPaintGreen = Paint()
        sectionPaintGreen.color = Color.GREEN
        sectionPaintTransparent = Paint()
        sectionPaintTransparent.color = Color.WHITE
        sectionPaintTransparent.alpha = 20
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        initPaths()
        drawBattery(width,height)


        canvas?.drawPath(backgroundPath, backgroundPaint)
        canvas?.drawPath(bodyPath, bodyPaint)
        canvas?.drawPath(sectionPath, sectionPaintGreen)

    }

    private fun drawBattery(width: Int, height: Int) {
        val util = Util()
        val outerCornerRadius = width/12f
        backgroundPath = util.getBatteryBackground(
                0f, 0f, width.toFloat(), height.toFloat(),
                outerCornerRadius, outerCornerRadius,  outerCornerRadius - outerCornerRadius / 3, false)


        val innerCornerRadius = width/24f
        bodyPath = util.getBodyPath(0f, 0f, width.toFloat(), height.toFloat(),
                innerCornerRadius, innerCornerRadius, outerCornerRadius)
    }

    private fun initPaths() {
        val util = Util()
        val outerCornerRadius = 14f
        backgroundPath = util.getBatteryBackground(
                50f, 50f, 150f, 300f,
                outerCornerRadius, outerCornerRadius, 50f + outerCornerRadius - outerCornerRadius / 3, false)


        val innerCornerRadius = 6f
        bodyPath = util.getBodyPath(50f, 50f, 150f, 300f,
                innerCornerRadius, innerCornerRadius, outerCornerRadius)

//        sectionPath = util.getSectionPath(
//                50f, 50f, 150f, 300f,
//                3f, 3f, outerCornerRadius
//        )
    }
}
