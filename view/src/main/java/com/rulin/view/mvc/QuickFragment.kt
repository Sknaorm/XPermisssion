package com.rulin.view.mvc

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


abstract class QuickFragment : Fragment(), IView {
    val mActivity by lazy { this.activity as FragmentActivity }
    val isParentHidden by lazy { isHidden(this) }
    abstract fun initData(bundle: Bundle)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    final override fun initData(intent: Intent) {

    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        if (args != null) {
            initData(args)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
        initThird()
        init()
    }

    private fun isHidden(fragment: Fragment): Boolean {
        val isHidden = fragment.isHidden
        val parent = fragment.parentFragment
        return if (parent == null) {
            isHidden
        } else {
            isHidden(parent)
        }
    }

}