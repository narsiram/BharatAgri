package com.sumcorp.bharatagri.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sumcorp.bharatagri.data.local.entity.model.MovieListResponse

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertmovies(movieResponse: MovieListResponse)

    @Query("select * from tbl_movie_data")
    fun getMovies(): MovieListResponse
}