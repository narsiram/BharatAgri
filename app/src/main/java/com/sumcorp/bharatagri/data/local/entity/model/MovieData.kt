package com.sumcorp.bharatagri.data.local.entity.model

import com.google.gson.annotations.SerializedName

data class MovieData(
    @SerializedName("vote_average")
    var voteAverage: Double,

    @SerializedName("vote_count")
    var voteCount: Long,

    @SerializedName("poster_path")
    var posterPath: String,

    @SerializedName("id")
    var id: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("overview")
    var overview: String,

    @SerializedName("release_date")
    var releaseDate: String,

    @SerializedName("status")
    var releaseStatus: String,

    @SerializedName("backdrop_path")
    var backgroundPath: String,

    @SerializedName("tagline")
    var tagline: String,

    @SerializedName("revenue")
    var revenue: String,

    @SerializedName("budget")
    var budget: String,

    @SerializedName("genres")
    var genere: List<Genere>,


    )