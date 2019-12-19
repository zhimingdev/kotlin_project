package com.test.sandev.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridView

class MyGridView : GridView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //makeMeasureSpec根据提供的大小值和模式创建一个测量值(格式)
        val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,
                View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}
