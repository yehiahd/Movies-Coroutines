package com.yehia.movies.repository

import androidx.paging.PagingData
import com.yehia.movies.datasource.remote.RemoteDataSource
import com.yehia.movies.model.Movie
import kotlinx.coroutines.flow.Flow

class MainRepository(private val dataSource: RemoteDataSource) {

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return dataSource.getPopularMoviesPaging()
    }
}