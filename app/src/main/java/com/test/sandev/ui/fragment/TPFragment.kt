package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.ItemTouchHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.TPModule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import com.test.sandev.view.CardConfig
import com.test.sandev.view.CardItemTouchCallBack
import com.test.sandev.view.SwipeCardLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_tp.*

class TPFragment(private var postion: String) : BaseFragment() {
    val network by lazy { NetWork() }
    private var mData: List<TPModule>? = null
    var mAdapter : CardAdapter? = null

    companion object {
        fun getInstance(postion: String): TPFragment {
            val fragment = TPFragment(postion)
            return fragment
        }
    }

    override fun initView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_tp, null)
        return view
    }

    override fun initSandevDate() {
        //初始化卡片的基本配置参数
        CardConfig.initConfig(context)
        recycler_view.layoutManager = SwipeCardLayoutManager()
        mAdapter = CardAdapter()
        recycler_view.adapter = mAdapter
        if (postion == "0") {
            network.getApi(Api::class.java).getYC()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object :ApiBaseResponse<List<TPModule>>(activity!!){
                        override fun onSuccess(t: List<TPModule>?) {
                            mData = t!!
                            mAdapter!!.setNewData(mData)
                            //三步
                            //1.创建ItemTuchCallBack
                            val callBack = CardItemTouchCallBack(recycler_view, mAdapter, mData)
                            //2.创建ItemTouchHelper并把callBack传进去
                            val itemTouchHelper = ItemTouchHelper(callBack)
                            //3.与RecyclerView关联起来
                            itemTouchHelper.attachToRecyclerView(recycler_view)
                        }

                        override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                        }

                        override fun onFail(e: ApiError) {
                        }

                    })
        } else if (postion == "1") {
            network.getApi(Api::class.java).getYJ()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object :ApiBaseResponse<List<TPModule>>(activity!!){
                        override fun onSuccess(t: List<TPModule>?) {
                            mData = t!!
                            mAdapter!!.setNewData(mData)
                            //三步
                            //1.创建ItemTuchCallBack
                            val callBack = CardItemTouchCallBack(recycler_view, mAdapter, mData)
                            //2.创建ItemTouchHelper并把callBack传进去
                            val itemTouchHelper = ItemTouchHelper(callBack)
                            //3.与RecyclerView关联起来
                            itemTouchHelper.attachToRecyclerView(recycler_view)
                        }

                        override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                        }

                        override fun onFail(e: ApiError) {
                        }

                    })
        } else if (postion == "2") {
            network.getApi(Api::class.java).getXJ()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object :ApiBaseResponse<List<TPModule>>(activity!!){
                        override fun onSuccess(t: List<TPModule>?) {
                            mData = t!!
                            mAdapter!!.setNewData(mData)
                            //三步
                            //1.创建ItemTuchCallBack
                            val callBack = CardItemTouchCallBack(recycler_view, mAdapter, mData)
                            //2.创建ItemTouchHelper并把callBack传进去
                            val itemTouchHelper = ItemTouchHelper(callBack)
                            //3.与RecyclerView关联起来
                            itemTouchHelper.attachToRecyclerView(recycler_view)
                        }

                        override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                        }

                        override fun onFail(e: ApiError) {
                        }

                    })
        } else if (postion == "3") {
            network.getApi(Api::class.java).getOG()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object :ApiBaseResponse<List<TPModule>>(activity!!){
                        override fun onSuccess(t: List<TPModule>?) {
                            mData = t!!
                            mAdapter!!.setNewData(mData)
                            //三步
                            //1.创建ItemTuchCallBack
                            val callBack = CardItemTouchCallBack(recycler_view, mAdapter, mData)
                            //2.创建ItemTouchHelper并把callBack传进去
                            val itemTouchHelper = ItemTouchHelper(callBack)
                            //3.与RecyclerView关联起来
                            itemTouchHelper.attachToRecyclerView(recycler_view)
                        }

                        override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                        }

                        override fun onFail(e: ApiError) {
                        }

                    })
        } else if (postion == "4") {
            network.getApi(Api::class.java).getDJ()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object :ApiBaseResponse<List<TPModule>>(activity!!){
                        override fun onSuccess(t: List<TPModule>?) {
                            mData = t!!
                            mAdapter!!.setNewData(mData)
                            //三步
                            //1.创建ItemTuchCallBack
                            val callBack = CardItemTouchCallBack(recycler_view, mAdapter, mData)
                            //2.创建ItemTouchHelper并把callBack传进去
                            val itemTouchHelper = ItemTouchHelper(callBack)
                            //3.与RecyclerView关联起来
                            itemTouchHelper.attachToRecyclerView(recycler_view)
                        }

                        override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                        }

                        override fun onFail(e: ApiError) {
                        }

                    })
        }else if (postion == "5") {
            network.getApi(Api::class.java).getNBA()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object :ApiBaseResponse<List<TPModule>>(activity!!){
                        override fun onSuccess(t: List<TPModule>?) {
                            mData = t!!
                            mAdapter!!.setNewData(mData)
                            //三步
                            //1.创建ItemTuchCallBack
                            val callBack = CardItemTouchCallBack(recycler_view, mAdapter, mData)
                            //2.创建ItemTouchHelper并把callBack传进去
                            val itemTouchHelper = ItemTouchHelper(callBack)
                            //3.与RecyclerView关联起来
                            itemTouchHelper.attachToRecyclerView(recycler_view)
                        }

                        override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                        }

                        override fun onFail(e: ApiError) {
                        }

                    })
        }

    }

    inner class CardAdapter : BaseQuickAdapter<TPModule,BaseViewHolder>(R.layout.item_card,mData) {
        override fun convert(helper: BaseViewHolder?, item: TPModule?) {
            var view = helper!!.getView<ImageView>(R.id.iv_photo)
            Picasso.with(context)
                    .load(item!!.imageurl)
                    .into(view)

            helper.setText(R.id.tv_name,"体育")
        }
    }
}