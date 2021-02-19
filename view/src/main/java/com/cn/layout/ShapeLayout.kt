package com.cn.layout

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.cn.architecture.R
import java.lang.RuntimeException
import kotlin.math.min

class ShapeLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    /**
     * 圆角权重
     */
    private var mCornersRadiusWeight = -1

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


    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ShapeLayout)

        mCornersRadiusWeight = a.getInt(R.styleable.ShapeLayout_corners_radius_weight, -1)
        if (mCornersRadiusWeight == -1) {
            mCornersRadius = a.getDimension(R.styleable.ShapeLayout_corners_radius, 0F)
            mLeftCornersRadius =
                a.getDimension(R.styleable.ShapeLayout_corners_left_radius, mCornersRadius)
            mTopCornersRadius =
                a.getDimension(R.styleable.ShapeLayout_corners_top_radius, mCornersRadius)
            mRightCornersRadius =
                a.getDimension(R.styleable.ShapeLayout_corners_right_radius, mCornersRadius)
            mBottomCornersRadius =
                a.getDimension(R.styleable.ShapeLayout_corners_bottom_radius, mCornersRadius)
        }
        mSolidColor = a.getColor(R.styleable.ShapeLayout_solid_color, -1)
        mGradientAngle = getGradientAngle(a.getInt(R.styleable.ShapeLayout_gradient_angle, 0))
        mGradientType = getGradientType(a.getInt(R.styleable.ShapeLayout_gradient_type, 0))
        mGradientStartColor = a.getColor(R.styleable.ShapeLayout_gradient_start_color, -1)
        mGradientCenterColor = a.getColor(R.styleable.ShapeLayout_gradient_center_color, -1)
        mGradientEndColor = a.getColor(R.styleable.ShapeLayout_gradient_end_color, -1)
        mGradientCenterX = a.getFloat(R.styleable.ShapeLayout_gradient_center_x, 0.5F)
        mGradientCenterY = a.getFloat(R.styleable.ShapeLayout_gradient_center_y, 0.5F)
        mGradientRadius = a.getFraction(R.styleable.ShapeLayout_gradient_radius, 1, 1, 0.5F)
        mStrokeColor = a.getColor(R.styleable.ShapeLayout_stroke_color, -1)
        mStrokeDashWidth = a.getDimension(R.styleable.ShapeLayout_stroke_width, 0F)
        mStrokeDashGap = a.getDimension(R.styleable.ShapeLayout_stroke_dash_gap, 0F)
        mStrokeDashWidth = a.getDimension(R.styleable.ShapeLayout_stroke_dash_width, 0F)
        a.recycle()
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        if (childCount > 0) {
            throw  RuntimeException("u only can add one view in this group")
        }
        super.addView(child, params)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount > 0) {
            val child = getChildAt(0)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            if (mCornersRadiusWeight == 1) {
                mCornersRadius = child.measuredHeight / 2.toFloat()
                mLeftCornersRadius = mCornersRadius
                mTopCornersRadius = mCornersRadius
                mRightCornersRadius = mCornersRadius
                mBottomCornersRadius = mCornersRadius
            } else if (mCornersRadiusWeight == 0) {
                mCornersRadius = child.measuredWidth / 2.toFloat()
                mLeftCornersRadius = mCornersRadius
                mTopCornersRadius = mCornersRadius
                mRightCornersRadius = mCornersRadius
                mBottomCornersRadius = mCornersRadius
            }
            setShapeBackground(child)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun setShapeBackground(view: View) {
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
        view.background = gradientDrawable
    }

    private fun getGradientType(result: Int): Int {
        return when (result) {
            0 -> GradientDrawable.LINEAR_GRADIENT
            1 -> GradientDrawable.RADIAL_GRADIENT
            2 -> GradientDrawable.SWEEP_GRADIENT
            else -> GradientDrawable.LINEAR_GRADIENT
        }
    }

    private fun getGradientAngle(result: Int): GradientDrawable.Orientation {
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
