package com.example.maikbiskup.moviefinder.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.maikbiskup.moviefinder.R
import com.example.maikbiskup.moviefinder.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieListViewModel:MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieListViewModel = MovieListViewModel(application)
    }

    fun startSearch(view: View){
        movieListViewModel.launchSearchResult(et_search_query.text.toString())
    }
}
