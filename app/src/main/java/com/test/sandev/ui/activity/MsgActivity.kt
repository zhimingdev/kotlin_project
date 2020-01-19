package com.test.sandev.ui.activity

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import com.blankj.utilcode.util.ToastUtils
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import kotlinx.android.synthetic.main.activity_msg.*

class MsgActivity : BaseActivity(), TextWatcher {
    override fun getLayoutId(): Int {
        return R.layout.activity_msg
    }

    override fun initData() {
        et_content.addTextChangedListener(this)
        et_content.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(100))
    }

    override fun initLisenter() {
        rl_msg_back.setOnClickListener {
            finish()
        }
        tv_submit.setOnClickListener {
            ToastUtils.showShort("提交成功")
            finish()
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        tv_count.text = "${s.toString().trim().length}/100"
    }

}