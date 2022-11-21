package com.hivislav.testgithubsearcher.presentation.adapter.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.hivislav.testgithubsearcher.databinding.ItemLocalFragmentRecyclerBinding
import com.hivislav.testgithubsearcher.databinding.ItemRemoteFragmentRecyclerBinding
import com.hivislav.testgithubsearcher.domain.Repo

class ViewPagerBaseFragmentAdapter(
    private var reposList: MutableList<Pair<Repo, Boolean>> = mutableListOf()
) :
    RecyclerView.Adapter<ViewPagerBaseFragmentAdapter.BaseHolder>() {

    var onRepoClickListener: OnRepoClickListener? = null

    fun setReposData(reposList: List<Pair<Repo, Boolean>>) {
        val diffCallback = ViewPagerBaseFragmentDiffCallback(this.reposList, reposList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.reposList = reposList.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {

        return when(viewType) {
            Repo.REMOTE_VIEW_TYPE -> {
                val binding = ItemRemoteFragmentRecyclerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                RemoteViewHolder(binding)
            }
            Repo.LOCAL_VIEW_TYPE -> {
                val binding = ItemLocalFragmentRecyclerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LocalViewHolder(binding)
            }

            else -> {throw RuntimeException("Unknown view type")}
        }
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bind(reposList[position])
    }

    override fun getItemCount(): Int {
        return reposList.size
    }

    override fun getItemViewType(position: Int): Int {
        return reposList[position].first.itemViewType
    }

    abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(repo: Pair<Repo, Boolean>)
    }

    inner class RemoteViewHolder(private val binding: ItemRemoteFragmentRecyclerBinding) :
        BaseHolder(binding.root) {
        override fun bind(repo: Pair<Repo, Boolean>) {

            itemView.apply {
                with(binding) {
                    avatarImage.load(repo.first.urlUserAvatar) {
                        transformations(RoundedCornersTransformation(25f))
                    }
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

    inner class LocalViewHolder(private val binding: ItemLocalFragmentRecyclerBinding) :
        BaseHolder(binding.root) {
        override fun bind(repo: Pair<Repo, Boolean>) {

            itemView.apply {
                with(binding) {
                    avatarImage.load(repo.first.urlUserAvatar) {
                        transformations(RoundedCornersTransformation(25f))
                    }
                    userNameTextView.text = repo.first.userName
                    repoNameTextView.text = repo.first.repoName
                    buttonOpenLink.visibility = if (repo.second) View.VISIBLE else View.GONE
                    buttonOpenZip.visibility = if (repo.second) View.VISIBLE else View.GONE
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
                binding.buttonOpenZip.setOnClickListener {
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