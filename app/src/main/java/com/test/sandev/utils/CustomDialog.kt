package com.test.sandev.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.test.sandev.R

class CustomDialog private constructor(context: Context, private val TITLE: String?,
                   private val MESSAGE: String?,
                   private val CONFIRMTEXT: String?,
                   private val CANCELTEXT: String?,
                   private val ONCONFIRMCLICKLISTENER: onConfirmClickListener?,
                   private val ONCANCELCLICKLISTENER: onCancelClickListener?) : Dialog(context,R.style.MyUsualDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.usual_dialog);
        setCanceledOnTouchOutside(false)
        initView()
    }

    private fun initView() {
        val btnConfirm = findViewById<Button>(R.id.btn_confirm)
        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val tvMessage = findViewById<TextView>(R.id.tv_message)

        if (!TextUtils.isEmpty(TITLE)) {
            tvTitle.text = TITLE
        }
        if (!TextUtils.isEmpty(MESSAGE)) {
            tvMessage.text = MESSAGE
        }
        if (!TextUtils.isEmpty(CONFIRMTEXT)) {
            btnConfirm.text = CONFIRMTEXT
        }
        if (!TextUtils.isEmpty(CANCELTEXT)) {
            btnCancel.text = CANCELTEXT
        }

        btnConfirm.setOnClickListener(View.OnClickListener { view ->
            if (ONCONFIRMCLICKLISTENER == null) {
                throw NullPointerException("clicklistener is not null")
            } else {
                ONCONFIRMCLICKLISTENER.onClick(view)
            }
        })
        btnCancel.setOnClickListener(View.OnClickListener { view ->
            if (ONCANCELCLICKLISTENER == null) {
                throw NullPointerException("clicklistener is not null")
            } else {
                ONCANCELCLICKLISTENER.onClick(view)
            }
        })

    }

    interface onConfirmClickListener {
        fun onClick(view: View)
    }

    interface onCancelClickListener {
        fun onClick(view: View)
    }

    fun shown(): CustomDialog {
        show()
        return this
    }

    class Builder public constructor(private val mContext: Context) {
        private var mTitle: String? = null
        private var mMessage: String? = null
        private var mConfirmText: String? = null
        private var mCancelText: String? = null
        private var mOnConfirmClickListener: onConfirmClickListener? = null
        private var mOnCcancelClickListener: onCancelClickListener? = null

        fun setTitle(title: String): Builder {
            this.mTitle = title
            return this
        }

        fun setMessage(message: String): Builder {
            this.mMessage = message
            return this
        }

        fun setOnConfirmClickListener(confirmText: String, confirmclickListener: onConfirmClickListener): Builder {
            this.mConfirmText = confirmText
            this.mOnConfirmClickListener = confirmclickListener
            return this
        }

        fun setOnCancelClickListener(cancelText: String, onCancelclickListener: onCancelClickListener): Builder {
            this.mCancelText = cancelText
            this.mOnCcancelClickListener = onCancelclickListener
            return this
        }

        fun build(): CustomDialog {
            return CustomDialog(
                mContext, mTitle, mMessage, mConfirmText, mCancelText,
                mOnConfirmClickListener, mOnCcancelClickListener
            )
        }
    }
}