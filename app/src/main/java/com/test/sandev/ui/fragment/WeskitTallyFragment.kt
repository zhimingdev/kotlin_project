package com.test.sandev.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.test.sandev.R
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.AccountTypeBean
import com.test.sandev.module.CommonData
import com.test.sandev.ui.activity.KeepAccounActivity
import kotlinx.android.synthetic.main.fragment_rv.*

class WeskitTallyFragment(private var typa :Int) : BaseFragment(){

    private var mAccountTypeBean: AccountTypeBean? = null
    private var mList: MutableList<AccountTypeBean>? = mutableListOf()
    companion object {
        val TYPE_PAYOUT = 0
        val TYPE_PAYIN = 1

        fun getInstance(type :Int) : WeskitTallyFragment {
            var fragment = WeskitTallyFragment(type)
            return fragment
        }
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_rv,null)
        return view
    }

    override fun initSandevDate() {
        mList!!.clear()
        val gridLayoutManager = GridLayoutManager(context, 4)
        rcv_contentRv.layoutManager = gridLayoutManager
        var mAccountBookAdapter = AccountBookAdapter()
        rcv_contentRv.adapter = mAccountBookAdapter
        when (typa) {
            TYPE_PAYOUT//支出
            -> {
                for (i in CommonData.payOutSortArray.indices) {
                    mAccountTypeBean = AccountTypeBean(CommonData.BOOK_TYPE_PAY_OUT, i, CommonData.payOutDrawableArray[i], CommonData.payOutSortArray[i])
                    mList!!.add(mAccountTypeBean!!)
                }
                mAccountBookAdapter.setNewData(mList!!)
            }
            TYPE_PAYIN//收入
            -> {
                for (i in CommonData.payInSortArray.indices) {
                    mAccountTypeBean = AccountTypeBean(CommonData.BOOK_TYPE_PAY_IN, i, CommonData.payInDrawableArray[i], CommonData.payInSortArray[i])
                    mList!!.add(mAccountTypeBean!!)
                }
                mAccountBookAdapter.setNewData(mList!!)
            }
        }
        mAccountBookAdapter.setOnItemClickListener { _, _, position ->
            val accountTypeBean = mList!![position]
            accountTypeBean.setSort(position)
            val bundle = Bundle()
            bundle.putSerializable("data", accountTypeBean)
            val intent = Intent(context, KeepAccounActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }


    inner class AccountBookAdapter : BaseQuickAdapter<AccountTypeBean, BaseViewHolder>(R.layout.account_book_item) {

        override fun convert(helper: BaseViewHolder, item: AccountTypeBean) {
            helper.setText(R.id.tv_type, item.getName())
            val view = helper.getView(R.id.iv_type) as ImageView
            Glide.with(activity).load(item.getDrawable()).into(view)
        }
    }
}