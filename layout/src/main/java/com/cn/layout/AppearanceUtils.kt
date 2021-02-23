package com.cn.layout

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable

object AppearanceUtils {
    fun getDrawableByAttrs(context: Context, id: Int): Drawable {
        val ta = context.obtainStyledAttributes(id, R.styleable.State)
        val mCornersRadius = ta.getDimension(R.styleable.State_shape_corners_radius, 0F)
        val mLeftCornersRadius =
            ta.getDimension(R.styleable.State_shape_corners_left_radius, mCornersRadius)
        val mTopCornersRadius =
            ta.getDimension(R.styleable.State_shape_corners_top_radius, mCornersRadius)
        val mRightCornersRadius =
            ta.getDimension(R.styleable.State_shape_corners_right_radius, mCornersRadius)
        val mBottomCornersRadius =
            ta.getDimension(R.styleable.State_shape_corners_bottom_radius, mCornersRadius)
        val mSolidColor = ta.getColor(R.styleable.State_shape_solid_color, -1)
        val mGradientAngle = getGradientAngle(ta.getInt(R.styleable.State_shape_gradient_angle, 0))
        val mGradientType = getGradientType(ta.getInt(R.styleable.State_shape_gradient_type, 0))
        val mGradientStartColor = ta.getColor(R.styleable.State_shape_gradient_start_color, -1)
        val mGradientCenterColor = ta.getColor(R.styleable.State_shape_gradient_center_color, -1)
        val mGradientEndColor = ta.getColor(R.styleable.State_shape_gradient_end_color, -1)
        val mGradientCenterX = ta.getFloat(R.styleable.State_shape_gradient_center_x, 0.5F)
        val mGradientCenterY = ta.getFloat(R.styleable.State_shape_gradient_center_y, 0.5F)
        val mGradientRadius = ta.getDimension(R.styleable.State_shape_gradient_radius, 0F)
        val mStrokeColor = ta.getColor(R.styleable.State_shape_stroke_color, -1)
        val mStrokeWidth = ta.getDimension(R.styleable.State_shape_stroke_width, 0F)
        val mStrokeDashGap = ta.getDimension(R.styleable.State_shape_stroke_dash_gap, 0F)
        val mStrokeDashWidth = ta.getDimension(R.styleable.State_shape_stroke_dash_width, 0F)
        ta.recycle()
        return GradientDrawable().run {
            this.shape = GradientDrawable.RECTANGLE
            this.cornerRadii = floatArrayOf(
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
                    this.colors = intArrayOf(
                        mGradientStartColor,
                        mGradientCenterColor,
                        mGradientEndColor
                    )
                } else {
                    this.colors = intArrayOf(
                        mGradientStartColor,
                        mGradientEndColor
                    )
                }
                this.gradientType = mGradientType
                when (mGradientType) {
                    GradientDrawable.LINEAR_GRADIENT -> {
                        this.orientation = mGradientAngle
                    }
                    GradientDrawable.SWEEP_GRADIENT -> {
                        this.setGradientCenter(mGradientCenterX, mGradientCenterY)
                    }
                    GradientDrawable.RADIAL_GRADIENT -> {
                        this.setGradientCenter(mGradientCenterX, mGradientCenterY)
                        this.gradientRadius = mGradientRadius
                    }
                }
            } else {
                this.setColor(mSolidColor)
            }
            if (mStrokeWidth != 0F && mStrokeColor != -1) {
                this.setStroke(
                    mStrokeWidth.toInt(),
                    mStrokeColor,
                    mStrokeDashWidth,
                    mStrokeDashGap
                )
            }
            this
        }
    }

     fun getGradientType(result: Int): Int {
        return when (result) {
            0 -> GradientDrawable.LINEAR_GRADIENT
            1 -> GradientDrawable.RADIAL_GRADIENT
            2 -> GradientDrawable.SWEEP_GRADIENT
            else -> GradientDrawable.LINEAR_GRADIENT
        }
    }

     fun getGradientAngle(result: Int): GradientDrawable.Orientation {
        return when (result) {
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