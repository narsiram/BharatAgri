package com.sumcorp.bharatagri.data.repo

import android.text.TextUtils
import com.sumcorp.bharatagri.data.local.dao.MovieDao
import com.sumcorp.bharatagri.data.local.entity.model.MovieData
import com.sumcorp.bharatagri.data.local.entity.model.MovieListResponse
import com.sumcorp.bharatagri.ui.util.Constants
import com.sumcorp.sliceassignment.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepoImplementation(private val movieDao: MovieDao, private val request: ApiService) :
    MovieRepository {

    override fun fetchMovies(
        page: Int,
        sorting: String,
        onSuccess: (MovieListResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val call = request.getMovies(
            Constants.API_KEY,
            page,
            if (TextUtils.isEmpty(sorting)) null else sorting
        )

        call.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val movieResponse = response.body()

                    Thread {
                        if (page == 1)
                            movieDao.insertmovies(movieResponse!!)

                        onSuccess(movieResponse!!)

                    }.start()

                } else {
                    onError(response.message())
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                onError("Please try again after some time")
            }

        })
    }

    override fun fetchMovieDetail(
        movieId: String,
        onSuccess: (MovieData) -> Unit,
        onError: (String) -> Unit
    ) {
        val call = request.getMovieDetail(movieId, Constants.API_KEY)

        call.enqueue(object : Callback<MovieData> {
            override fun onResponse(
                call: Call<MovieData>,
                response: Response<MovieData>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val movieResponse = response.body()
                    onSuccess(movieResponse!!)

                } else {
                    onError(response.message())
                }
            }

            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                onError("Please try again after some time")
            }

        })
    }

    override fun fetchMoviesLocally(onSuccess: (MovieListResponse?) -> Unit) {
        Thread {
            onSuccess(movieDao.getMovies())
        }.start()
    }
}