package com.cn.madel

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.*

abstract class QuickDialog(private val manager: FragmentManager) : DialogFragment() {
    protected lateinit var mDialog: Dialog
    protected lateinit var mWindow: Window
    protected lateinit var mDecorView: View
    abstract fun getLayoutId(): Int
    open fun getAnim(): Int? = null
    open fun getWidth(): Int? = null
    open fun getHeight(): Int? = null
    open fun getDimAmount(): Float? = null
    open fun getGravity(): Int? = null
    open fun getX(): Int? = null
    open fun getY(): Int? = null
    open fun isFullScreen(): Boolean = false
    open fun windowBackgroundDrawable(): Drawable? = null
    open fun onShow(dialogInterface: DialogInterface) {}
    open fun tag(): String = this::class.java.simpleName
    open fun init() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(getLayoutId(), container, false)

    fun show() = super.show(manager, tag())
    fun showNow() = super.showNow(manager, tag())

    @Deprecated("", ReplaceWith("show()", "QuickDialogFragment"))
    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    @Deprecated(
        "", ReplaceWith(
            "showNow()",
            "QuickDialogFragment"
        )
    )
    override fun showNow(manager: FragmentManager, tag: String?) {
        super.showNow(manager, tag)
    }

    @Deprecated(
        "", ReplaceWith(
            "show()",
            "QuickDialogFragment"
        )
    )
    override fun show(transaction: FragmentTransaction, tag: String?): Int {
        return super.show(transaction, tag)
    }

    final override fun onStart() {
        super.onStart()
        mDialog = dialog ?: return
        mWindow = mDialog.window ?: return
        mDecorView = mWindow.decorView
        mDialog.setOnShowListener { onShow(it) }
        getAnim()?.let { mWindow.setWindowAnimations(it) }
        val mAttributes = mWindow.attributes
        getWidth()?.let { mAttributes.width = it }
        getHeight()?.let { mAttributes.height = it }
        getDimAmount()?.let { mAttributes.dimAmount = it }
        getGravity()?.let { mAttributes.gravity = it }
        getX()?.let { mAttributes.x = it }
        getY()?.let { mAttributes.y = it }
        mWindow.attributes = mAttributes
        val windowBackgroundDrawable = windowBackgroundDrawable()
        if (windowBackgroundDrawable == null) {
            if (isFullScreen()) {
                mWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        } else {
            mWindow.setBackgroundDrawable(windowBackgroundDrawable)
        }
        init()
    }


}