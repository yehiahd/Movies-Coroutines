package com.yehia.movies.model

import com.google.gson.annotations.SerializedName


data class PopularMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val movies: List<Movie>
)

data class Movie(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("backdrop_path") val backdropPath: String = "",
    @SerializedName("original_title") val originalTitle: String = "",
    @SerializedName("title") val title: String = "Hamada",
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("overview") val overview: String = "",
    @SerializedName("vote_average") val voteAverage: Double = 0.0
)