package com.cn.utils.expand

import android.view.View
import android.widget.TextView


fun View.gone() {
    this.visibility = View.GONE
}

fun View.goneIf(block: () -> Boolean) {
    if (block())
        this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.visibleIf(block: () -> Boolean) {
    if (block())
        this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.invisibleIf(block: () -> Boolean) {
    if (block())
        this.visibility = View.INVISIBLE
}

fun TextView.setTextOrEmpty(text: String?) {
    if (text.isNullOrEmpty()) {
        this.text = ""
    } else {
        this.text = text
    }
}

fun <T : View> T.setOnNoDoubleClickListener(timeOut: Long = 1000, block: (T) -> Unit) {
    var lastTime = 0L
    setOnClickListener {
        if (System.currentTimeMillis() - lastTime <= timeOut) return@setOnClickListener
        block(this)
        lastTime = System.currentTimeMillis()
    }
}