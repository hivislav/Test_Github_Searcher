package com.hivislav.testgithubsearcher.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hivislav.testgithubsearcher.databinding.FragmentSearchBinding
import com.hivislav.testgithubsearcher.domain.Repo
import com.hivislav.testgithubsearcher.presentation.GithubApplication
import com.hivislav.testgithubsearcher.presentation.adapter.recycler.ViewPagerBaseFragmentAdapter
import com.hivislav.testgithubsearcher.presentation.viewmodel.AppState
import com.hivislav.testgithubsearcher.presentation.viewmodel.SearchFragmentViewModel
import com.hivislav.testgithubsearcher.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSearchBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SearchFragmentViewModel

    private val component by lazy {
        (requireActivity().application as GithubApplication).component
    }
    private val adapter = ViewPagerBaseFragmentAdapter()

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerSearchFragment.adapter = adapter
        setupClickListeners()

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[SearchFragmentViewModel::class.java]

        viewModel.loadedRepos.observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        binding.textInputLayoutSearchFragment.setEndIconOnClickListener {
            findRepos()
        }

        adapter.onRepoClickListener = object : ViewPagerBaseFragmentAdapter.OnRepoClickListener {
            override fun onOpenLinkClick(repo: Repo) {
                openRepoLinkInBrowser(repo)
            }
            override fun onDownloadClick(repo: Repo) {
                viewModel.downloadRepo(repo)
            }
        }
    }

    private fun openRepoLinkInBrowser(repo: Repo) {
        val url = Uri.parse(repo.urlRepository)
        val openLinkIntent = Intent(Intent.ACTION_VIEW, url)
        val chooser = Intent.createChooser(openLinkIntent, "Choose app for open link").apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        if (openLinkIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(chooser)
        } else {
            Toast.makeText(
                requireActivity(),
                "Application not found to open link",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun findRepos() {
        val request = binding.editTextSearchFragment.text.toString()
        viewModel.loadRepos(request)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                adapter.setReposData(appState.reposList)
            }
            is AppState.Error -> {
                //TODO add errorState
                Toast.makeText(
                    requireContext(),
                    "Error: ${appState.errorMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is AppState.Loading -> {
                //TODO add progress
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}