package com.hivislav.testgithubsearcher.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hivislav.testgithubsearcher.databinding.ItemSearchFragmentRecyclerBinding
import com.hivislav.testgithubsearcher.domain.Repo

class SearchFragmentAdapter :
    ListAdapter<Repo, SearchFragmentAdapter.SearchViewHolder>(SearchFragmentDiffCallback) {

    var onRepoClickListener: OnRepoClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchFragmentRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchViewHolder(private val binding: ItemSearchFragmentRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            itemView.apply {
                with(binding) {
                    avatarImage.load(repo.urlUserAvatar)
                    userNameTextView.text = repo.userName
                    repoNameTextView.text = repo.repoName
                }
                setOnClickListener {
                    onRepoClickListener?.onRepoClick(repo)
                }
            }
        }
    }

    interface OnRepoClickListener {
        fun onRepoClick (repo: Repo)
    }
}