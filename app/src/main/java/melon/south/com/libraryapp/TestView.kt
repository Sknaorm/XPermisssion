package melon.south.com.libraryapp

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class TestView : FrameLayout {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
}