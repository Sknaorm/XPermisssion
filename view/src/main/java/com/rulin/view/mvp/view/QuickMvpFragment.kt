package com.rulin.view.mvp.view

import android.os.Bundle
import com.rulin.view.mvc.QuickFragment
import com.rulin.view.mvp.presenter.IPresenter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class QuickMvpFragment<P : IPresenter<*>> : QuickFragment() {

    val mPresenter by lazy { getPresenter() ?: get() }
    private fun get(): P {
        val superClass: Type = javaClass.genericSuperclass as ParameterizedType
        val type: Type? = (superClass as ParameterizedType).actualTypeArguments[0]
        val p = type as Class<P>
        return p.getConstructor(IView::class.java).newInstance(this)
    }

    open fun getPresenter(): P? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume()
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