package com.cn.layout

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Appearance(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val mBindView by lazy { (this.parent as ViewGroup).findViewById<View>(mBindId) }
    private var mBindId: Int = -1
    private var isBindViewSelected = false
    private var mSelectedDrawable: Drawable? = null
    private var mDefaultDrawable: Drawable? = null
    private var mSelectedTextColor: ColorStateList? = null
    private var mDefaultTextColor: ColorStateList? = null


    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.Appearance)
        mBindId = ta.getResourceId(R.styleable.Appearance_bind_id, -1)
        isBindViewSelected = ta.getBoolean(R.styleable.Appearance_isSelected, false)
        val selectedTextColor = ta.getColor(R.styleable.Appearance_selected_text_color, -1)
        if (selectedTextColor != -1) {
            mSelectedTextColor = ColorStateList.valueOf(selectedTextColor)
        }
        val defaultTextColor = ta.getColor(R.styleable.Appearance_default_text_color, -1)
        if (defaultTextColor != -1) {
            mDefaultTextColor = ColorStateList.valueOf(defaultTextColor)
        }
        mSelectedDrawable = ta.getDrawable(R.styleable.Appearance_selected_drawable)
        val selectedStyle = ta.getResourceId(R.styleable.Appearance_selected_style, -1)
        if (selectedStyle != -1) {
            mSelectedDrawable = AppearanceUtils.getDrawableByAttrs(context, selectedStyle)
        }
        //style文件
        mDefaultDrawable = ta.getDrawable(R.styleable.Appearance_default_drawable)
        val defaultStyle = ta.getResourceId(R.styleable.Appearance_default_style, -1)
        if (defaultStyle != -1) {
            mDefaultDrawable = AppearanceUtils.getDrawableByAttrs(context, defaultStyle)
        }
        ta.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setBindViewAppearance()
    }

    private fun setBindViewAppearance() {
        if (mDefaultDrawable != null) {
            if (mSelectedDrawable == null) {
                mBindView.background = mDefaultDrawable
            } else {
                val stateListDrawable = StateListDrawable()
                stateListDrawable.addState(
                    intArrayOf(android.R.attr.state_selected),
                    mSelectedDrawable
                )
                stateListDrawable.addState(
                    intArrayOf(-android.R.attr.state_selected),
                    mDefaultDrawable
                )
                mBindView.background = stateListDrawable
            }
        }
        if (mBindView is TextView && mDefaultTextColor != null) {
            if (mSelectedTextColor == null) {
                (mBindView as TextView).setTextColor(mDefaultTextColor)
            } else {
                val colorStateList = ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_selected),
                        intArrayOf(-android.R.attr.state_selected)
                    ), intArrayOf(
                        checkNotNull(mSelectedTextColor).defaultColor,
                        checkNotNull(mDefaultTextColor).defaultColor
                    )
                )
                (mBindView as TextView).setTextColor(colorStateList)
            }
        }
        mBindView.isSelected = isBindViewSelected
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(0, 0)
    }

}

