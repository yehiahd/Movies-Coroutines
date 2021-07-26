package com.yehia.movies.datasource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.yehia.movies.model.Movie
import com.yehia.movies.util.Constant.Api.BASE_URL
import com.yehia.movies.util.FlipperNetworkInterceptor
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(FlipperOkhttpInterceptor(FlipperNetworkInterceptor.getFlipperNetworkPlugin()))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }


    fun getPopularMoviesPaging(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 20)) {
            MoviePagingSource(apiService)
        }.flow
    }


}