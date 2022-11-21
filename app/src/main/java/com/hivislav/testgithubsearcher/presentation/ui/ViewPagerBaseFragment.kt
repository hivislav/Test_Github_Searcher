package com.hivislav.testgithubsearcher.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.hivislav.testgithubsearcher.R
import com.hivislav.testgithubsearcher.databinding.FragmentViewPagerBaseBinding
import com.hivislav.testgithubsearcher.presentation.adapter.viewpager.ViewPagerAdapter

class ViewPagerBaseFragment : Fragment() {

    private var _binding: FragmentViewPagerBaseBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("ViewPagerBaseFragmentBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(this)
        bindTabLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindTabLayout() {
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            tab.text = when (position) {
                SEARCH_TAB -> {
                    getString(R.string.search)
                }
                DOWNLOADS_TAB -> {
                    getString(R.string.downloads)
                }
                else -> EMPTY_STRING
            }
        }.attach()
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val SEARCH_TAB = 0
        private const val DOWNLOADS_TAB = 1
    }
}