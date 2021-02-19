package com.cn.layout

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.cn.architecture.R
import kotlin.math.min


class ShapeLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var mCornersRadius = 0F
    private var mLeftCornersRadius = 0F
    private var mTopCornersRadius = 0F
    private var mRightCornersRadius = 0F
    private var mBottomCornersRadius = 0F
    private var mSolidColor = -1
    private var mGradientStartColor = -1
    private var mGradientCenterColor = -1
    private var mGradientEndColor = -1
    private var mGradientAngle = 0
    private var mGradientType = 0
    private var mGradientCenterX = 0.5F
    private var mGradientCenterY = 0.5F
    private var mGradientRadius = 0F
    private var mStrokeColor = -1
    private var mStrokeWidth = 0F
    private var mStrokeDashGap = 0F
    private var mStrokeDashWidth = 0F

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ShapeLayout)
        mCornersRadius = a.getDimension(R.styleable.ShapeLayout_corners_radius, 0F)
        mLeftCornersRadius =
            a.getDimension(R.styleable.ShapeLayout_corners_left_radius, mCornersRadius)
        mTopCornersRadius =
            a.getDimension(R.styleable.ShapeLayout_corners_top_radius, mCornersRadius)
        mRightCornersRadius =
            a.getDimension(R.styleable.ShapeLayout_corners_right_radius, mCornersRadius)
        mBottomCornersRadius =
            a.getDimension(R.styleable.ShapeLayout_corners_bottom_radius, mCornersRadius)
        mSolidColor = a.getColor(R.styleable.ShapeLayout_solid_color, -1)
        //Gradient
        mGradientAngle = a.getInt(R.styleable.ShapeLayout_gradient_angle, 0)
        mGradientType = a.getInt(R.styleable.ShapeLayout_gradient_type, 0)
        mGradientStartColor = a.getColor(R.styleable.ShapeLayout_gradient_start_color, -1)
        mGradientCenterColor = a.getColor(R.styleable.ShapeLayout_gradient_center_color, -1)
        mGradientEndColor = a.getColor(R.styleable.ShapeLayout_gradient_end_color, -1)
        mGradientCenterX = a.getFloat(R.styleable.ShapeLayout_gradient_center_x, 0.5F)
        mGradientCenterY = a.getFloat(R.styleable.ShapeLayout_gradient_center_y, 0.5F)
        mGradientRadius = a.getFraction(R.styleable.ShapeLayout_gradient_radius, 1, 1, 0.5F)
        mStrokeColor = a.getColor(R.styleable.ShapeLayout_stroke_color, -1)
        mStrokeWidth = a.getDimension(R.styleable.ShapeLayout_stroke_width, 0F)
        mStrokeDashGap = a.getDimension(R.styleable.ShapeLayout_stroke_dash_gap, 0F)
        mStrokeDashWidth = a.getDimension(R.styleable.ShapeLayout_stroke_dash_width, 0F)
        a.recycle()
    }


    private fun setShapeBackground(view: View?) {
        view ?: return
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.cornerRadii = floatArrayOf(
            mTopCornersRadius,
            mTopCornersRadius,
            mRightCornersRadius,
            mRightCornersRadius,
            mBottomCornersRadius,
            mBottomCornersRadius,
            mLeftCornersRadius,
            mLeftCornersRadius
        )
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
            gradientDrawable.gradientType = getGradientType(mGradientType)
            when (getGradientType(mGradientType)) {
                GradientDrawable.LINEAR_GRADIENT -> {
                    gradientDrawable.orientation = getGradientAngle(mGradientAngle)
                }
                GradientDrawable.SWEEP_GRADIENT -> {
                    gradientDrawable.setGradientCenter(mGradientCenterX, mGradientCenterY)
                }
                GradientDrawable.RADIAL_GRADIENT -> {
                    gradientDrawable.setGradientCenter(mGradientCenterX, mGradientCenterY)
                    gradientDrawable.gradientRadius =
                        min(view.measuredHeight, view.measuredWidth) * mGradientRadius
                }
            }
        } else {
            gradientDrawable.setColor(mSolidColor)
        }
        if (mStrokeWidth != 0F && mStrokeColor != -1) {
            gradientDrawable.setStroke(
                mStrokeWidth.toInt(),
                mStrokeColor,
                mStrokeDashWidth,
                mStrokeDashGap
            )
        }
        view.background = gradientDrawable
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount == 1) {
            val child = getChildAt(0)
            if (child.measuredHeight == 0 && child.measuredWidth == 0) {
                measureChildren(widthMeasureSpec, heightMeasureSpec)
                setShapeBackground(getChildAt(0))
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        if (childCount == 1) throw IllegalAccessException("only have one childView")
        super.addView(child, params)
    }

    private fun getGradientAngle(angle: Int): GradientDrawable.Orientation {
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

    private fun getGradientType(type: Int): Int {
        return when (type) {
            0 -> GradientDrawable.LINEAR_GRADIENT
            1 -> GradientDrawable.RADIAL_GRADIENT
            2 -> GradientDrawable.SWEEP_GRADIENT
            else -> GradientDrawable.LINEAR_GRADIENT
        }
    }
}