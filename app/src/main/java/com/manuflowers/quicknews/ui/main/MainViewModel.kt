package com.manuflowers.quicknews.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manuflowers.quicknews.data.model.ArticleResponse
import com.manuflowers.quicknews.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    fun searchNews(query: String): Flow<PagingData<ArticleResponse>> {
        return repository
            .getSearchResultsNews(query)
            .cachedIn(viewModelScope)
    }
}