package com.test.myapplication.ui.activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.SPUtils
import com.test.myapplication.base.BaseActivity
import com.test.myapplication.utils.FragmentUtils
import com.test.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(){
    //需要显示的fragment
    var showfragment : Fragment? = null
    //当前显示的fragment
    var currentfragment :Fragment? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initLisenter() {
        bottomBar.setOnTabSelectListener{
            var transaction = supportFragmentManager.beginTransaction()
             showfragment = FragmentUtils.instance.getFragment(it)
            if (currentfragment != null && showfragment != currentfragment) {
                transaction.hide(currentfragment!!)
            }
            if (showfragment!!.isAdded) {
                transaction.show(FragmentUtils.instance.getFragment(it))
            }else{
                transaction.add(R.id.container,showfragment!!)
            }
            transaction.commit()
            currentfragment = showfragment
        }
    }
}
