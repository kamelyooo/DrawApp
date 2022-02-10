package com.example.drawingapp.UI

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.drawingapp.Enums.ShapeType
import com.example.drawingapp.R
import com.example.drawingapp.UI.MainActivity.Companion.mPaint

class PaintView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPath: Path? = MainActivity.mPath
    private lateinit var mCanvas: Canvas
    private lateinit var mBitmap: Bitmap
    private val TOUCH_STROKE_WIDTH = 5f
    private val TOUCH_TOLERANCE = 4f

    /**
     * Indicates if you are drawing
     */
    private var isDrawing = false

    /**
     * Indicates if the drawing is ended
     */
    private var isDrawingEnded = false


    private var mStartX = 0f
    private var mStartY = 0f

    private var mx = 0f
    private var my = 0f
    var countTouch: Int = 0




    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.apply {
            isAntiAlias = true
            isDither = true
            color = resources.getColor(R.color.red)
            strokeWidth = TOUCH_STROKE_WIDTH
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }


    }




    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = Canvas(mBitmap);
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mx = event!!.x
        my = event.y
        when (MainActivity.mCurrentShape) {
            ShapeType.LINE -> onTouchEventLine(event)
            ShapeType.SMOOTHLINE -> onTouchEventSmoothLine(event)
            ShapeType.RECTANGLE -> onTouchEventRectangle(event)

            ShapeType.CIRCLE -> onTouchEventCircle(event)

        }
        return true

    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mBitmap, 0f, 0f, mPaint);


        if (isDrawing) {
            when (MainActivity.mCurrentShape) {
                ShapeType.SMOOTHLINE -> onDrawLine(canvas);
                ShapeType.LINE -> onDrawLine(canvas);
                ShapeType.RECTANGLE -> onDrawRectangle(canvas)
                ShapeType.CIRCLE -> onDrawCircle(canvas);
            }
        }
    }

    //-------------------------------------------
    //Draw Line and Smooth Line


    private fun onDrawLine(canvas: Canvas) {
        val dx = Math.abs(mx - mStartX)
        val dy = Math.abs(my - mStartY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            canvas.drawLine(mStartX, mStartY, mx, my, mPaint)
        }


    }

    private fun onTouchEventSmoothLine(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mx
                mStartY = my
                mPath!!.reset()
                mPath!!.moveTo(mx, my)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = Math.abs(mx - mStartX)
                val dy = Math.abs(my - mStartY)
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                    mPath!!.quadTo(mStartX, mStartY, (mx + mStartX) / 2, (my + mStartY) / 2)
                    mStartX = mx
                    mStartY = my
                }
                mCanvas.drawPath(mPath!!, mPaint!!)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                mPath!!.lineTo(mStartX, mStartY)
                mCanvas.drawPath(mPath!!, mPaint!!)
                mPath!!.reset()
                invalidate()
            }
        }
    }

    private fun onTouchEventLine(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mx
                mStartY = my
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                invalidate()


            }
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                mCanvas.drawLine(mStartX, mStartY, mx, my, mPaint!!)
                invalidate()
            }
        }
    }


    //---------------------------------------------------------------
    // Draw Circle
    private fun onTouchEventCircle(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mx
                mStartY = my
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> invalidate()
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                mCanvas.drawOval(
                    mStartX,
                    mStartY,
                    mx,
                    my,
                    mPaint!!
                )
                invalidate()
            }
        }
    }

    private fun onDrawCircle(canvas: Canvas) {
        canvas.drawOval(mStartX, mStartY, mx, my, mPaint!!);
    }
    //--------------------------------------------------------------------
    //Draw Rectangle

    private fun drawRectangle(canvas: Canvas, paint: Paint) {
        val right = if (mStartX > mx) mStartX else mx
        val left = if (mStartX > mx) mx else mStartX
        val bottom = if (mStartY > my) mStartY else my
        val top = if (mStartY > my) my else mStartY
        canvas.drawRect(left, top, right, bottom, paint)
    }

    private fun onDrawRectangle(canvas: Canvas) {
        drawRectangle(canvas, mPaint!!);
    }


    private fun onTouchEventRectangle(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mx
                mStartY = my
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> invalidate()
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                drawRectangle(mCanvas, mPaint!!)
                invalidate()
            }
        }
    }


}