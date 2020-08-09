package com.manuflowers.quicknews.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.manuflowers.quicknews.R
import com.manuflowers.quicknews.data.networking.buildApiService
import com.manuflowers.quicknews.data.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val repository = NewsRepository(buildApiService())
    private val adapter = ArticlesAdapter()

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))
            .get(MainViewModel::class.java)

        mainRecyclerView.adapter = adapter
        setupListeners()
        search(setNewQueryValue())
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