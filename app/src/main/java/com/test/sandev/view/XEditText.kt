package com.test.sandev.view

import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatEditText

import java.util.ArrayList
import java.util.regex.Pattern

class XEditText : AppCompatEditText {

    // 保存设置的所有输入限制
    private var inputFilters: MutableList<InputFilter>? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        inputFilters = ArrayList()
    }

    /**
     * 设置允许输入的最大字符数
     */
    fun setMaxLengthFilter(maxLength: Int) {
        inputFilters!!.add(InputFilter.LengthFilter(maxLength))
        filters = inputFilters!!.toTypedArray()
    }

    /**
     * 设置可输入小数位
     */
    fun setDecimalFilter(num: Int) {
        inputFilters!!.add(SignedDecimalFilter(0, num))
        filters = inputFilters!!.toTypedArray()
    }

    /**
     * 设置最大值
     * @param maxmum  允许的最大值
     * @param numOfDecimal  允许的小数位
     */
    fun setMaxmumFilter(maxmum: Double, numOfDecimal: Int) {
        inputFilters!!.add(MaxmumFilter(0, maxmum, numOfDecimal))
        filters = inputFilters!!.toTypedArray()
    }

    /**
     * 设置只能输入整数
     * @param min 输入整数的最小值
     */
    fun setIntergerFilter(min: Int) {
        inputFilters!!.add(SignedIntegerFilter(min))
        filters = inputFilters!!.toTypedArray()
    }

    /**
     * 只能够输入手机号码
     */
    fun setTelFilter() {
        inputFilters!!.add(TelephoneNumberInputFilter())
        filters = inputFilters!!.toTypedArray()
    }

    /**
     * 小数位数限制
     */
    private class SignedDecimalFilter internal constructor(min: Int, numOfDecimals: Int) : InputFilter {

        private val pattern: Pattern

        init {
            pattern = Pattern.compile("^" + (if (min < 0) "-?" else "")
                    + "[0-9]*\\.?[0-9]" + if (numOfDecimals > 0) "{0,$numOfDecimals}$" else "*")
        }

        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence {
            if (source == ".") {
                if (dstart == 0 || !(dest[dstart - 1] >= '0' && dest[dstart - 1] <= '9') || dest[0] == '0') {
                    return ""
                }
            }
            if (source == "0" && dest.toString().contains(".") && dstart == 0) { //防止在369.369的最前面输入0变成0369.369这种不合法的形式
                return ""
            }
            val builder = StringBuilder(dest)
            builder.delete(dstart, dend)
            builder.insert(dstart, source)
            return if (!pattern.matcher(builder.toString()).matches()) {
                ""
            } else source

        }
    }

    /**
     * 限制输入最大值
     */
    private class MaxmumFilter internal constructor(min: Int, private val maxNum: Double, numOfDecimals: Int) : InputFilter {

        private val pattern: Pattern

        init {
            pattern = Pattern.compile("^" + (if (min < 0) "-?" else "")
                    + "[0-9]*\\.?[0-9]" + if (numOfDecimals > 0) "{0,$numOfDecimals}$" else "*")
        }

        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence {
            if (source == ".") {
                if (dstart == 0 || !(dest[dstart - 1] >= '0' && dest[dstart - 1] <= '9') || dest[0] == '0') {
                    return ""
                }
            }
            if (source == "0" && dest.toString().contains(".") && dstart == 0) {
                return ""
            }

            val builder = StringBuilder(dest)
            builder.delete(dstart, dend)
            builder.insert(dstart, source)
            if (!pattern.matcher(builder.toString()).matches()) {
                return ""
            }

            if (!TextUtils.isEmpty(builder)) {
                val num = java.lang.Double.parseDouble(builder.toString())
                if (num > maxNum) {
                    return ""
                }
            }
            return source
        }
    }

    /**
     * 限制整数
     */
    private class SignedIntegerFilter internal constructor(min: Int) : InputFilter {
        private val pattern: Pattern

        init {
            pattern = Pattern.compile("^" + (if (min < 0) "-?" else "") + "[0-9]*$")
        }

        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence {
            val builder = StringBuilder(dest)
            builder.insert(dstart, source)
            return if (!pattern.matcher(builder.toString()).matches()) {
                ""
            } else source
        }
    }

    /**
     * 限制电话号码
     */
    private class TelephoneNumberInputFilter : InputFilter {

        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence {
            val builder = StringBuilder(dest)
            builder.insert(dstart, source)
            val length = builder.length
            if (length == 1) {
                return if (builder[0] == '1') {
                    source
                } else {
                    ""
                }
            }

            if (length > 0 && length <= 11) {
                val suffixSize = length - 2
                val pattern = Pattern.compile("^1[3-9]\\d{$suffixSize}$")
                return if (pattern.matcher(builder.toString()).matches()) {
                    source
                } else {
                    ""
                }
            }

            return ""
        }
    }
}