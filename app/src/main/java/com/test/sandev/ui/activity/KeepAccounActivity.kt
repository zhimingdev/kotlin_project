package com.test.sandev.ui.activity

import com.blankj.utilcode.util.ToastUtils
import com.test.sandev.MyApplication
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.entity.BookEntity
import com.test.sandev.module.AccountTypeBean
import com.test.sandev.module.MessageEvent
import com.test.sandev.utils.ActivityUtil
import com.test.sandev.utils.DataString
import kotlinx.android.synthetic.main.activity_keep_account.*
import org.greenrobot.eventbus.EventBus
import java.util.*

class KeepAccounActivity : BaseActivity() {

    private var mData: AccountTypeBean? = null
    private var mMsg: String? = null
    private var mMoney: String? = null
    private var id : Long = 1

    override fun getLayoutId(): Int {
        return R.layout.activity_keep_account
    }

    override fun initData() {
        mData = intent.getSerializableExtra("data") as AccountTypeBean
        tv_category.text = mData!!.getName()
        tv_weskit_current_day.text = DataString.StringData()
        et_money.isFocusable = true
        et_money.isFocusableInTouchMode = true
        et_money.requestFocus()
        et_money.setMaxmumFilter(99999.99, 2)
        et_money.setDecimalFilter(2)
    }

    override fun initLisenter() {
        but_add_accountbook.setOnClickListener {
            mMsg = et_message.text.toString().trim()
            mMoney = et_money.text.toString().trim()
            if (mMoney.isNullOrBlank()) {
                ToastUtils.showShort("请输入金额")
                return@setOnClickListener
            }else {
                val bookEntity = BookEntity()
                bookEntity.bookType = mData!!.getType()
                bookEntity.bookSort = mData!!.getSort()
                val calendar = Calendar.getInstance()
                bookEntity.year = calendar.get(Calendar.YEAR)
                bookEntity.month = calendar.get(Calendar.MONTH)
                bookEntity.day = calendar.get(Calendar.DAY_OF_MONTH)
                bookEntity.time = calendar.timeInMillis
                bookEntity.bookNote = mMsg
                bookEntity.amount = mMoney!!.toDouble()
                var bookEntityDao = MyApplication.instances.daoSession!!.bookEntityDao
                var insert = bookEntityDao.insert(bookEntity)
                if (insert != -1L) {
                    ToastUtils.showShort("添加成功")
                    EventBus.getDefault().post(MessageEvent("addlife"))
                    finish()
                    ActivityUtil.finishActivity(WeskitTallyActivity::class.java)
                }
            }
        }
    }
}