package com.hivislav.testgithubsearcher.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hivislav.testgithubsearcher.BuildConfig
import com.hivislav.testgithubsearcher.databinding.FragmentDownloadBinding
import com.hivislav.testgithubsearcher.domain.Repo
import com.hivislav.testgithubsearcher.presentation.GithubApplication
import com.hivislav.testgithubsearcher.presentation.adapter.recycler.ViewPagerBaseFragmentAdapter
import com.hivislav.testgithubsearcher.presentation.viewmodel.DownloadFragmentViewModel
import com.hivislav.testgithubsearcher.presentation.viewmodel.AppState
import com.hivislav.testgithubsearcher.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class DownloadFragment : Fragment() {

    private var _binding: FragmentDownloadBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentDownloadBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: DownloadFragmentViewModel

    private val component by lazy {
        (requireActivity().application as GithubApplication).component
    }

    private val adapter = ViewPagerBaseFragmentAdapter()

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerDownloadFragment.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[DownloadFragmentViewModel::class.java]

        viewModel.downloadedRepos.observe(viewLifecycleOwner) {
            renderData(it)
        }

        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDownloadRepos()
    }

    override fun onPause() {
        super.onPause()
        viewModel.getDownloadRepos()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun setupClickListeners() {
        adapter.onRepoClickListener = object : ViewPagerBaseFragmentAdapter.OnRepoClickListener {
            override fun onOpenLinkClick(repo: Repo) {
                openRepoLinkInBrowser(repo)
            }
            override fun onDownloadClick(repo: Repo) {
                openZipFromLocalStorage(repo)
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

    private fun openZipFromLocalStorage(repo: Repo) {
        val path = Environment.getExternalStoragePublicDirectory(repo.archiveUrl)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(path.extension).toString()
        val uri = FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", path)

        val openLinkIntent = Intent(Intent.ACTION_VIEW, uri)
        openLinkIntent.setDataAndType(uri, mimeType)
        openLinkIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        if (openLinkIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(openLinkIntent)
        } else {
            Toast.makeText(
                requireActivity(),
                "Application not found to open link",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        fun newInstance() = DownloadFragment()
    }
}