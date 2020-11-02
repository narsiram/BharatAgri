package com.sumcorp.sliceassignment.retrofit

import com.sumcorp.bharatagri.data.local.entity.model.MovieData
import com.sumcorp.bharatagri.data.local.entity.model.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String?
    ): Call<MovieListResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): Call<MovieData>
}