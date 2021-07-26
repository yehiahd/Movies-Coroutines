package com.yehia.movies.datasource.remote

import com.yehia.movies.BuildConfig
import com.yehia.movies.model.PopularMoviesResponse
import com.yehia.movies.util.Constant.Api.API_KEY
import com.yehia.movies.util.Constant.Api.PAGE
import com.yehia.movies.util.Constant.Api.POPULAR
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(POPULAR)
    suspend fun getPopularMovies(
        @Query(API_KEY) apiKey: String = BuildConfig.API_KEY,
        @Query(PAGE) page: Int
    ): PopularMoviesResponse

}