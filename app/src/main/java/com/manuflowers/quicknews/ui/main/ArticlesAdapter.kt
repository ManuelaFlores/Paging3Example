package com.manuflowers.quicknews.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.manuflowers.quicknews.R
import com.manuflowers.quicknews.data.model.ArticleResponse

class ArticlesAdapter :
    PagingDataAdapter<ArticleResponse, RecyclerView.ViewHolder>(ARTICLES_COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as ArticlesViewHolder).bind(repoItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_view_holder, parent, false)

        return ArticlesViewHolder(view)
    }

    companion object {
        val ARTICLES_COMPARATOR = object : DiffUtil.ItemCallback<ArticleResponse>() {
            override fun areItemsTheSame(
                oldItem: ArticleResponse,
                newItem: ArticleResponse
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ArticleResponse,
                newItem: ArticleResponse
            ): Boolean = oldItem == newItem

        }
    }
}