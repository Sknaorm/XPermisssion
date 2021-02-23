package com.cn.layout

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class OneShape(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val mBindView by lazy { (this.parent as ViewGroup).findViewById<View>(mBindId) }
    private var mBindId: Int = -1
    private var mDrawable: Drawable? = null


    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.OneShape)
        mBindId = ta.getResourceId(R.styleable.OneShape_bind_id, -1)
        mDrawable = getDrawableByAttrs(ta)
        ta.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mBindView.background = mDrawable
    }

    fun getDrawableByAttrs(ta: TypedArray): Drawable {
        val mCornersRadius = ta.getDimension(R.styleable.OneShape_shape_corners_radius, 0F)
        val mLeftCornersRadius =
            ta.getDimension(R.styleable.OneShape_shape_corners_left_radius, mCornersRadius)
        val mTopCornersRadius =
            ta.getDimension(R.styleable.OneShape_shape_corners_top_radius, mCornersRadius)
        val mRightCornersRadius =
            ta.getDimension(R.styleable.OneShape_shape_corners_right_radius, mCornersRadius)
        val mBottomCornersRadius =
            ta.getDimension(R.styleable.OneShape_shape_corners_bottom_radius, mCornersRadius)
        val mSolidColor = ta.getColor(R.styleable.OneShape_shape_solid_color, -1)
        val mGradientAngle = AppearanceUtils.getGradientAngle(
            ta.getInt(
                R.styleable.OneShape_shape_gradient_angle,
                0
            )
        )
        val mGradientType = AppearanceUtils.getGradientType(
            ta.getInt(
                R.styleable.OneShape_shape_gradient_type,
                0
            )
        )
        val mGradientStartColor =
            ta.getColor(R.styleable.OneShape_shape_gradient_start_color, -1)
        val mGradientCenterColor =
            ta.getColor(R.styleable.OneShape_shape_gradient_center_color, -1)
        val mGradientEndColor = ta.getColor(R.styleable.OneShape_shape_gradient_end_color, -1)
        val mGradientCenterX = ta.getFloat(R.styleable.OneShape_shape_gradient_center_x, 0.5F)
        val mGradientCenterY = ta.getFloat(R.styleable.OneShape_shape_gradient_center_y, 0.5F)
        val mGradientRadius = ta.getDimension(R.styleable.OneShape_shape_gradient_radius, 0F)
        val mStrokeColor = ta.getColor(R.styleable.OneShape_shape_stroke_color, -1)
        val mStrokeWidth = ta.getDimension(R.styleable.OneShape_shape_stroke_width, 0F)
        val mStrokeDashGap = ta.getDimension(R.styleable.OneShape_shape_stroke_dash_gap, 0F)
        val mStrokeDashWidth =
            ta.getDimension(R.styleable.OneShape_shape_stroke_dash_width, 0F)
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(0, 0)
    }

}

