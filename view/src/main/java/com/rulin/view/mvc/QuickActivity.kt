package com.rulin.view.mvc

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class QuickActivity : AppCompatActivity(), IView {
    val mActivity by lazy { this }
    open fun createView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View {
        return delegate.createView(parent, name, context, attrs)
    }

    open fun actonBeforeSuperCreate(savedInstanceState: Bundle?) {}
    open fun actonBeforeSetContentView(savedInstanceState: Bundle?) {}
    open fun actonAfterSetContentView(savedInstanceState: Bundle?) {}
    override fun onCreate(savedInstanceState: Bundle?) {
//        LayoutInflaterCompat.setFactory2(layoutInflater, QuickFactory())
        actonBeforeSuperCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        actonBeforeSetContentView(savedInstanceState)
        setContentView(getLayoutId())
        actonAfterSetContentView(savedInstanceState)
        initData(intent)
        initView()
        initEvent()
        initThird()
        init()
    }

    inner class QuickFactory : LayoutInflater.Factory2 {
        override fun onCreateView(
            parent: View?,
            name: String,
            context: Context,
            attrs: AttributeSet
        ): View? {
            return createView(parent, name, context, attrs)
        }

        override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
            return null
        }
    }
}