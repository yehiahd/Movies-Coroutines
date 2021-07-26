package com.yehia.movies.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.paging.PagingData
import com.yehia.movies.model.Movie
import com.yehia.movies.repository.MainRepository
import com.yehia.movies.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart

class MainViewModel(private val repo: MainRepository) : ViewModel() {

    suspend fun getPopularMovies() = liveData<Resource<PagingData<Movie>>> {
        repo.getPopularMovies()
            .onStart {
                emit(Resource.loading())
            }.catch { emit(Resource.error(it.message)) }
            .collectLatest {
                emit(Resource.success(it))
            }
    }

}