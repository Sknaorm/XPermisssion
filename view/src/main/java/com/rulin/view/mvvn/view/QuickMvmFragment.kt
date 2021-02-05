package com.rulin.view.mvvn.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rulin.view.mvc.QuickFragment
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class QuickMvmFragment<T : ViewModel> : QuickFragment() {


    val mViewModel by lazy { getViewModel() ?: get() }
    private fun get(): T {
        val superClass: Type = javaClass.genericSuperclass as ParameterizedType
        val type: Type? = (superClass as ParameterizedType).actualTypeArguments[0]
        val t = type as Class<T>
        return ViewModelProvider(this).get(t)
    }

    fun <V : ViewModel> createViewModel(tClass: Class<V>): V {
        return ViewModelProvider(this).get(tClass)
    }

    fun <V : ViewModel> createBaseViewModel(tClass: Class<V>): V {
        return ViewModelProvider(mActivity).get(tClass)
    }

    open fun getViewModel(): T? = null
    abstract fun initObservable()

}