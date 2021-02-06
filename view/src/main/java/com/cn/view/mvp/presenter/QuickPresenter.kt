package com.cn.view.mvp.presenter

import com.cn.view.mvp.view.IView

abstract class QuickPresenter<V : IView>(protected var mView: V? = null) :
    IPresenter<V> {
    override fun onDestroy() {
        super.onDestroy()
        mView = null
    }
}