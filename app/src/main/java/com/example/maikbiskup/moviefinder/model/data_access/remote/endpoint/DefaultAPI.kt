package com.example.maikbiskup.moviefinder.model.data_access.remote.endpoint

import com.example.maikbiskup.moviefinder.model.data_model.Movie
import com.example.maikbiskup.moviefinder.model.data_model.MovieList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DefaultAPI {

    @GET("/")
    fun getMovie(@Query("i") title: String, @Query("plot") fullPlot: String): Single<Movie>

    @GET("/")
    fun getMovies(@Query("s") title: String, @Query("page") page: String): Single<MovieList>
}