package com.cn.permission


class Builder {
    internal val permissions: ArrayList<String> = arrayListOf()
    fun addPermission(name: String) {
        permissions.add(name)
    }
}