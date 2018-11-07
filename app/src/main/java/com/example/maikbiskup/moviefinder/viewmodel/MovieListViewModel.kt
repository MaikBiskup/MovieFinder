package com.example.maikbiskup.moviefinder.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.view.View
import android.widget.Toast
import com.example.maikbiskup.moviefinder.R
import com.example.maikbiskup.moviefinder.model.data_access.ProjectRepository
import com.example.maikbiskup.moviefinder.model.data_access.remote.ErrorHandling.ExtendedErrorHandling
import com.example.maikbiskup.moviefinder.model.data_model.Movie
import com.example.maikbiskup.moviefinder.model.data_model.MovieList
import com.example.maikbiskup.moviefinder.view.activity.DetailedSearchResult
import com.example.maikbiskup.moviefinder.view.activity.SearchResultActivity
import de.vectron_systems_ag.gethappy.consumer.rest.retrofit.ErrorHandling.SimpleErrorHandler
import io.reactivex.functions.Consumer


class MovieListViewModel(private var applicationContext: Application) : AndroidViewModel(applicationContext) {

    private var movieListObservable: LiveData<MovieList>
    private var bussy = false
    private var errorHandler = SimpleErrorHandler()


    init {
        movieListObservable = ProjectRepository.getMovieList()

        errorHandler.setExtendedErrorHandling(object: ExtendedErrorHandling{

            override fun onError() {
                bussy = false
                Toast.makeText(applicationContext, applicationContext.getString(R.string.onError), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getShowSpinner(): Int {
        if (bussy){
            return View.VISIBLE
        }

        return View.GONE
    }

    private fun resetData(){
        ProjectRepository.resetMovieList()
    }



    fun doSearch(title: String){
        if (title.isNotEmpty()){
            bussy = true


            ProjectRepository.getMovieList(title.trim()).subscribe(Consumer { _ ->
                if (checkListEmpty()){
                    Toast.makeText(applicationContext, applicationContext.getString(R.string.noResults), Toast.LENGTH_SHORT).show()
                }
                bussy = false
            }, errorHandler)
        }
    }

    private fun checkListEmpty(): Boolean{
        if (movieListObservable.value?.Search != null){
            if (movieListObservable.value?.Search!!.isNotEmpty()){
                return false
            }
        }

        return true
    }
    fun getMovieList() : LiveData<MovieList>{

        return movieListObservable
    }

    // Launching

    fun onClickSearchResult(query: String?) {
        if (query != null){
            launchSearchResultDetailed(query)
        }
    }

    fun launchSearchResult(query: String){
        if (query.length >= 3){
            applicationContext.startActivity(SearchResultActivity.getStartIntent(applicationContext, query))
            resetData()
            doSearch(query)
        }else{
            Toast.makeText(applicationContext, applicationContext.getString(R.string.queryToShort), Toast.LENGTH_SHORT).show()
        }

    }

    fun launchSearchResultDetailed(query: String){
        applicationContext.startActivity(DetailedSearchResult.getStartIntent(applicationContext, query))
        MovieViewModel(applicationContext).doSearchDetailed(query)
    }
}