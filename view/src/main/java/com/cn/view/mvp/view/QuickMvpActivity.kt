package com.cn.view.mvp.view

import com.cn.view.mvc.QuickActivity
import com.cn.view.mvp.presenter.IPresenter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class QuickMvpActivity<P : IPresenter<*>> : QuickActivity(), IView {

    val mPresenter by lazy { getPresenter() ?: get() }
    private fun get(): P {
        val superClass: Type = javaClass.genericSuperclass as ParameterizedType
        val type: Type? = (superClass as ParameterizedType).actualTypeArguments[0]
        val p = type as Class<P>
        val newInstance = p.constructors[0].newInstance(this)
        return newInstance as P
    }

    open fun getPresenter(): P? = null



    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume()
    }

    override fun onRestart() {
        super.onRestart()
        mPresenter.onRestart()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

}