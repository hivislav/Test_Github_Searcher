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

    class SearchViewHolder(private val binding: ItemSearchFragmentRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            itemView.apply {
                with(binding) {
                    avatarImage.load(repo.urlUserAvatar)
                    userNameTextView.text = repo.userName
                    repoNameTextView.text = repo.repoName
                }
            }
        }
    }
}