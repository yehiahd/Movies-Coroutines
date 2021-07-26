package com.yehia.movies.ui.main_screen

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.yehia.movies.databinding.ActivityMainBinding
import com.yehia.movies.model.Movie
import com.yehia.movies.util.Resource
import com.yehia.movies.util.Status
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = MoviesAdapter()

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactoryProvider()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter.setOnItemClickListener {
            Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
        }

        binding.recyclerMovies.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = this@MainActivity.adapter
        }

        adapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                binding.progressBar.isVisible = true
            } else {
                binding.progressBar.isVisible = false

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
                }
            }

        }

        lifecycleScope.launchWhenCreated {
            viewModel.getPopularMovies().observe(this@MainActivity, {
                renderUI(it)
            })
        }
    }

    private fun renderUI(response: Resource<PagingData<Movie>>) {
        when (response.status) {
            Status.SUCCESS -> {
                renderSuccessState(response)
            }
            Status.ERROR -> renderErrorState(response)
            Status.LOADING -> renderLoadingState()
            Status.NOTHING -> {
            }
        }
    }

    private fun renderLoadingState() {
        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
    }

    private fun renderErrorState(errorResponse: Resource<PagingData<Movie>>) {
        Toast.makeText(this, "${errorResponse.errorMessage}", Toast.LENGTH_SHORT).show()
    }

    private fun renderSuccessState(successResponse: Resource<PagingData<Movie>>) {

        lifecycleScope.launch {
            adapter.submitData(successResponse.data!!)
        }
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q &&
            isTaskRoot &&
            supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.backStackEntryCount ?: 0 == 0 &&
            supportFragmentManager.backStackEntryCount == 0
        ) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
}