package com.hivislav.testgithubsearcher.presentation.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hivislav.testgithubsearcher.presentation.ui.SearchFragment
import com.hivislav.testgithubsearcher.presentation.ui.ViewPagerBaseFragment

class ViewPagerAdapter(fragmentActivity: ViewPagerBaseFragment) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = arrayOf(
        SearchFragment.newInstance()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}