package com.test.sandev.utils

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation


class CircleCrop : BitmapTransformation {

    constructor(context: Context) : super(context) {}

    constructor(bitmapPool: BitmapPool) : super(bitmapPool) {}

    override fun getId(): String {
        return "com.example.glidetest.CircleCrop"
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val diameter = Math.min(toTransform.width, toTransform.height)

        val toReuse = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888)
        val result: Bitmap
        if (toReuse != null) {
            result = toReuse
        } else {
            result = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        }

        val dx = (toTransform.width - diameter) / 2
        val dy = (toTransform.height - diameter) / 2
        val canvas = Canvas(result)
        val paint = Paint()
        val shader = BitmapShader(toTransform, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP)
        if (dx != 0 || dy != 0) {
            val matrix = Matrix()
            matrix.setTranslate((-dx).toFloat(), (-dy).toFloat())
            shader.setLocalMatrix(matrix)
        }
        paint.shader = shader
        paint.isAntiAlias = true
        val radius = diameter / 2f
        canvas.drawCircle(radius, radius, radius, paint)

        if (toReuse != null && !pool.put(toReuse)) {
            toReuse.recycle()
        }
        return result
    }

}