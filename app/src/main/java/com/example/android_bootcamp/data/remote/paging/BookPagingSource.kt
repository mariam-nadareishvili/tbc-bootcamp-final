package com.example.android_bootcamp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_bootcamp.data.remote.model.FeedBookDto
import com.example.android_bootcamp.data.remote.sevice.BookApiService

class FeedBookPagingSource(private val apiService: BookApiService) : PagingSource<Int, FeedBookDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FeedBookDto> {
        val page = params.key ?: 1
        return try {
            val books = apiService.searchBooks(page, params.loadSize)
            LoadResult.Page(
                data = books,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (books.isEmpty()) null else page + 1
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FeedBookDto>): Int? {
        return state.anchorPosition
    }
}
