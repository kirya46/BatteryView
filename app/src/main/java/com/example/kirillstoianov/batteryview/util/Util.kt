package com.example.kirillstoianov.batteryview.util

import android.graphics.Path
import android.graphics.RectF

/**
 * Created by Kirill Stoianov on 26/10/2018.
 */
class Util {


    fun getSquare(path: Path): Path {
        path.reset()
        path.moveTo(10f, 10f)
        path.lineTo(10f, 60f)

        path.lineTo(60f, 60f)
        path.lineTo(60f, 10f)
        path.lineTo(10f, 10f)

        path.close()

        return path
    }

    //https://stackoverflow.com/questions/5896234/how-to-use-android-canvas-to-draw-a-rectangle-with-only-topleft-and-topright-cor
    fun roundedRectQuadApi14(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, conformToOriginalPost: Boolean): Path {
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
    fun roundedRectArcApi14(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, conformToOriginalPost: Boolean): Path {
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


//    fun getBatteryBackground(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, conformToOriginalPost: Boolean): Path {
//        var rx = rx
//        var ry = ry
//        val path = Path()
//        if (rx < 0) rx = 0f
//        if (ry < 0) ry = 0f
//        val width = right - left
////        val height = bottom - top  fixme: uncomment
//        val height = bottom - (top - 200)
//        if (rx > width / 2) rx = width / 2
//        if (ry > height / 2) ry = height / 2
//        val widthMinusCorners = width - 2 * rx
//        val heightMinusCorners = height - 2 * ry
//
//        path.moveTo(right, top + ry)
//        path.arcTo(RectF(right - 2 * rx, top, right, top + 2 * ry), 0f, -90f, false) //top-right-corner
//        path.rLineTo(-widthMinusCorners, 0f)
//        path.arcTo(RectF(left, top, left + 2 * rx, top + 2 * ry), 270f, -90f, false)//top-left corner.
//        path.rLineTo(0f, heightMinusCorners)
//        if (conformToOriginalPost) {
//            path.rLineTo(0f, ry)
//            path.rLineTo(width, 0f)
//            path.rLineTo(0f, -ry)
//        } else {
//            path.arcTo(RectF(left, bottom - 2 * ry, left + 2 * rx, bottom), 180f, -90f, false) //bottom-left corner
//            path.rLineTo(widthMinusCorners, 0f)
//            path.arcTo(RectF(right - 2 * rx, bottom - 2 * ry, right, bottom), 90f, -90f, false) //bottom-right corner
//        }
//
//        path.rLineTo(0f, -heightMinusCorners)
//
//        path.close()
//        return path
//    }

    fun getBatteryBackground(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, tipPadding: Float, conformToOriginalPost: Boolean): Path {
        var rx = rx
        var ry = ry
        val path = Path()
        if (rx < 0) rx = 0f
        if (ry < 0) ry = 0f

        val bodyTop = tipPadding
        val width = right - left
        val height = bottom - bodyTop
        if (rx > width / 2) rx = width / 2
        if (ry > height / 2) ry = height / 2
        val widthMinusCorners = width - 2 * rx
        val heightMinusCorners = height - 2 * ry

        path.moveTo(right, bodyTop + ry)

        path.arcTo(RectF(right - 2 * rx, bodyTop, right, bodyTop + 2 * ry), 0f, -90f, false) //top right corner
        path.rLineTo(-widthMinusCorners, 0f)

        path.arcTo(RectF(left, bodyTop, left + 2 * rx, bodyTop + 2 * ry), 270f, -90f, false)//top left corner.
        path.rLineTo(0f, heightMinusCorners)

        path.arcTo(RectF(left, bottom - 2 * ry, left + 2 * rx, bottom), 180f, -90f, false) //bottom left corner
        path.rLineTo(widthMinusCorners, 0f)

        path.arcTo(RectF(right - 2 * rx, bottom - 2 * ry, right, bottom), 90f, -90f, false) //bottom right corner
        path.rLineTo(0f, -heightMinusCorners)


        //draw tip
        val tipLeft = left + (width / 4)
        val tipRight = left + (width / 2 + width / 4)
        val tipTop = top
        val tipBottom = top + 2 * ry

        val tipPath = roundedRectArcApi14(tipLeft, tipTop, tipRight, tipBottom, rx, ry, conformToOriginalPost)
        path.addPath(tipPath)

        path.close()

        return path
    }


    fun getBodyPath(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, paddingTop: Float): Path {
        val bodyTop = top + paddingTop - paddingTop / 3
        return roundedRectArcApi14(left + rx, bodyTop + ry, right - rx, bottom - ry, rx, ry, false)
    }

//    fun getSectionPath(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, paddingTop: Float): Path {
//        val sectionCount = 6f
//        val bodyTop = top + paddingTop - paddingTop / 3
//
//
//        return roundedRectArcApi14(left + rx, bodyTop + ry, right - rx,bottom-(bottom/sectionCount) - ry, rx, ry, false)
//    }

    @Deprecated("test")
    fun test(left: Float, top: Float, right: Float, bottom: Float, cr: Float, conformToOriginalPost: Boolean): Path {
        val path = Path()
        val width = right - left
        val height = bottom - top

        var leftP: Float
        var topP: Float
        var rightP: Float
        var bottomP: Float


        //Set cursor
        path.moveTo(width - cr, cr)

        //1-2
        leftP = width - 2 * cr
        topP = top + cr
        rightP = right
        bottomP = top + 2 * cr

        path.arcTo(RectF(leftP, topP, rightP, bottomP), 0f, -90f, false) //top right corner

        //2-3
        path.lineTo(right, height - cr)

        //3-4
        leftP = right - cr
        topP = bottom - cr
        rightP = right
        bottomP = bottom
        path.arcTo(RectF(leftP, topP, rightP, bottomP), 90f, -90f, false) //bottom right corner

        //4-5
        path.lineTo((right - left) + cr, bottom)

        //5-6
        leftP = left
        topP = bottom - cr
        rightP = (right - left) + cr
        bottomP = bottom
        path.arcTo(RectF(leftP, topP, rightP, bottomP), 180f, -90f, false)  //bottom left corner

        //6-7
        path.lineTo(left, (cr * 2))

        //7-8
        leftP = left
        topP = cr
        rightP = cr
        bottomP = cr
        path.arcTo(RectF(leftP, topP, rightP, bottomP), 270f, -90f, false)  //top left corner

        //8-9
        path.lineTo((width / 4) - cr * 2, cr)

        //9-10
        leftP = (width / 4) - cr
        topP = top
        rightP = cr
        bottomP = top + cr
        path.arcTo(RectF(leftP, topP, rightP, bottomP), 270f, -90f, false) //tip left corner

        //10-11
        path.lineTo((width / 2 - width / 4) - cr, top)

        //11-12
        leftP = (width / 2 - width / 4) - cr
        topP = cr
        rightP = (width / 2 - width / 4)
        bottomP = cr
        path.arcTo(RectF(leftP, topP, rightP, bottomP), 0f, -90f, false) //tip right

        //12-13
        path.lineTo(width - cr, cr)

        path.close()

        return path
    }

}
