package com.manuflowers.quicknews.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.manuflowers.quicknews.R
import com.manuflowers.quicknews.data.networking.buildApiService
import com.manuflowers.quicknews.data.repository.NewsRepository
import com.manuflowers.quicknews.ui.main.list.ArticlesAdapter
import com.manuflowers.quicknews.ui.main.list.NewsLoadStateAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val repository = NewsRepository(buildApiService())
    private val adapter =
        ArticlesAdapter()

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))
            .get(MainViewModel::class.java)

        initAdapter()
        setupListeners()
        search(setNewQueryValue())
        mainRetryButton.setOnClickListener { adapter.retry() }
    }

    private fun initAdapter() {
        mainRecyclerView.adapter = adapter

        mainRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = NewsLoadStateAdapter { adapter.retry() },
            footer = NewsLoadStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            mainRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            mainProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            mainRetryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupListeners() {

        searchEditText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                sendNewRequest()
                true
            } else {
                false
            }
        }

        searchEditText.setOnEditorActionListener {_, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                sendNewRequest()
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy {
                    it.refresh
                }
                .filter { it.refresh is LoadState.NotLoading }
                .collect{ mainRecyclerView.scrollToPosition(0) }
        }
    }

    private fun sendNewRequest() {
        searchEditText.text.trim().let {
            if (it.isNotEmpty()) {
                search(it.toString())
            }
        }
    }

    private fun search(query: String) {
        lifecycleScope.launch {
            mainViewModel.searchNews(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setNewQueryValue(): String {
        val currentQuery = searchEditText.text.toString()
        return if (currentQuery.isEmpty()) "cine" else currentQuery
    }
}