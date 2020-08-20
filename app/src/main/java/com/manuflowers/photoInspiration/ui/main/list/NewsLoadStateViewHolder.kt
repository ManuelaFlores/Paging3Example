package com.manuflowers.photoInspiration.ui.main.list

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.photos_load_state_footer_item_view.view.*

class NewsLoadStateViewHolder(
    override val containerView: View,
    private val retryCallback: () -> Unit
) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(loadState: LoadState) = with(containerView) {
        progressBar.isVisible = loadState is LoadState.Loading
        retryButton.isVisible = loadState is LoadState.Error
        retryButton.setOnClickListener { retryCallback() }
        errorMessageTextView.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        errorMessageTextView.text = (loadState as? LoadState.Error)?.error?.message
    }

}