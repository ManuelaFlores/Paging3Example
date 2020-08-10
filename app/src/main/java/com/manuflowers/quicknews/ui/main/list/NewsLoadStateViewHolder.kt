package com.manuflowers.quicknews.ui.main.list

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_load_state_footer_item_view.view.*

class NewsLoadStateViewHolder(
    override val containerView: View
) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(loadState: LoadState, retryListener: () -> Unit) = with(containerView) {
        if (loadState is LoadState.Error) errorMessageTextView.text =
            loadState.error.localizedMessage

        progressBar.isVisible = loadState is LoadState.Loading
        retryButton.isVisible = loadState !is LoadState.Loading
        errorMessageTextView.isVisible = loadState !is LoadState.Loading
        retryButton.setOnClickListener { retryListener.invoke() }
    }

}