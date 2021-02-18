package com.cn.layout

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView


class ShapeTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    init {
        ShapeUtils.getAttrByShapeView(context, this, attrs)
    }
}
