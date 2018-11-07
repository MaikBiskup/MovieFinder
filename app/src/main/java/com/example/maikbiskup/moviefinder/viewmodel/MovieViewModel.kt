package com.example.maikbiskup.moviefinder.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import android.widget.Toast
import com.example.maikbiskup.moviefinder.R
import com.example.maikbiskup.moviefinder.model.data_access.ProjectRepository
import com.example.maikbiskup.moviefinder.model.data_access.remote.ErrorHandling.ExtendedErrorHandling
import com.example.maikbiskup.moviefinder.model.data_model.Movie
import de.vectron_systems_ag.gethappy.consumer.rest.retrofit.ErrorHandling.SimpleErrorHandler
import io.reactivex.functions.Consumer

class MovieViewModel(private var applicationContext: Application): AndroidViewModel(applicationContext) {

    private var movieObservable: LiveData<Movie>
    private val RATING_SOURCE_KEY = "Rotten Tomatoes"
    private var bussy = false

    private var errorHandler = SimpleErrorHandler()


    init {
        movieObservable = ProjectRepository.getMovie()

        errorHandler.setExtendedErrorHandling(object: ExtendedErrorHandling {

            override fun onError() {
                bussy = false
                Toast.makeText(applicationContext, applicationContext.getString(R.string.onError), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun doSearchDetailed(imdbID: String){



        if (imdbID.isNotEmpty()){
            bussy = true
            ProjectRepository.getMovie(imdbID.trim()).subscribe (Consumer{ _ ->
                bussy = false
            }, errorHandler)
        }
    }

    fun getShowSpinner(): Int {
        if (bussy){
            return View.VISIBLE
        }

        return View.GONE
    }

    fun getMovie(): LiveData<Movie>{
        return movieObservable
    }

    // Getter

    fun getActors(): String {

        var ret = ""

        if (movieObservable.value != null) {
            if (movieObservable.value!!.Actors != null) {
                val actors = movieObservable.value!!.Actors!!.split(',')

                actors.forEach{actor ->
                    ret += actor.trim() + "\n"
                }
            }
        }

        return ret
    }

    fun getTitle():String{
        if (movieObservable.value != null){
            if (movieObservable.value!!.Title != null){
                return movieObservable.value!!.Title!!
            }
        }
        return ""
    }

    fun getYear():String{
        if (movieObservable.value != null){
            if (movieObservable.value!!.Year != null){
                return movieObservable.value!!.Year!!
            }
        }
        return ""
    }

    fun getDescription():String{
        var description = applicationContext.getString(R.string.year_header) + " " + getYear() + "\n\n"

        description += applicationContext.getString(R.string.actors_header) + "\n" + getActors() + "\n"

        description += applicationContext.getString(R.string.plot_header) + "\n" + getPlot()

        return description
    }

    fun getPlot():String{

        var ret = ""

        if (movieObservable.value != null){
            if (movieObservable.value!!.Plot != null){
                ret += movieObservable.value!!.Plot!!
            }
        }
        return ret
    }

    fun getRating():Float{
        if (movieObservable.value != null){
            if (movieObservable.value!!.Ratings != null){
                if (movieObservable.value!!.Ratings!!.isNotEmpty()){

                    val value = movieObservable.value!!.Ratings!!.find { rating ->
                        rating.Source == RATING_SOURCE_KEY
                    }?.Value

                    if (value != null){
                        val numberStr = value.trim('%')

                        return try{
                            (numberStr.toInt()/20.0f)
                        }catch (ex: Exception){
                            0.0f
                        }
                    }
                }
            }
        }
        return 0.0f

    }


}