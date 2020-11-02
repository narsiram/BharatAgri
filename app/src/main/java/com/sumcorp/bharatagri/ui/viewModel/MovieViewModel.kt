package com.sumcorp.bharatagri.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumcorp.bharatagri.data.local.entity.model.MovieData
import com.sumcorp.bharatagri.data.local.entity.model.MovieListResponse
import com.sumcorp.bharatagri.data.repo.MovieRepository
import com.sumcorp.sliceassignment.retrofit.NetworkHelper

class MovieViewModel(

    private var networkHelper: NetworkHelper,
    private var movieRepository: MovieRepository
) : ViewModel() {

    private val _movieListResponse = MutableLiveData<MovieListResponse>()

    val movieListResponse: LiveData<MovieListResponse>
        get() = _movieListResponse

    //single movie
    private val _movieResponse = MutableLiveData<MovieData>()

    val movieResponse: LiveData<MovieData>
        get() = _movieResponse

    private val _errorResponse = MutableLiveData<String>()

    val errorResponse: LiveData<String>
        get() = _errorResponse

    fun getMovies(page: Int, sorting: String) {
        if (networkHelper.isNetworkConnected()) {
            movieRepository.fetchMovies(page, sorting, {
                _movieListResponse.postValue(it)
            }, {
                _errorResponse.postValue(it)
            })
        } else {
            if (page == 1)
                movieRepository.fetchMoviesLocally {
                    if (it != null && it.results.isNotEmpty())
                        _movieListResponse.postValue(it)
                    else
                        _errorResponse.postValue("response empty")


                }
        }
    }


    fun getMovieDetail(movieId: String) {
        if (networkHelper.isNetworkConnected()) {
            movieRepository.fetchMovieDetail(movieId, {
                _movieResponse.postValue(it)
            }, {
                _errorResponse.postValue(it)
            })
        }
    }
}