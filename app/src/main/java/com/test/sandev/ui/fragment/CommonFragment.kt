package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.test.sandev.R
import com.test.sandev.adapter.HomeAdapter
import com.test.sandev.adapter.SqAdapter
import com.test.sandev.adapter.VideoAdapter
import com.test.sandev.adapter.holder.VideoViewHolder
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.*
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_common.*

class CommonFragment(private var number : Int) : BaseFragment() {

    val netWork by lazy { NetWork() }
    var homeAdapter : HomeAdapter? = null
    var sqAdapter : SqAdapter? = null
    var data : List<VideoModule>? = listOf()

    companion object {
        fun getInstanca(type : Int) : CommonFragment {
            return CommonFragment(type)
        }
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_common,null)
        return view
    }

    override fun initSandevDate() {
        loadData()
    }

    private fun loadData() {
        if (number == 0) {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = RecyclerView.VERTICAL
            recyclerView!!.layoutManager = linearLayoutManager
            netWork.getApi(Api::class.java).getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<HomeBean>(activity!!) {
                    override fun onSuccess(t: HomeBean?) {
                        if (homeAdapter == null) {
                            homeAdapter = HomeAdapter(context,t!!.datas)
                        }
                        recyclerView.adapter = homeAdapter
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })

//            netWork.getApi(Api::class.java).getAaData()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(object : ApiBaseResponse<HomeBean>(activity!!) {
//                        override fun onSuccess(t: HomeBean?) {
//                            if (homeAdapter == null) {
//                                homeAdapter = HomeAdapter(context,t!!.datas)
//                            }
//                            recyclerView.adapter = homeAdapter
//                        }
//
//                        override fun onCodeError(tBaseReponse: BaseResponse<*>) {
//                        }
//
//                        override fun onFail(e: ApiError) {
//                        }
//
//                    })
        }else if(number == 2){
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = RecyclerView.VERTICAL
            recyclerView!!.layoutManager = linearLayoutManager
            netWork.getApi(Api::class.java).getSqData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : ApiBaseResponse<List<SqModule>>(activity!!) {
                        override fun onSuccess(t: List<SqModule>?) {
                            if (sqAdapter == null) {
                                sqAdapter = SqAdapter(context,t!!)
                            }
                            recyclerView.adapter = sqAdapter
                        }

                        override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                        }

                        override fun onFail(e: ApiError) {
                        }
                    })
        }else{
            getDtat()
        }
    }

    private fun getDtat() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.setRecyclerListener {
            val niceVideoPlayer = (it as VideoViewHolder).mVideoPlayer
            if (niceVideoPlayer === NiceVideoPlayerManager.instance().currentNiceVideoPlayer) {
                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
            }
        }
        netWork.getApi(Api::class.java).getVideoData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :ApiBaseResponse<List<VideoModule>>(activity!!) {
                    override fun onSuccess(t: List<VideoModule>?) {
                        data = t
                        val adapter = VideoAdapter(context!!, data)
                        recyclerView.adapter = adapter
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
//        netWork.getApi(Api::class.java).getAvInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object :ApiBaseResponse<List<VideoModule>>(activity!!) {
//                    override fun onSuccess(t: List<VideoModule>?) {
//                        data = t
//                        val adapter = VideoAdapter(context!!, data)
//                        recyclerView.adapter = adapter
//                    }
//
//                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
//                    }
//
//                    override fun onFail(e: ApiError) {
//                    }
//
//                })
    }


    override fun onStop() {
        super.onStop()
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
        }
    }
}