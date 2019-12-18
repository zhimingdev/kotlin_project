package com.test.sandev.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import kotlin.math.sqrt

class IconNmuberView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var bitmap: Bitmap? = null
    private var number = "0"
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val x = width / 2
        val y = height / 2

        val paint = Paint()

        //在画布上画上图标作为背景，不需动态加载的话可以省略这部
        if (bitmap != null) {
            canvas.drawBitmap(bitmap!!, null!!, paint)
        }

        //数字为0直接返回
        if (number == "0") {
            return
        }

        //设置画笔为红色
        paint.color = Color.RED
        //计算小圆形的圆心图标，半径取大图标半径的四分之一
        canvas.drawCircle((x + sqrt((x * x / 2).toDouble())).toFloat(), (x - sqrt((x * x / 2).toDouble())).toFloat(), (x / 4).toFloat(), paint)
        paint.color = Color.WHITE
        //为适应各种屏幕分辨率，字体大小取半径的3.5分之一，具体根据项目需要调节
        paint.textSize = (x / 3.5).toFloat()
        //去除锯齿效果
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.isAntiAlias = true
        //字体加粗
        paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        //字体位置设置为以圆心为中心
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(number, (x + sqrt((x * x / 2).toDouble())).toFloat(), (x - sqrt((x * x / 2).toDouble())).toFloat() + x / 9, paint)
    }

    //设置图标
    fun setIcon(bitmap: Bitmap) {
        this.bitmap = bitmap
    }

    //设置数字
    fun setNumber(number: String) {
        this.number = number
    }

}
