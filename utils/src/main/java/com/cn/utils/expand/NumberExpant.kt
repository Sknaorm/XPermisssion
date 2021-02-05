package com.cn.utils.expand

import android.content.res.Resources

val Number.px
    get() = this.toFloat() * Resources.getSystem().displayMetrics.density
val Number.dp
    get() = this.toFloat() / Resources.getSystem().displayMetrics.density
val Number.toScreenWidth
    get() = this.toFloat() * Resources.getSystem().displayMetrics.widthPixels
val Number.toScreenHeight
    get() = this.toFloat() * Resources.getSystem().displayMetrics.heightPixels