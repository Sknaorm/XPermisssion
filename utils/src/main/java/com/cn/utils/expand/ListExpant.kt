package com.cn.utils.expand

fun <T> List<T>.getIndexOrNull(index: Int): T? {
    if (this.isNullOrEmpty()) {
        return null
    }
    if (index >= this.size) {
        return null
    }
    return this[index]
}

fun <T> MutableList<T>.move(index: Int, item: T) {
    this.remove(item)
    this.add(index, item)
}