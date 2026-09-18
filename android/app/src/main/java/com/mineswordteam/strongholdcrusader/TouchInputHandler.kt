package com.mineswordteam.strongholdcrusader

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View

class TouchInputHandler(
    context: Context,
    private val listener: TouchInputListener
) : View.OnTouchListener {

    interface TouchInputListener {
        fun onCameraPan(deltaX: Float, deltaY: Float)
        fun onPinchZoom(zoomDelta: Float)
        fun onTap(x: Float, y: Float)
    }

    private var initialX = 0f
    private var initialY = 0f
    private var isDragging = false
    private val touchSlop = 12f

    private val scaleDetector = ScaleGestureDetector(
        context,
        object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val scaleFactor = detector.scaleFactor
                val zoomDelta = (scaleFactor - 1.0f) * 10f
                listener.onPinchZoom(zoomDelta)
                return true
            }
        }
    )

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)

        if (event.pointerCount > 1) {
            isDragging = false
            return true
        }

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                initialX = event.x
                initialY = event.y
                isDragging = false
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - initialX
                val dy = event.y - initialY
                if (Math.abs(dx) > touchSlop || Math.abs(dy) > touchSlop) {
                    isDragging = true
                    listener.onCameraPan(dx, dy)
                    initialX = event.x
                    initialY = event.y
                }
            }
            MotionEvent.ACTION_UP -> {
                if (!isDragging) {
                    listener.onTap(event.x, event.y)
                }
                isDragging = false
            }
        }
        return true
    }
}
