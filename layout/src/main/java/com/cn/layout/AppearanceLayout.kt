package com.cn.layout

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.w3c.dom.Text
import kotlin.math.min

class AppearanceLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {
    private var mBindId: Int = -1
    private val mBindMeasureHeight = 0
    private val mBindMeasureWidth = 0
    private var mBindSelected = false
    val mStateListDrawable = StateListDrawable()

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.AppearanceLayout)
        mBindId = ta.getResourceId(R.styleable.AppearanceLayout_bind_id, -1)
        mBindSelected = ta.getBoolean(R.styleable.AppearanceLayout_isSelected, false)
        ta.recycle()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    /**
     * 避免重复设置背景
     */
    private fun isNeedMeasureAgain(child: View): Boolean {
        if (child.measuredWidth == mBindMeasureWidth && child.measuredHeight == mBindMeasureHeight) {
            return false
        }
        return true
    }


    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        if (mBindId != -1) {
            val bindView = (this.parent as? ViewGroup)?.findViewById<View>(mBindId) ?: return
            if (isNeedMeasureAgain(bindView)) {
                val a = Array(childCount) { IntArray(1) }
                val b = IntArray(childCount)
                val stateListDrawable = StateListDrawable()
                for (i in 0 until childCount) {
                    val child = getChildAt(i) as Appearance
                    val shapeState = intArrayOf(child.getShapeState())
                    val shapeDrawable = child.getShapeDrawable(bindView)
                    stateListDrawable.addState(
                        shapeState,
                        shapeDrawable
                    )
                    a[i] = shapeState
                    val shapeColor = child.getShapeColor()
                    b[i] = shapeColor
                }
                if (bindView is TextView) {
                    if (b.find { it == -1 } == null) {
                        bindView.setTextColor(ColorStateList(a, b))
                    }
                }
                bindView.background = stateListDrawable
                bindView.isSelected = mBindSelected
            }
        }
        setMeasuredDimension(0, 0)
    }

    override fun addView(child: View?, params: LayoutParams?) {
        if (child !is Appearance) {
            return
        }
        super.addView(child, params)
    }

}

