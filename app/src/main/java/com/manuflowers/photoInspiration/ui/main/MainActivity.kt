package com.manuflowers.photoInspiration.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.manuflowers.photoInspiration.R
import com.manuflowers.photoInspiration.application.QuickNewsApplication
import com.manuflowers.photoInspiration.data.networking.buildApiService
import com.manuflowers.photoInspiration.data.repository.PhotosRepository
import com.manuflowers.photoInspiration.ui.main.list.PhotosAdapter
import com.manuflowers.photoInspiration.ui.main.list.NewsLoadStateAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {

    private val repository = PhotosRepository(buildApiService(), QuickNewsApplication.PHOTOS_DATABASE)
    private val adapter = PhotosAdapter()

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))
            .get(MainViewModel::class.java)

        initAdapter()
        setupListeners()
        search()
        //mainRetryButton.setOnClickListener { adapter.retry() }
    }

    private fun initAdapter() {
        mainRecyclerView.adapter = adapter

        mainRecyclerView.adapter = adapter.withLoadStateFooter(
            footer = NewsLoadStateAdapter(adapter)
        )

        //Some common UI operations
        /*adapter.addLoadStateListener { loadState ->
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
        }*/
    }

    private fun setupListeners() {
        //Managing loading state
        /*lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy {
                    it.refresh
                }
                .filter { it.refresh is LoadState.NotLoading }
                .collect{ mainRecyclerView.scrollToPosition(0) }
        }*/
    }

    private fun search() {
        lifecycleScope.launch {
            mainViewModel.getPhotos().collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { state ->
                mainProgressBar.isVisible = state.refresh is LoadState.Loading
            }
        }
    }
}