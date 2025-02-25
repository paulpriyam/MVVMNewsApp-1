package com.androiddevs.mvvmnewsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapter.NewsAdapter
import com.androiddevs.mvvmnewsapp.utils.ResponseWrapper
import com.androiddevs.mvvmnewsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_breaking_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        initAdapter()
        viewModel.getBreakingNews()

        lifecycleScope.launch {
            viewModel.newsPager?.collectLatest {
                if (::newsAdapter.isInitialized) {
                    newsAdapter.submitData(it)
                }
            }
        }
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
//        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer { result ->
//            when (result) {
//                is ResponseWrapper.Loading -> {
//                    showProgressBar()
//                }
//                is ResponseWrapper.Error -> {
//                    hideProgressBar()
//                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
//                }
//                is ResponseWrapper.Success -> {
//                    hideProgressBar()
//                    result.data?.let {
//                        newsAdapter.differ.submitList(it.articles)
//                    }
//                }
//            }
//        })
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        newsAdapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.Loading -> {
                    showProgressBar()
                }
                is LoadState.NotLoading -> {
                    hideProgressBar()
                }
                is LoadState.Error -> {
                    hideProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }
}