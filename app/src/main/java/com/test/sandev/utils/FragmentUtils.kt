package com.test.sandev.utils

import androidx.fragment.app.Fragment
import com.test.sandev.R
import com.test.sandev.ui.fragment.HomeFragment
import com.test.sandev.ui.fragment.KeFuFragment
import com.test.sandev.ui.fragment.VBangFragment
import com.test.sandev.ui.fragment.YueDanFragment

class FragmentUtils private constructor() {

    private val homeFragment by lazy { HomeFragment() }
    private val mvFragment by lazy { VBangFragment() }
    private val yueDanFragment by lazy { YueDanFragment() }
    private val kefufragment by lazy { KeFuFragment() }

    companion object {
        val instance by lazy { FragmentUtils() }
    }

    fun getFragment(tabId: Int) : Fragment {
        return when (tabId) {
            R.id.tab_home -> return homeFragment
            R.id.tab_mv -> return mvFragment
            R.id.tab_kefu -> return kefufragment
            R.id.tab_yuedan -> return yueDanFragment
            else -> return homeFragment
        }
    }
}