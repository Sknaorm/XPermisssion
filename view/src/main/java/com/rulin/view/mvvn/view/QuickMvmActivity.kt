package com.rulin.view.mvvn.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rulin.view.mvc.QuickActivity
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class QuickMvmActivity<T : ViewModel> : QuickActivity() {

    val mViewModel by lazy { getViewModel() ?: get() }
    private fun get(): T {
        val superClass: Type = javaClass.genericSuperclass as ParameterizedType
        val type: Type? = (superClass as ParameterizedType).actualTypeArguments[0]
        val t = type as Class<T>
        return ViewModelProvider(this).get(t)
    }

    open fun getViewModel(): T? = null

}