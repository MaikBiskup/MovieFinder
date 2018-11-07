package com.example.maikbiskup.moviefinder.model.data_model

data class Movie(
    val imdbID: String?,
    val Title: String?,
    val Year: String?,
    val Ratings: List<Rating>?,
    val Plot: String?,
    val Actors: String?,
    val Poster: String?)