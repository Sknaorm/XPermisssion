package com.cn.layout

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import kotlin.math.min


class ShapeData {

    companion object {
        fun createByAttr(context: Context, attrs: Int): ShapeData {
            return ShapeData().apply {
                val ta = context.obtainStyledAttributes(attrs, R.styleable.State)
                mCornersRadiusWeight = ta.getInt(R.styleable.State_corners_radius_weight, -1)
                if (mCornersRadiusWeight == -1) {
                    mCornersRadius = ta.getDimension(R.styleable.State_corners_radius, 0F)
                    mLeftCornersRadius =
                        ta.getDimension(R.styleable.State_corners_left_radius, mCornersRadius)
                    mTopCornersRadius =
                        ta.getDimension(R.styleable.State_corners_top_radius, mCornersRadius)
                    mRightCornersRadius =
                        ta.getDimension(R.styleable.State_corners_right_radius, mCornersRadius)
                    mBottomCornersRadius =
                        ta.getDimension(R.styleable.State_corners_bottom_radius, mCornersRadius)
                }
                mSolidColor = ta.getColor(R.styleable.State_solid_color, -1)
                mSolidTextColor = ta.getColor(R.styleable.State_solid_text_color, -1)
                mGradientAngle = getGradientAngle(ta.getInt(R.styleable.State_gradient_angle, 0))
                mGradientType = getGradientType(ta.getInt(R.styleable.State_gradient_type, 0))
                mGradientStartColor = ta.getColor(R.styleable.State_gradient_start_color, -1)
                mGradientCenterColor = ta.getColor(R.styleable.State_gradient_center_color, -1)
                mGradientEndColor = ta.getColor(R.styleable.State_gradient_end_color, -1)
                mGradientCenterX = ta.getFloat(R.styleable.State_gradient_center_x, 0.5F)
                mGradientCenterY = ta.getFloat(R.styleable.State_gradient_center_y, 0.5F)
                mGradientRadius = ta.getFraction(R.styleable.State_gradient_radius, 1, 1, 0.5F)
                mStrokeWidth= ta.getDimension(R.styleable.State_stroke_width ,0F)
                mStrokeColor = ta.getColor(R.styleable.State_stroke_color, -1)
                mStrokeDashWidth = ta.getDimension(R.styleable.State_stroke_width, 0F)
                mStrokeDashGap = ta.getDimension(R.styleable.State_stroke_dash_gap, 0F)
                mStrokeDashWidth = ta.getDimension(R.styleable.State_stroke_dash_width, 0F)
                ta.recycle()
            }
        }
        fun createByAttr(context: Context, attrs: AttributeSet): ShapeData {
            return ShapeData().apply {
                val ta = context.obtainStyledAttributes(attrs, R.styleable.StateLayout)
                mCornersRadiusWeight = ta.getInt(R.styleable.StateLayout_corners_radius_weight, -1)
                if (mCornersRadiusWeight == -1) {
                    mCornersRadius = ta.getDimension(R.styleable.StateLayout_corners_radius, 0F)
                    mLeftCornersRadius =
                        ta.getDimension(R.styleable.StateLayout_corners_left_radius, mCornersRadius)
                    mTopCornersRadius =
                        ta.getDimension(R.styleable.StateLayout_corners_top_radius, mCornersRadius)
                    mRightCornersRadius =
                        ta.getDimension(R.styleable.StateLayout_corners_right_radius, mCornersRadius)
                    mBottomCornersRadius =
                        ta.getDimension(R.styleable.StateLayout_corners_bottom_radius, mCornersRadius)
                }
                mSolidColor = ta.getColor(R.styleable.StateLayout_solid_color, -1)
                mSolidTextColor = ta.getColor(R.styleable.StateLayout_solid_text_color, -1)
                mGradientAngle = getGradientAngle(ta.getInt(R.styleable.StateLayout_gradient_angle, 0))
                mGradientType = getGradientType(ta.getInt(R.styleable.StateLayout_gradient_type, 0))
                mGradientStartColor = ta.getColor(R.styleable.StateLayout_gradient_start_color, -1)
                mGradientCenterColor = ta.getColor(R.styleable.StateLayout_gradient_center_color, -1)
                mGradientEndColor = ta.getColor(R.styleable.StateLayout_gradient_end_color, -1)
                mGradientCenterX = ta.getFloat(R.styleable.StateLayout_gradient_center_x, 0.5F)
                mGradientCenterY = ta.getFloat(R.styleable.StateLayout_gradient_center_y, 0.5F)
                mGradientRadius = ta.getFraction(R.styleable.StateLayout_gradient_radius, 1, 1, 0.5F)
                mStrokeWidth= ta.getDimension(R.styleable.StateLayout_stroke_width ,0F)
                mStrokeColor = ta.getColor(R.styleable.StateLayout_stroke_color, -1)
                mStrokeDashWidth = ta.getDimension(R.styleable.StateLayout_stroke_width, 0F)
                mStrokeDashGap = ta.getDimension(R.styleable.StateLayout_stroke_dash_gap, 0F)
                mStrokeDashWidth = ta.getDimension(R.styleable.StateLayout_stroke_dash_width, 0F)
                ta.recycle()
            }
        }
    }

    /**
     * 内填充文字颜色
     */
    var mSolidTextColor = -1

    /**
     * 圆角权重
     */
    var mCornersRadiusWeight = -1

    /**
     * 圆角
     */
    var mCornersRadius = 0F

    /**
     * 左圆角
     */
    var mLeftCornersRadius = 0F

    /**
     * 上圆角
     */
    var mTopCornersRadius = 0F

    /**
     * 右圆角
     */
    var mRightCornersRadius = 0F

    /**
     * 下圆角
     */
    var mBottomCornersRadius = 0F

    /**
     * 填充色
     */
    var mSolidColor = -1

    /**
     * 渐变开始色
     */
    var mGradientStartColor = -1

    /**
     * 渐变中间色
     */
    var mGradientCenterColor = -1

    /**
     * 渐变结束色
     */
    var mGradientEndColor = -1

    /**
     * 渐变开始点角度(GradientType.LINEAR_GRADIENT)
     */
    var mGradientAngle = GradientDrawable.Orientation.LEFT_RIGHT

    /**
     * 渐变类型
     */
    var mGradientType = GradientDrawable.LINEAR_GRADIENT

    /**
     * 渐变中心X(GradientType.RADIAL_GRADIENT||GradientType.SWEEP_GRADIENT)
     */
    var mGradientCenterX = 0.5F

    /**
     * 渐变中心Y(GradientType.RADIAL_GRADIENT||GradientType.SWEEP_GRADIENT)
     */
    var mGradientCenterY = 0.5F

    /**
     * 渐变半径(GradientType.RADIAL_GRADIENT)
     */
    var mGradientRadius = 0F

    /**
     * 边框色
     */
    var mStrokeColor = -1

    /**
     * 边框宽度
     */
    var mStrokeWidth = 0F

    /**
     * 边框虚线间隔
     */
    var mStrokeDashGap = 0F

    /**
     * 边框虚线宽度
     */
    var mStrokeDashWidth = 0F
    internal fun getGradientType(result: Int): Int {
        return when (result) {
            0 -> GradientDrawable.LINEAR_GRADIENT
            1 -> GradientDrawable.RADIAL_GRADIENT
            2 -> GradientDrawable.SWEEP_GRADIENT
            else -> GradientDrawable.LINEAR_GRADIENT
        }
    }

    internal fun getGradientAngle(result: Int): GradientDrawable.Orientation {
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

    internal fun getBackground(view: View): GradientDrawable {
        if (view.measuredHeight == 0 || view.measuredWidth == 0) {
            throw IllegalArgumentException("measuredHeight and  measuredWidth must can not be 0")
        }
        val gradientDrawable = GradientDrawable()
        if (mCornersRadiusWeight == 1) {
            mCornersRadius = view.measuredHeight / 2.toFloat()
            mLeftCornersRadius = mCornersRadius
            mTopCornersRadius = mCornersRadius
            mRightCornersRadius = mCornersRadius
            mBottomCornersRadius = mCornersRadius
        } else if (mCornersRadiusWeight == 0) {
            mCornersRadius = view.measuredWidth / 2.toFloat()
            mLeftCornersRadius = mCornersRadius
            mTopCornersRadius = mCornersRadius
            mRightCornersRadius = mCornersRadius
            mBottomCornersRadius = mCornersRadius
        }
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
            gradientDrawable.gradientType = mGradientType
            when (mGradientType) {
                GradientDrawable.LINEAR_GRADIENT -> {
                    gradientDrawable.orientation = mGradientAngle
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
        return gradientDrawable
    }

    internal fun getTextColor(): Int {
        return mSolidTextColor
    }
}