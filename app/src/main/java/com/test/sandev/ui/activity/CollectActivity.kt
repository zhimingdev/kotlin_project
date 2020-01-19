package com.test.sandev.ui.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.sandev.MyApplication
import com.test.sandev.R
import com.test.sandev.adapter.CollectAdapter
import com.test.sandev.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collect.*

class CollectActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_collect
    }

    override fun initData() {
        var dao = MyApplication.instances.daoSession!!.collectEntityDao
        var list = dao.loadAll()
        if (list.size == 0) {
            ll_empty.visibility = View.VISIBLE
            rv_collect.visibility = View.GONE
        }else{
            ll_empty.visibility = View.GONE
            rv_collect.visibility = View.VISIBLE
        }
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        rv_collect.layoutManager = linearLayoutManager
        var adapter : CollectAdapter = CollectAdapter(this,list)
        rv_collect.adapter = adapter
    }

    override fun initLisenter() {
        rl_collect_back.setOnClickListener {
            finish()
        }
    }

}