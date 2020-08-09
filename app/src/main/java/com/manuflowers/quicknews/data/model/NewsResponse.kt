package com.manuflowers.quicknews.data.model

import com.squareup.moshi.Json

data class NewsResponse(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "totalResults") val totalResults: Int,
    @field:Json(name = "articles") val articles: List<ArticleResponse>
)

data class ArticleResponse(
    @field:Json(name = "author") val author: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "urlToImage") val urlToImage: String,
    @field:Json(name = "publishedAt") val publishedAt: String,
    @field:Json(name = "content") val content: String
)