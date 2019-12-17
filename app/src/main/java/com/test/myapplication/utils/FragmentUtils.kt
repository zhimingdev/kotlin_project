package com.test.myapplication.utils

import androidx.fragment.app.Fragment
import com.test.myapplication.R
import com.test.myapplication.ui.fragment.HomeFragment
import com.test.myapplication.ui.fragment.VBangFragment
import com.test.myapplication.ui.fragment.YueDanFragment

class FragmentUtils private constructor() {

    val homeFragment by lazy { HomeFragment() }
    val mvFragment by lazy { VBangFragment() }
    val yueDanFragment by lazy { YueDanFragment() }

    companion object {
        val instance by lazy { FragmentUtils() }
    }

    fun getFragment(tabId: Int) : Fragment {
        return when (tabId) {
            R.id.tab_home -> return homeFragment
            R.id.tab_mv -> return mvFragment
            R.id.tab_yuedan -> return yueDanFragment
            else -> return homeFragment
        }
    }
}