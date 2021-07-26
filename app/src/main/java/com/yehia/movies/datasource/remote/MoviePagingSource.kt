package com.yehia.movies.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yehia.movies.model.Movie

class MoviePagingSource(private val apiService: ApiService) : PagingSource<Int, Movie>() {

    private val INITIAL_PAGE = 1

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val currentPage = params.key ?: INITIAL_PAGE
        return try {

            val response = apiService.getPopularMovies(page = currentPage)
            val movies = response.movies

            val nextPage = if (movies.isEmpty()) null else currentPage + 1

            LoadResult.Page(
                data = response.movies,
                prevKey = if (currentPage == INITIAL_PAGE) null else currentPage,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}