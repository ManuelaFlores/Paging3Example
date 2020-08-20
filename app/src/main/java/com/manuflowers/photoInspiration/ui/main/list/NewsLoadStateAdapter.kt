package com.manuflowers.photoInspiration.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.manuflowers.photoInspiration.R

class NewsLoadStateAdapter(private val adapter: PhotosAdapter) : LoadStateAdapter<NewsLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: NewsLoadStateViewHolder, loadState: LoadState) {
        val params = holder.itemView.layoutParams
        if (params is StaggeredGridLayoutManager.LayoutParams) {
            params.isFullSpan = true
        }
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NewsLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.photos_load_state_footer_item_view, parent, false)
        return NewsLoadStateViewHolder(
            view
        ) { adapter.retry()}
    }
}