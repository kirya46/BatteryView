package com.example.kirillstoianov.batteryview

import android.content.Context
import android.graphics.*
import android.view.View
import android.os.Build
import android.annotation.TargetApi
import com.example.kirillstoianov.batteryview.util.Util


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
        backgroundPath = Util().getBatteryBackground(
                50f, 50f, 150f, 450f,
                14f, 14f, false)
    }

}
