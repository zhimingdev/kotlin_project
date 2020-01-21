package com.test.sandev.ui.activity

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.SPUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.constans.UrlConstans
import com.test.sandev.module.HomeNewBean
import com.test.sandev.module.LanqZhuModule
import com.test.sandev.utils.CircleTransform
import com.test.sandev.utils.StatusBarUtil
import com.test.sandev.utils.WatchHistoryUtils
import kotlinx.android.synthetic.main.layout_watch_history.*
import java.util.*
import kotlin.collections.ArrayList

class ZhuJiaActivity : BaseActivity() {
    private var itemListData = ArrayList<LanqZhuModule>()
    companion object {
        private const val HISTORY_MAX = 20
    }

    override fun getLayoutId(): Int = R.layout.layout_watch_history

    override fun initData() {
        tv_header_title.text = "我的专家"
        multipleStatusView.showLoading()
        itemListData = queryZhujiaHistory()
        //返回
        toolbar.setNavigationOnClickListener { finish() }

        val mAdapter = ZhuanjiaAdpter()
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
        mAdapter.setNewData(itemListData)

        if (itemListData.size > 0) {
            multipleStatusView.showContent()
        } else {
            multipleStatusView.showEmpty()
        }

        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
    }

    private fun queryZhujiaHistory() :ArrayList<LanqZhuModule>{
        val zjList = ArrayList<LanqZhuModule>()
        val hisAll = WatchHistoryUtils.getAll(UrlConstans.FILE_PEOPLE, this) as Map<*, *>
        //将key排序升序
        val keys = hisAll.keys.toTypedArray()
        val keyLength = keys.size
        //这里计算 如果历史记录条数是大于 可以显示的最大条数，则用最大条数做循环条件，防止历史记录条数-最大条数为负值，数组越界
        val hisLength = if (keyLength > HISTORY_MAX) HISTORY_MAX else keyLength
        (1..hisLength).mapTo(zjList) {
            WatchHistoryUtils.getObject(UrlConstans.FILE_PEOPLE, this,
                    keys[keyLength - it] as String) as LanqZhuModule
        }
        return zjList
    }

    inner class ZhuanjiaAdpter : BaseQuickAdapter<LanqZhuModule, BaseViewHolder>(R.layout.item_ba_gaunhzu,itemListData) {
        override fun convert(helper: BaseViewHolder?, item: LanqZhuModule?) {
            Picasso.with(this@ZhuJiaActivity).load(item!!.zjurl).transform(CircleTransform()).into(helper!!.getView(R.id.iv_bas) as ImageView)
            helper.setText(R.id.tv_bas_name,item.zjname)
            helper.setText(R.id.tv_bas_tags,item.biaoshi)
            helper.setText(R.id.tv_bas_mz,item.mingzhong)
            helper.setText(R.id.tv_tab_one,item.tablist!![0].history)
            helper.setText(R.id.tv_tab_two,item.tablist!![1].history)
            helper.getView<TextView>(R.id.tv_gz_zhujia).visibility = View.GONE
        }
    }
}