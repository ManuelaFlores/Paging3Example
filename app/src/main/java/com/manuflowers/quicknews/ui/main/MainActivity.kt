package com.manuflowers.quicknews.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.manuflowers.quicknews.R
import com.manuflowers.quicknews.data.networking.buildApiService
import com.manuflowers.quicknews.data.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val repository = NewsRepository(buildApiService())
    private val adapter = ArticlesAdapter()

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel =  ViewModelProvider(this, MainViewModelFactory(repository))
        .get(MainViewModel::class.java)

        mainRecyclerView.adapter = adapter
        search("cine")
    }

    private fun search(query: String) {
        lifecycleScope.launch {
            mainViewModel.searchNews(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}