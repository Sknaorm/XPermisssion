package com.rulin.layout

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.rulin.architecture.R

class ShapeLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var mCornersRadius = -1F
    private var mLeftCornersRadius = -1F
    private var mTopCornersRadius = -1F
    private var mRightCornersRadius = -1F
    private var mBottomCornersRadius = -1F
    private var mSolidColor = -1
    private var mGradientStartColor = -1
    private var mGradientCenterColor = -1
    private var mGradientEndColor = -1
    private var mOrientation = 0

    init {
        val styleable = context.obtainStyledAttributes(attrs, R.styleable.ShapeLayout)
        mCornersRadius = styleable.getDimension(R.styleable.ShapeLayout_corners_radius, -1F)
        mSolidColor = styleable.getColor(R.styleable.ShapeLayout_solid_color, -1)
        mOrientation = styleable.getInt(R.styleable.ShapeLayout_gradient_angle, 0)
//        val shape = styleable.getInt(R.styleable.ShapeLayout_shape, -1)
//        when (shape) {
//            0 -> setShapeBackground()
//        }
        styleable.recycle()
    }


    fun setShapeBackground(view: View?) {
        view ?: return
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.cornerRadius = mCornersRadius
        if (mGradientStartColor != -1 && mGradientEndColor != -1) {
            if (mGradientCenterColor != -1) {
                gradientDrawable.colors = intArrayOf(
                    mGradientStartColor,
                    mGradientCenterColor,
                    mGradientEndColor
                )
            } else {
                gradientDrawable.colors = intArrayOf(
                    mGradientStartColor,
                    mGradientEndColor
                )
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
                gradientDrawable.orientation = changeOrientation(mOrientation)
            }
        } else {
            gradientDrawable.setColor(mSolidColor)

        }
        view.background = gradientDrawable
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        setShapeBackground(child)
        super.addView(child, params)
    }

    private fun changeOrientation(angle: Int): GradientDrawable.Orientation {
        return when (angle) {
            0 -> GradientDrawable.Orientation.LEFT_RIGHT
            45 -> GradientDrawable.Orientation.BL_TR
            90 -> GradientDrawable.Orientation.BOTTOM_TOP
            135 -> GradientDrawable.Orientation.BR_TL
            180 -> GradientDrawable.Orientation.RIGHT_LEFT
            225 -> GradientDrawable.Orientation.TR_BL
            270 -> GradientDrawable.Orientation.TOP_BOTTOM
            315 -> GradientDrawable.Orientation.TL_BR
            else -> GradientDrawable.Orientation.LEFT_RIGHT
        }
    }

}