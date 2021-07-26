package com.yehia.movies.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yehia.movies.datasource.remote.RemoteDataSource
import com.yehia.movies.repository.MainRepository

class MainViewModelFactoryProvider : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(MainRepository(RemoteDataSource())) as T
    }
}