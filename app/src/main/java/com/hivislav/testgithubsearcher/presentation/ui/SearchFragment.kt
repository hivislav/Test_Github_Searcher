package com.hivislav.testgithubsearcher.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.hivislav.testgithubsearcher.R
import com.hivislav.testgithubsearcher.databinding.FragmentSearchBinding
import com.hivislav.testgithubsearcher.domain.Repo
import com.hivislav.testgithubsearcher.hideKeyboard
import com.hivislav.testgithubsearcher.presentation.GithubApplication
import com.hivislav.testgithubsearcher.presentation.adapter.recycler.ViewPagerBaseFragmentAdapter
import com.hivislav.testgithubsearcher.presentation.viewmodel.AppStateSearchFragment
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

    @Inject
    lateinit var imageLoader: ImageLoader

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
            binding.editTextSearchFragment.setText(EMPTY_STRING)
        }

        binding.editTextSearchFragment.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                findRepos()
                textView.hideKeyboard()
                true
            } else {
                false
            }
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

    private fun renderData(appStateSearchFragment: AppStateSearchFragment) {
        when (appStateSearchFragment) {
            is AppStateSearchFragment.Success -> {
                hideProgress()
                hideNothingFoundError()
                adapter.setReposData(appStateSearchFragment.reposList)
            }
            is AppStateSearchFragment.Error -> {
                hideProgress()

                if (appStateSearchFragment.errorMessage.contains(ERROR_HTTP_404)) {
                    showNothingFoundError()
                } else {
                    showErrorSnackBar(appStateSearchFragment)
                }
            }
            is AppStateSearchFragment.Loading -> {
                showProgress()
            }
        }
    }

    private fun showErrorSnackBar(appStateSearchFragment: AppStateSearchFragment.Error) {
        Snackbar.make(
            binding.root,
            "Error: ${appStateSearchFragment.errorMessage}",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("Try again") {
                viewModel.loadRepos(binding.editTextSearchFragment.text.toString())
            }
            .show()
    }

    private fun showNothingFoundError() {
        binding.errorImageSearchFragment.visibility = View.VISIBLE
        binding.errorTextViewSearchFragment.visibility = View.VISIBLE
        binding.recyclerSearchFragment.visibility = View.GONE
    }

    private fun hideNothingFoundError() {
        binding.errorImageSearchFragment.visibility = View.GONE
        binding.errorTextViewSearchFragment.visibility = View.GONE
        binding.recyclerSearchFragment.visibility = View.VISIBLE
    }

    private fun showProgress() {
        binding.textInputLayoutSearchFragment.isEndIconVisible = false
        binding.progressSearchFragment.visibility = View.VISIBLE
        binding.progressSearchFragment.load(R.drawable.running_cat, imageLoader)
    }

    private fun hideProgress() {
        binding.textInputLayoutSearchFragment.isEndIconVisible = true
        binding.progressSearchFragment.visibility = View.GONE
    }

    companion object {
        fun newInstance() = SearchFragment()
        private const val EMPTY_STRING = ""
        private const val ERROR_HTTP_404 = "HTTP 404"
    }
}