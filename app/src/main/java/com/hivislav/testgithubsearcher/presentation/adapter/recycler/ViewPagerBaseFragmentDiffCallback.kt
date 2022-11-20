package com.hivislav.testgithubsearcher.presentation.adapter.recycler

import androidx.recyclerview.widget.DiffUtil
import com.hivislav.testgithubsearcher.domain.Repo

class ViewPagerBaseFragmentDiffCallback(
    private var oldItems: List<Pair<Repo, Boolean>>,
    private var newItems: List<Pair<Repo, Boolean>>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.repoId == newItems[newItemPosition].first.repoId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}