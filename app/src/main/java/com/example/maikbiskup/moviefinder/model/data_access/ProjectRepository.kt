package com.example.maikbiskup.moviefinder.model.data_access

import android.arch.lifecycle.LiveData
import com.example.maikbiskup.moviefinder.model.data_access.remote.endpoint.DefaultController
import de.vectron_systems_ag.gethappy.consumer.rest.retrofit.ErrorHandling.SimpleErrorHandler
import io.reactivex.functions.Consumer
import android.arch.lifecycle.MutableLiveData
import com.example.maikbiskup.moviefinder.model.data_model.Movie
import com.example.maikbiskup.moviefinder.model.data_model.MovieList
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.Exception


object ProjectRepository{

    private val defaultController = DefaultController()
    private val movieListLiveData = MutableLiveData<MovieList>()
    private val movieLiveData = MutableLiveData<Movie>()

    private var currentPage = 1
    private var maxCount = 0

    fun getMovieList(): LiveData<MovieList>{

        return movieListLiveData
    }

    fun getMovieList(title: String): Single<MutableLiveData<MovieList>>{

        if (movieListLiveData.value?.Search == null || maxCount == 0 || movieListLiveData.value!!.Search!!.size < maxCount){

            return defaultController.movies(title, currentPage)
                .flatMap {movies->
                    addMovies(movies)
                    currentPage++
                    Single.just(movieListLiveData)
                }

        }

        return Single.just(movieListLiveData)
    }

    fun resetMovieList(){
        currentPage = 1
        maxCount = 0
        movieListLiveData.value = null
    }

    private fun addMovies(movies : MovieList){

        if (movieListLiveData.value?.Search != null && movies.Search != null){
            var newList = MovieList(movieListLiveData.value?.Search, movieListLiveData.value?.totalResults)
            newList.Search!!.addAll(movies.Search)
            movieListLiveData.value = newList
        }else if (movies.Search != null){
            movieListLiveData.value = movies
            maxCount = try{
                if (movies.totalResults != null){
                    movies.totalResults.toInt()
                }else{
                    0
                }
            }catch (ex:Exception){
                0
            }
        }

    }

    fun getMovie():LiveData<Movie>{
        return movieLiveData
    }
    fun getMovie(id: String) : Single<MutableLiveData<Movie>>{
        return defaultController.movie(id).flatMap { movie ->
            movieLiveData.value = movie
            Single.just(movieLiveData)
        }
    }

}