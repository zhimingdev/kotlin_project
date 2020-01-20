package com.test.sandev.ui.activity

import android.annotation.TargetApi
import android.os.Build
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.google.android.flexbox.*
import com.test.sandev.R
import com.test.sandev.adapter.CategoryDetailAdapter
import com.test.sandev.adapter.HotKeywordsAdapter
import com.test.sandev.api.Api
import com.test.sandev.base.BaseActivity
import com.test.sandev.module.HomeNewBean
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import com.test.sandev.utils.StatusBarUtil
import com.test.sandev.utils.ViewAnimUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity :BaseActivity() {
    val net by lazy { NetWork() }
    private var mHotKeywordsAdapter: HotKeywordsAdapter? = null
    private var keyWords: String? = null
    private var nextPageUrl: String? = null
    private var itemList = ArrayList<HomeNewBean.Issue.Item>()
    private val mResultAdapter by lazy { CategoryDetailAdapter(this, itemList, R.layout.item_category_detail) }
    /**
     * 是否加载更多
     */
    private var loadingMore = false


    override fun getLayoutId() = R.layout.activity_search

    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpEnterAnimation() // 入场动画
            setUpExitAnimation() // 退场动画
        } else {
            setUpView()
        }
        mRecyclerView_result.layoutManager = LinearLayoutManager(this)
        mRecyclerView_result.adapter = mResultAdapter
        requestHotWordData()
        //实现自动加载
        mRecyclerView_result.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = (mRecyclerView_result.layoutManager as LinearLayoutManager).itemCount
                val lastVisibleItem = (mRecyclerView_result.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    loadMoreData()
                }
            }
        })

        //取消
        tv_cancel.setOnClickListener { onBackPressed() }
        //键盘的搜索按钮
        et_search_view.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    closeSoftKeyboard()
                    keyWords = et_search_view.text.toString().trim()
                    if (keyWords.isNullOrEmpty()) {
                        ToastUtils.showShort("请输入你感兴趣的关键词")
                    } else {
                        querySearchData(keyWords!!)
                    }
                }
                return false
            }

        })

        mLayoutStatusView = multipleStatusView
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
    }

    private fun loadMoreData() {
        net.getBaseApi(Api::class.java).getIssueData(nextPageUrl!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeNewBean.Issue>{
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(issue: HomeNewBean.Issue) {
                        nextPageUrl = issue.nextPageUrl
                        setSearchResult(issue)
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }

    private fun requestHotWordData() {
        net.getBaseApi(Api::class.java).getHotWord()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<String>>{
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(string: ArrayList<String>) {
                        showHotWordView()
                        mHotKeywordsAdapter = HotKeywordsAdapter(this@SearchActivity, string, R.layout.item_flow_text)
                        val flexBoxLayoutManager = FlexboxLayoutManager(this@SearchActivity)
                        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP      //按正常方向换行
                        flexBoxLayoutManager.flexDirection = FlexDirection.ROW   //主轴为水平方向，起点在左端
                        flexBoxLayoutManager.alignItems = AlignItems.CENTER    //定义项目在副轴轴上如何对齐
                        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START  //多个轴对齐方式

                        mRecyclerView_hot.layoutManager = flexBoxLayoutManager
                        mRecyclerView_hot.adapter = mHotKeywordsAdapter
                        //设置 Tag 的点击事件
                        mHotKeywordsAdapter?.setOnTagItemClickListener {
                            closeSoftKeyboard()
                            keyWords = it
                            querySearchData(it)
                        }
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }

    private fun querySearchData(it: String) {
        net.getBaseApi(Api::class.java).getSearchData(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeNewBean.Issue>{
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(issue: HomeNewBean.Issue) {
                        if (issue.count > 0 && issue.itemList.size > 0) {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        }else{
                            setEmptyView()
                        }
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }

    private fun setEmptyView() {
        ToastUtils.showShort("抱歉，没有找到相匹配的内容")
        hideHotWordView()
        tv_search_count.visibility = View.GONE
        mLayoutStatusView?.showEmpty()
    }

    private fun setSearchResult(issue: HomeNewBean.Issue) {
        loadingMore = false
        hideHotWordView()
        tv_search_count.visibility = View.VISIBLE
        tv_search_count.text = String.format(resources.getString(R.string.search_result_count), keyWords, issue.total)

        itemList = issue.itemList
        mResultAdapter.addData(issue.itemList)
    }


    /**
     * 显示热门关键字的 流式布局
     */
    private fun showHotWordView(){
        layout_hot_words.visibility = View.VISIBLE
        layout_content_result.visibility = View.GONE
    }

    /**
     * 隐藏热门关键字的 View
     */
    private fun hideHotWordView(){
        layout_hot_words.visibility = View.GONE
        layout_content_result.visibility = View.VISIBLE
    }

    /**
     * 退场动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpExitAnimation() {
        val fade = Fade()
        window.returnTransition = fade
        fade.duration = 300
    }

    /**
     * 进场动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpEnterAnimation() {
        val transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {

            }

            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                animateRevealShow()
            }

            override fun onTransitionCancel(transition: Transition) {

            }

            override fun onTransitionPause(transition: Transition) {

            }

            override fun onTransitionResume(transition: Transition) {

            }
        })
    }

    private fun setUpView() {
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        animation.duration = 300
        rel_container.startAnimation(animation)
        rel_container.visibility = View.VISIBLE
        //打开软键盘
        openKeyBord(et_search_view, applicationContext)
    }

    /**
     * 关闭软件盘
     */
    fun closeSoftKeyboard() {
        closeKeyBord(et_search_view, applicationContext)
    }


    /**
     * 展示动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateRevealShow() {
        ViewAnimUtils.animateRevealShow(
                this, rel_frame,
                fab_circle.width / 2, R.color.backgroundColor,
                object : ViewAnimUtils.OnRevealAnimationListener {
                    override fun onRevealHide() {

                    }

                    override fun onRevealShow() {
                        setUpView()
                    }
                })
    }


    // 返回事件
    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimUtils.animateRevealHide(
                    this, rel_frame,
                    fab_circle.width / 2, R.color.backgroundColor,
                    object : ViewAnimUtils.OnRevealAnimationListener {
                        override fun onRevealHide() {
                            defaultBackPressed()
                        }

                        override fun onRevealShow() {

                        }
                    })
        } else {
            defaultBackPressed()
        }
    }

    // 默认回退
    private fun defaultBackPressed() {
        closeSoftKeyboard()
        super.onBackPressed()
    }
}