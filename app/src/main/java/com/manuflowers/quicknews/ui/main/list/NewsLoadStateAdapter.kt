package com.manuflowers.quicknews.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.manuflowers.quicknews.R

class NewsLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<NewsLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: NewsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NewsLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_load_state_footer_item_view, parent, false)
        return NewsLoadStateViewHolder(
            view
        )
    }
}