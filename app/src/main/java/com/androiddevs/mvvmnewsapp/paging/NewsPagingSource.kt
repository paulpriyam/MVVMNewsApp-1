package com.androiddevs.mvvmnewsapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.androiddevs.mvvmnewsapp.model.Article
import com.androiddevs.mvvmnewsapp.model.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository

class NewsPagingSource(
    private val newsRepository: NewsRepository,
    private val countryCode: String? = null,
    private val searchTerm: String? = null
) : PagingSource<Int, Article>() {
    private val DefaultPageNumber = 1
    private var response: NewsResponse? = null
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val currentPage = params.key ?: DefaultPageNumber

        response = if (!searchTerm.isNullOrBlank()) {
            newsRepository.getSearchedNews(searchTerm, 20, currentPage).body()
        } else {
            newsRepository.getBreakingNews(countryCode.orEmpty(),params.loadSize,currentPage).body()
        }

        try {
            response?.articles?.let {
                return LoadResult.Page(
                    data = it,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (it.isEmpty()) null else currentPage + 1
                )
            } ?: run {
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            return LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )
        }

    }
}