package com.cn.layout

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

class StateLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var isChildSelected = false
    private val mChildMeasureHeight = 0
    private val mChildMeasureWidth = 0
    private var mDefaultBackground: ShapeData? = null
    private var mSelectedBackground: ShapeData? = null
    private var mUnselectedBackground: ShapeData? = null
        get() {
            if (field == null) {
                field = mDefaultBackground
            }
            return field
        }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.StateLayout)
        isChildSelected = ta.getBoolean(R.styleable.StateLayout_selected, false)
        val default = ta.getResourceId(R.styleable.StateLayout_default_appearance, -1)
        val selected = ta.getResourceId(R.styleable.StateLayout_selected_appearance, -1)
        val unselected = ta.getResourceId(R.styleable.StateLayout_unselected_appearance, -1)
        mDefaultBackground = ShapeData.createByAttr(context, attrs)
        if (default != -1) {
            mDefaultBackground = ShapeData.createByAttr(context, default)
        }
        if (selected != -1) {
            mSelectedBackground = ShapeData.createByAttr(context, selected)
        }
        if (unselected != -1) {
            mUnselectedBackground = ShapeData.createByAttr(context, unselected)
        }
        ta.recycle()
    }

    /**
     * 避免重复设置背景
     */
    private fun isNeedMeasureAgain(child: View): Boolean {
        if (child.measuredWidth == mChildMeasureWidth && child.measuredHeight == mChildMeasureHeight) {
            return false
        }
        return true
    }

    /**
     * 有些属性需要测量之后才可以得到
     * 如果迷人背景不为空，计算出圆角之后在设置背景
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount > 0) {
            val child = getChildAt(0)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            if (isNeedMeasureAgain(child)) {
                setBackground(child)
                if (child is TextView) {
                    setChildTextColor(child)
                }
                child.isSelected = isChildSelected
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * 目前只实现了 selector 与 default
     */
    private fun setBackground(child: View) {
        val default = mDefaultBackground ?: return
        if (mSelectedBackground == null) {
            child.background = default.getBackground(child)
            return
        }
        val drawable = StateListDrawable().apply {
            addState(
                intArrayOf(android.R.attr.state_selected),
                mSelectedBackground?.getBackground(child)
            )
            addState(
                intArrayOf(-android.R.attr.state_selected),
                mUnselectedBackground?.getBackground(child)
            )
            addState(
                intArrayOf(android.R.attr.state_empty),
                mDefaultBackground?.getBackground(child)
            )
        }
        child.background = drawable
    }

    private fun setChildTextColor(child: TextView) {
        val default = mDefaultBackground ?: return
        if (mSelectedBackground == null) {
            child.setTextColor(default.getTextColor())
            return
        }
        val color = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_selected),
                intArrayOf(-android.R.attr.state_selected),
                intArrayOf(android.R.attr.state_empty)
            ),
            intArrayOf(
                mSelectedBackground?.getTextColor() ?: Color.BLACK,
                mUnselectedBackground?.getTextColor() ?: Color.BLACK,
                mDefaultBackground?.getTextColor() ?: Color.BLACK
            )
        )
        child.setTextColor(color)
    }
}

