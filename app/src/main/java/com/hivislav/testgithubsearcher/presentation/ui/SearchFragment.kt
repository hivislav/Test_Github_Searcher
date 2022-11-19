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
import com.hivislav.testgithubsearcher.R
import com.hivislav.testgithubsearcher.databinding.FragmentSearchBinding
import com.hivislav.testgithubsearcher.domain.Repo
import com.hivislav.testgithubsearcher.presentation.GithubApplication
import com.hivislav.testgithubsearcher.presentation.adapter.SearchFragmentAdapter
import com.hivislav.testgithubsearcher.presentation.viewmodel.SearchFragmentAppState
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
    private val adapter = SearchFragmentAdapter()

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
        adapter.onRepoClickListener = object : SearchFragmentAdapter.OnRepoClickListener {
            override fun onRepoClick(repo: Repo) {
                openRepoLinkInBrowser(repo)
            }
        }

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[SearchFragmentViewModel::class.java]

        viewModel.loadedRepos.observe(viewLifecycleOwner) {
            renderData(it)
        }

        binding.textInputLayoutSearchFragment.setEndIconOnClickListener {
            findRepos()
        }
    }

    private fun openRepoLinkInBrowser(repo: Repo) {
        val url = Uri.parse(repo.urlRepository)
        val openLinkIntent = Intent(Intent.ACTION_VIEW, url)
        val chooser = Intent.createChooser(openLinkIntent, "Choose app for open link")

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun findRepos() {
        val request = binding.editTextSearchFragment.text.toString()
        viewModel.loadRepos(request)
    }

    private fun renderData(appState: SearchFragmentAppState) {
        when (appState) {
            is SearchFragmentAppState.Success -> {
                adapter.submitList(appState.reposList)
            }
            is SearchFragmentAppState.Error -> {
                //TODO add errorState
                Toast.makeText(
                    requireContext(),
                    "Error: ${appState.errorMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is SearchFragmentAppState.Loading -> {
                //TODO add progress
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        fun newInstance() = SearchFragment()
    }
}