package com.sumcorp.bharatagri.data.local.entity.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.sumcorp.bharatagri.data.local.typeConverter.MovieTypeConverter

@Entity(tableName = "tbl_movie_data")
data class MovieListResponse(
    @PrimaryKey
    val page: Int = 1,

    @ColumnInfo(name = "movie_list")
    @TypeConverters(MovieTypeConverter::class)
//    @SerializedName("results")
    var results: ArrayList<MovieData>
)