package com.hivislav.testgithubsearcher.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hivislav.testgithubsearcher.databinding.ItemSearchFragmentRecyclerBinding
import com.hivislav.testgithubsearcher.domain.Repo

class SearchFragmentAdapter(
    private var reposList: MutableList<Pair<Repo, Boolean>> = mutableListOf()
) :
    RecyclerView.Adapter<SearchFragmentAdapter.SearchViewHolder>() {

    var onRepoClickListener: OnRepoClickListener? = null

    fun setReposData(reposList: List<Pair<Repo, Boolean>>) {
        val diffCallback = SearchFragmentDiffCallback(this.reposList, reposList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.reposList = reposList.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchFragmentRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(reposList[position])
    }

    override fun getItemCount(): Int {
        return reposList.size
    }

    inner class SearchViewHolder(private val binding: ItemSearchFragmentRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Pair<Repo, Boolean>) {

            itemView.apply {
                with(binding) {
                    avatarImage.load(repo.first.urlUserAvatar)
                    userNameTextView.text = repo.first.userName
                    repoNameTextView.text = repo.first.repoName
                    buttonOpenLink.visibility = if (repo.second) View.VISIBLE else View.GONE
                    buttonDownloadZip.visibility = if (repo.second) View.VISIBLE else View.GONE
                }
                setOnClickListener {
                    reposList[layoutPosition] = reposList[layoutPosition].let {
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
                }
                binding.buttonOpenLink.setOnClickListener {
                    onRepoClickListener?.onOpenLinkClick(repo.first)
                }
                binding.buttonDownloadZip.setOnClickListener {
                    onRepoClickListener?.onDownloadClick(repo.first)
                }
            }
        }
    }

    interface OnRepoClickListener {
        fun onOpenLinkClick(repo: Repo)
        fun onDownloadClick(repo: Repo)
    }
}