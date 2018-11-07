package com.example.maikbiskup.moviefinder.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.maikbiskup.moviefinder.R
import com.example.maikbiskup.moviefinder.databinding.ActivityDetailedSearchResultBinding
import android.databinding.DataBindingUtil
import android.webkit.URLUtil
import com.example.maikbiskup.moviefinder.helper.GlideApp
import com.example.maikbiskup.moviefinder.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_detailed_search_result.*


class DetailedSearchResult : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var query: String
    private lateinit var binding: ActivityDetailedSearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_search_result)

        setupQuery()
        setUpViewModel()
    }

    private fun setupQuery(){
        query = intent.getStringExtra(EXTRA_SEARCHRESULTDETAILED)
    }

    private fun setUpViewModel(){

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        viewModel.getMovie().observe(this, Observer{movie ->
            binding.viewModel = viewModel

            movie?.Poster?.let {poster ->
                if (URLUtil.isValidUrl(poster)){
                    GlideApp.with(this).load(poster).into(img_header)
                }
            }
        })

    }

    companion object {
        private const val EXTRA_SEARCHRESULTDETAILED = "com.example.maikbiskup.moviefinder.EXTRA_SEARCHRESULTDETAILED"

        fun getStartIntent(context: Context, query: String): Intent {
            var intent = Intent(context, DetailedSearchResult::class.java)
            intent.putExtra(EXTRA_SEARCHRESULTDETAILED, query)
            return intent
        }
    }
}
