package com.test.sandev.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.test.sandev.MyApplication
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.entity.BookEntity
import com.test.sandev.module.CommonData
import com.test.sandev.module.MessageEvent
import kotlinx.android.synthetic.main.activity_life_record.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LifeRecordActivity : BaseActivity(){
    override fun getLayoutId(): Int {
        return R.layout.activity_life_record
    }

    override fun initData() {
        EventBus.getDefault().register(this)
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        rv_lift.layoutManager = linearLayoutManager
        showUi()
    }

    private fun showUi() {
        var bookEntityDao = MyApplication.instances.daoSession!!.bookEntityDao
        var list = bookEntityDao.loadAll()
        if (list.size != 0) {
            rv_lift.visibility = View.VISIBLE
            ll_lift_empty.visibility = View.GONE
        }else {
            rv_lift.visibility = View.GONE
            ll_lift_empty.visibility = View.VISIBLE
        }
        var adapter = LifeAdapter(this)
        rv_lift.adapter = adapter
        adapter.setNewData(list)
    }

    override fun initLisenter() {
        but_add.setOnClickListener {
            var intent = Intent(this,WeskitTallyActivity::class.java)
            startActivity(intent)
        }

        rl_lift_back.setOnClickListener {
            finish()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onecent(event : MessageEvent) {
        when (event.message) {
            "addlife" -> {
                showUi()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    class LifeAdapter(val context: Context) : BaseQuickAdapter<BookEntity,BaseViewHolder>(R.layout.item_weskit_chart_book) {
        override fun convert(helper: BaseViewHolder?, item: BookEntity?) {
            helper!!.setText(R.id.tv_life_time,item!!.year.toString()+"-"+(item!!.month+1).toString())
            var sort: String? = null
            when (item.bookType) {
                BookEntity.BOOK_TYPE_PAY_OUT -> sort = CommonData.payOutSortArray[item.bookSort]
                BookEntity.BOOK_TYPE_PAY_IN -> sort = CommonData.payInSortArray[item.bookSort]
            }
            helper!!.setText(R.id.book_sort_tv,sort)
            helper!!.setText(R.id.tv_life_msg,item.bookNote)
            var type = item!!.bookType
            when(type) {
                CommonData.BOOK_TYPE_PAY_IN -> {
                    helper!!.setText(R.id.book_amount_tv,"+ "+item!!.amount)
                    helper!!.getView<TextView>(R.id.book_amount_tv).setTextColor(context.resources.getColor(R.color.light_green))
                }
                CommonData.BOOK_TYPE_PAY_OUT -> {
                    helper!!.setText(R.id.book_amount_tv,"- "+item!!.amount)
                    helper!!.getView<TextView>(R.id.book_amount_tv).setTextColor(context.resources.getColor(R.color.red))
                }
            }
        }
    }
}