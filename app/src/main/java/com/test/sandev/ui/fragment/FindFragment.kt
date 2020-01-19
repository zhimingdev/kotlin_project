package com.test.sandev.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.test.sandev.R
import com.test.sandev.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_find.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso
import com.test.sandev.api.Api
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.GuanModule
import com.test.sandev.ui.activity.GuanActivty
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class FindFragment : BaseFragment() {

    var fragments : MutableList<Fragment> = mutableListOf()
    private val titles  = arrayOf("英超","意甲","西甲","欧冠","德甲","NBA")
    var adapter : FindAdapter? = null
    var rvadapter : MyAdapter? = null
    var data : List<GuanModule.DataListBean>? = null

    val network by lazy { NetWork() }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_find,null)
        return view
    }

    override fun initSandevDate() {
        fragments.clear()
        fragments.add(TPFragment.getInstance("0"))
        fragments.add(TPFragment.getInstance("1"))
        fragments.add(TPFragment.getInstance("2"))
        fragments.add(TPFragment.getInstance("3"))
        fragments.add(TPFragment.getInstance("4"))
        fragments.add(TPFragment.getInstance("5"))
        adapter = FindAdapter()
        vp_find_content.adapter = adapter
        tl_find_tabs.setupWithViewPager(vp_find_content)

        val gridLayoutManager = GridLayoutManager(context,2)
        rv_cahung.layoutManager = gridLayoutManager
        rvadapter = MyAdapter()
        rv_cahung.adapter = rvadapter
        getInf()

    }

    private fun getInf() {
        network.getGuanApi(Api::class.java).getGuan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<GuanModule>(activity!!){
                    override fun onSuccess(t: GuanModule?) {
                        data = t!!.dataList
                        rvadapter!!.setNewData(data)
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    override fun initSandevListenter() {
        rvadapter!!.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(activity,GuanActivty::class.java)
            var address = data!![position].address
            var stadiumItemName = data!![position].stadiumItemName
            var telephone = data!![position].telephone
            intent.putExtra("address",address)
            intent.putExtra("stadiumItemName",stadiumItemName)
            intent.putExtra("telephone",telephone)
            var imageUrl = data!![position].imageList!![0].imageUrl
            intent.putExtra("imageUrl",imageUrl)
            startActivity(intent)
        }
    }

    inner class MyAdapter : BaseQuickAdapter<GuanModule.DataListBean,BaseViewHolder>(R.layout.item_guan,data) {
        override fun convert(helper: BaseViewHolder?, item: GuanModule.DataListBean?) {
            helper!!.setText(R.id.tv_guang,item!!.stadiumItemName)
            if (item.imageList!!.isNotEmpty() && item.imageList != null) {
                Picasso.with(activity).load(item.imageList!![0].imageUrl).into(helper.getView(R.id.iv_gaun) as ImageView)
            }
        }

    }

    inner class FindAdapter : FragmentPagerAdapter(activity!!.supportFragmentManager){
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

    }

}