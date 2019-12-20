package com.test.sandev.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF

import com.blankj.utilcode.util.SizeUtils
import com.squareup.picasso.Transformation

class CircleCornerForm(private val mContext: Context) : Transformation {

    override fun transform(source: Bitmap): Bitmap {

        val widthLight = source.width
        val heightLight = source.height
        val radius = SizeUtils.dp2px(8f) // 圆角半径

        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(output)
        val paintColor = Paint()
        paintColor.flags = Paint.ANTI_ALIAS_FLAG

        val rectF = RectF(Rect(0, 0, widthLight, heightLight))

        canvas.drawRoundRect(rectF, radius.toFloat(), radius.toFloat(), paintColor)
        //        canvas.drawRoundRect(rectF, widthLight / 5, heightLight / 5, paintColor);

        val paintImage = Paint()
        paintImage.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
        canvas.drawBitmap(source, 0f, 0f, paintImage)
        source.recycle()
        return output
    }

    override fun key(): String {
        return "roundcorner"
    }
}