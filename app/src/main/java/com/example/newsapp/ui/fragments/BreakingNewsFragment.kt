package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.ui.NewsAcitvity
import com.example.newsapp.utils.Resource
import com.example.newsapp.viewmodel.NewsViewModel
import retrofit2.Response

class BreakingNewsFragment : Fragment() {
    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapterBreakingNews: NewsAdapter
    private val TAG = "response"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_breaking_news, container, false)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //SetUp RecyclerView First
        setUpRecyclerView()
        adapterBreakingNews.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("article", it) }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }

        viewModel.getBreakingNews("eg")
        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data.let { newsResponse ->
                        adapterBreakingNews.differ.submitList(newsResponse?.articles)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message.let {
                    Toast.makeText(requireContext(),"An error occurred $it",Toast.LENGTH_LONG).show()
                    }
                }

                else -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.isVisible = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.isVisible = true
    }

    private fun setUpRecyclerView() {
        adapterBreakingNews = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = adapterBreakingNews
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}