package com.sumcorp.bharatagri.data.repo

import com.sumcorp.bharatagri.data.local.entity.model.MovieData
import com.sumcorp.bharatagri.data.local.entity.model.MovieListResponse

interface MovieRepository {

    fun fetchMovies(
        page: Int,
        sorting: String,
        onSuccess: (MovieListResponse) -> Unit,
        onError: (String) -> Unit
    )

    fun fetchMovieDetail(movieId: String, onSuccess: (MovieData) -> Unit, onError: (String) -> Unit)

    fun fetchMoviesLocally(onSuccess: (MovieListResponse?) -> Unit)

}