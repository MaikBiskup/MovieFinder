package com.example.maikbiskup.moviefinder.model.data_access.remote.endpoint

import com.example.maikbiskup.moviefinder.model.data_access.remote.BaseController
import com.example.maikbiskup.moviefinder.model.data_access.remote.TransformerApplySchedulers
import com.example.maikbiskup.moviefinder.model.data_model.Movie
import com.example.maikbiskup.moviefinder.model.data_model.MovieList
import io.reactivex.Single

class DefaultController : BaseController() {

    private var defaultAPI: DefaultAPI
    private val fullPlot = "full"

    init {
        val retrofit = super.getRetroFit(ENDPOINT)

        defaultAPI = retrofit.create(DefaultAPI::class.java)
    }

    // ++++++++++++++++++ POST ++++++++++++++++++

    fun movie(id: String): Single<Movie> {
        return defaultAPI.getMovie(id, fullPlot)
            .compose<Movie>(TransformerApplySchedulers.singleApplySchedulers<Movie>())
    }

    fun movies(title: String): Single<MovieList> {
        return defaultAPI.getMovies(title, 1.toString())
            .compose<MovieList>(TransformerApplySchedulers.singleApplySchedulers<MovieList>())
    }

    fun movies(title: String, pageNo: Int): Single<MovieList> {
        return defaultAPI.getMovies(title, pageNo.toString())
            .compose<MovieList>(TransformerApplySchedulers.singleApplySchedulers<MovieList>())
    }

    companion object {

        internal val ENDPOINT = ""
    }
}