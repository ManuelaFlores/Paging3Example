package com.manuflowers.quicknews.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.manuflowers.quicknews.data.model.ArticleResponse
import com.manuflowers.quicknews.data.networking.NewsApi
import kotlinx.coroutines.flow.Flow

class NewsRepository(
    private val newsApi: NewsApi
) {
    fun getSearchResultsNews(query: String): Flow<PagingData<ArticleResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(newsApi, query) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}