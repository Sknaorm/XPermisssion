package com.cn.layout

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import com.cn.architecture.R
import kotlin.math.min

object ShapeUtils {
    private fun setShapeBackground(view: View, shapeData: ShapeData) {
        shapeData.apply {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.shape = GradientDrawable.RECTANGLE
            gradientDrawable.cornerRadii = floatArrayOf(
                topCornersRadius,
                topCornersRadius,
                rightCornersRadius,
                rightCornersRadius,
                bottomCornersRadius,
                bottomCornersRadius,
                leftCornersRadius,
                leftCornersRadius
            )
            if (gradientStartColor != -1 && gradientEndColor != -1) {
                if (gradientCenterColor != -1) {
                    gradientDrawable.colors = intArrayOf(
                        gradientStartColor,
                        gradientCenterColor,
                        gradientEndColor
                    )
                } else {
                    gradientDrawable.colors = intArrayOf(
                        gradientStartColor,
                        gradientEndColor
                    )
                }
                gradientDrawable.gradientType = gradientType
                when (gradientType) {
                    GradientDrawable.LINEAR_GRADIENT -> {
                        gradientDrawable.orientation = gradientAngle
                    }
                    GradientDrawable.SWEEP_GRADIENT -> {
                        gradientDrawable.setGradientCenter(gradientCenterX, gradientCenterY)
                    }
                    GradientDrawable.RADIAL_GRADIENT -> {
                        gradientDrawable.setGradientCenter(gradientCenterX, gradientCenterY)
                        gradientDrawable.gradientRadius =
                            min(view.measuredHeight, view.measuredWidth) * gradientRadius
                    }
                }
            } else {
                gradientDrawable.setColor(solidColor)
            }
            if (strokeWidth != 0F && strokeColor != -1) {
                gradientDrawable.setStroke(
                    strokeWidth.toInt(),
                    strokeColor,
                    strokeDashWidth,
                    strokeDashGap
                )
            }
            view.background = gradientDrawable
        }

    }

    fun getAttrByShapeView(context: Context, view: View, attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView)
        val shapeData = ShapeData()
        shapeData.cornersRadius = a.getDimension(R.styleable.ShapeTextView_corners_radius, 0F)
        shapeData.leftCornersRadius =
            a.getDimension(R.styleable.ShapeTextView_corners_left_radius, shapeData.cornersRadius)
        shapeData.topCornersRadius =
            a.getDimension(R.styleable.ShapeTextView_corners_top_radius, shapeData.cornersRadius)
        shapeData.rightCornersRadius =
            a.getDimension(R.styleable.ShapeTextView_corners_right_radius, shapeData.cornersRadius)
        shapeData.bottomCornersRadius =
            a.getDimension(R.styleable.ShapeTextView_corners_bottom_radius, shapeData.cornersRadius)
        shapeData.solidColor = a.getColor(R.styleable.ShapeTextView_solid_color, -1)
        shapeData.gradientAngle =
            shapeData.getGradientAngle(a.getInt(R.styleable.ShapeTextView_gradient_angle, 0))
        shapeData.gradientType =
            shapeData.getGradientType(a.getInt(R.styleable.ShapeTextView_gradient_type, 0))
        shapeData.gradientStartColor =
            a.getColor(R.styleable.ShapeTextView_gradient_start_color, -1)
        shapeData.gradientCenterColor =
            a.getColor(R.styleable.ShapeTextView_gradient_center_color, -1)
        shapeData.gradientEndColor = a.getColor(R.styleable.ShapeTextView_gradient_end_color, -1)
        shapeData.gradientCenterX = a.getFloat(R.styleable.ShapeTextView_gradient_center_x, 0.5F)
        shapeData.gradientCenterY = a.getFloat(R.styleable.ShapeTextView_gradient_center_y, 0.5F)
        shapeData.gradientRadius =
            a.getFraction(R.styleable.ShapeTextView_gradient_radius, 1, 1, 0.5F)
        shapeData.strokeColor = a.getColor(R.styleable.ShapeTextView_stroke_color, -1)
        shapeData.strokeDashWidth = a.getDimension(R.styleable.ShapeTextView_stroke_width, 0F)
        shapeData.strokeDashGap = a.getDimension(R.styleable.ShapeTextView_stroke_dash_gap, 0F)
        shapeData.strokeDashWidth = a.getDimension(R.styleable.ShapeTextView_stroke_dash_width, 0F)
        setShapeBackground(view, shapeData)
        a.recycle()
    }
}
