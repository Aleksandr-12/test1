package com.test.task.Server

import android.graphics.*
import com.squareup.picasso.Transformation

class RectRoundedTransformation() : Transformation {

    override fun transform(source: Bitmap): Bitmap {

        val x = 0
        val y = 0
        val w = source.width
        val h = source.height
        val squaredBitmap = Bitmap.createBitmap(source, x, y, w, h)

        if (squaredBitmap != source) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap(w, h, source.config)

        val canvas = Canvas(bitmap)

//          >>  Draw original rectangle image
//            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
//            canvas.drawBitmap(squaredBitmap, 0F, 0F, paint)

        val rCorner = 10F

        cropCorners(squaredBitmap, canvas, rCorner)
        squaredBitmap.recycle()


        //drawBorder(canvas, rCorner)


        return bitmap
    }

    private fun cropCorners(bitmapSource: Bitmap, canvas: Canvas, rCorner: Float) {

        val width  = canvas.width
        val height = canvas.height

        val maskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
        val maskCanvas = Canvas(maskBitmap)
        val maskPaint  = Paint(Paint.ANTI_ALIAS_FLAG)
        val maskRect   = RectF(0F, 0F, width.toFloat(), height.toFloat())

        // 1. draw base rect
        maskCanvas.drawRect(maskRect, maskPaint)

        // 2. cut off inner rounded rect, leave corners
        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        maskCanvas.drawRoundRect(maskRect, rCorner, rCorner, maskPaint)

        // 3. draw original rectangle image
        val paintSource = Paint(Paint.ANTI_ALIAS_FLAG)

        canvas.drawBitmap(bitmapSource, 0f, 0f, paintSource)

        // 4.cut off corners
        val paintCropped = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        paintCropped.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        canvas.drawBitmap(maskBitmap!!, 0f, 0f, paintCropped)
    }

  /*  private fun drawBorder(canvas: Canvas, rCorner: Float) {

        val borderWidth  = 30F

        val paintBorder         = Paint()
        paintBorder.strokeWidth = borderWidth
        paintBorder.style       = Paint.Style.STROKE
        paintBorder.color       = Color.RED
        paintBorder.isAntiAlias = true


//            // ANDROID'S BORDER IS ALWAYS CENTERED.
//            //  So have to shift every side by half of border's width

        val rectLeft   = 0 + borderWidth / 2
        val rectTop    = 0 + borderWidth / 2
        val rectRight  = canvas.width.toFloat() - borderWidth / 2
        val rectBottom = canvas.height.toFloat() - borderWidth / 2
        val rectF      = RectF(rectLeft, rectTop, rectRight, rectBottom)

//            //  So have to corner reduce radius by half of border's width
        val rectRCorner = rCorner - borderWidth / 2

        canvas.drawRoundRect(rectF, rectRCorner, rectRCorner, paintBorder)
    }*/

    override fun key(): String {
        return "rectRounded"
    }
}