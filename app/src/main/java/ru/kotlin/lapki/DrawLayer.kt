package ru.kotlin.lapki

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View


class DrawLayer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val mPaint = Paint()
        mPaint.setStyle(Paint.Style.STROKE)
        mPaint.setColor(Color.GRAY)
        mPaint.setAntiAlias(true)
        canvas.save()

        val xMax = 10f
        val xMin = -10f
        val yMax = 10f
        val yMin = -10f
        val width = canvas.getWidth()
        val height = canvas.getHeight()
        canvas.scale(width / (xMax - xMin), -height / (yMax - yMin))
        canvas.translate(-xMin + 0.2f, -yMax + 0.2f)
        mPaint.setStrokeWidth(.1f)

        // Ось X

        // Ось X
        canvas.drawLine(-10f, 0f, 10f, 0f, mPaint)

        // Ось Y

        // Ось Y
        canvas.drawLine(0f, -10f, 0f, 10f, mPaint)

        mPaint.setColor(Color.RED)
        canvas.drawLine(0f, 0f, 10f, 10f, mPaint)

        // Точки вдоль оси X

        // Точки вдоль оси X
        canvas.drawPoint(1f, 0f, mPaint)
        canvas.drawPoint(2f, 0f, mPaint)
        canvas.drawPoint(3f, 0f, mPaint)

        // Точки вдоль оси Y

        // Точки вдоль оси Y
        canvas.drawPoint(0f, 1f, mPaint)
        canvas.drawPoint(0f, 2f, mPaint)
        canvas.drawPoint(0f, 3f, mPaint)

        canvas.restore()

        val w: Float
        val h: Float
        val cx: Float
        val cy: Float
        val radius: Float
        w = getWidth().toFloat()
        h = getHeight().toFloat()
        cx = w / 2
        cy = h / 2

        radius = if (w > h) {
            h / 2
        } else {
            w / 2
        }

        val paint = Paint()
        paint.color = Color.RED // установим зелёный цвет
paint.isAntiAlias = true
        paint.setStyle(Paint.Style.STROKE);


        canvas.drawCircle(cx, cy, radius,  paint)

    }
}