package com.manuflowers.quicknews.data.repository

import androidx.paging.PagingSource
import com.manuflowers.quicknews.data.model.ArticleResponse
import com.manuflowers.quicknews.data.networking.API_KEY
import com.manuflowers.quicknews.data.networking.NewsApi
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(
    private val service: NewsApi,
    private val query: String
) : PagingSource<Int, ArticleResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleResponse> {
        val position = params.key ?: NEWS_STARTING_PAGE_INDEX
        val apiQuery = query
        return try {
            val response = service.getNews(
                query = apiQuery,
                page = position,
                pageSize = params.loadSize,
                apiKey = API_KEY
            )
            val articles = response.articles
            LoadResult.Page(
                data = articles,
                prevKey = if (position == NEWS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (articles.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }
}

const val NEWS_STARTING_PAGE_INDEX = 1