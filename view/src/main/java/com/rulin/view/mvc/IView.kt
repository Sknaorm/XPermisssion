package com.rulin.view.mvc

import android.content.Intent

interface IView {
    fun getLayoutId(): Int
    fun initData(intent: Intent)
    fun initView()
    fun initEvent()
    fun initThird()
    fun init()
}