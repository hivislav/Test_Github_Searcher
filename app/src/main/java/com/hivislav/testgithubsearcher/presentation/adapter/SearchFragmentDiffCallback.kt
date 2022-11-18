package com.hivislav.testgithubsearcher.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hivislav.testgithubsearcher.domain.Repo

object SearchFragmentDiffCallback: DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.repoId == newItem.repoId
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem == newItem
    }
}