package com.manuflowers.quicknews.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manuflowers.quicknews.data.model.ArticleResponse
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_view_holder.view.*

class ArticlesViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(articleResponse: ArticleResponse) = with(containerView) {
        newsTitleTextView.text = articleResponse.title
        Glide.with(this)
            .load(articleResponse.urlToImage)
            .centerCrop()
            .into(newsImageView)
    }

}