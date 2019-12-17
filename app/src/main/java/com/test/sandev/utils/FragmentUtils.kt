package com.test.sandev.utils

import androidx.fragment.app.Fragment
import com.test.sandev.R
import com.test.sandev.ui.fragment.HomeFragment
import com.test.sandev.ui.fragment.VBangFragment
import com.test.sandev.ui.fragment.YueDanFragment

class FragmentUtils private constructor() {

    val homeFragment by lazy { com.test.sandev.ui.fragment.HomeFragment() }
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