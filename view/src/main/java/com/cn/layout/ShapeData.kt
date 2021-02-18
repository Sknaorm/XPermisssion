package com.cn.layout

import android.graphics.drawable.GradientDrawable

class ShapeData {
    /**
     * 圆角
     */
    var cornersRadius = 0F

    /**
     * 左圆角
     */
    var leftCornersRadius = 0F

    /**
     * 上圆角
     */
    var topCornersRadius = 0F

    /**
     * 右圆角
     */
    var rightCornersRadius = 0F

    /**
     * 下圆角
     */
    var bottomCornersRadius = 0F

    /**
     * 填充色
     */
    var solidColor = -1

    /**
     * 渐变开始色
     */
    var gradientStartColor = -1

    /**
     * 渐变中间色
     */
    var gradientCenterColor = -1

    /**
     * 渐变结束色
     */
    var gradientEndColor = -1

    /**
     * 渐变开始点角度(GradientType.LINEAR_GRADIENT)
     */
    var gradientAngle = GradientAngle.LEFT_RIGHT.type

    /**
     * 渐变类型
     */
    var gradientType = GradientType.LINEAR_GRADIENT.type

    /**
     * 渐变中心X(GradientType.RADIAL_GRADIENT||GradientType.SWEEP_GRADIENT)
     */
    var gradientCenterX = 0.5F

    /**
     * 渐变中心Y(GradientType.RADIAL_GRADIENT||GradientType.SWEEP_GRADIENT)
     */
    var gradientCenterY = 0.5F

    /**
     * 渐变半径(GradientType.RADIAL_GRADIENT)
     */
    var gradientRadius = 0F

    /**
     * 边框色
     */
    var strokeColor = -1

    /**
     * 边框宽度
     */
    var strokeWidth = 0F

    /**
     * 边框虚线间隔
     */
    var strokeDashGap = 0F

    /**
     * 边框虚线宽度
     */
    var strokeDashWidth = 0F

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

    enum class GradientType(val type: Int) {
        LINEAR_GRADIENT(GradientDrawable.LINEAR_GRADIENT),
        RADIAL_GRADIENT(GradientDrawable.RADIAL_GRADIENT),
        SWEEP_GRADIENT(GradientDrawable.SWEEP_GRADIENT)
    }

    enum class GradientAngle(val type: GradientDrawable.Orientation, val angel: Int) {
        LEFT_RIGHT(GradientDrawable.Orientation.LEFT_RIGHT, 0),
        BL_TR(GradientDrawable.Orientation.BL_TR, 45),
        BOTTOM_TOP(GradientDrawable.Orientation.BOTTOM_TOP, 90),
        BR_TL(GradientDrawable.Orientation.BR_TL, 135),
        RIGHT_LEFT(GradientDrawable.Orientation.RIGHT_LEFT, 180),
        TR_BL(GradientDrawable.Orientation.TR_BL, 225),
        TOP_BOTTOM(GradientDrawable.Orientation.TOP_BOTTOM, 270),
        TL_BR(GradientDrawable.Orientation.TL_BR, 315),
    }
}