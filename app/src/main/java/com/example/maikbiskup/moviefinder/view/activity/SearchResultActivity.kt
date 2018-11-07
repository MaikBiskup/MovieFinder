package com.example.maikbiskup.moviefinder.view.activity

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.maikbiskup.moviefinder.R
import com.example.maikbiskup.moviefinder.view.adapter.MovieListAdapter
import com.example.maikbiskup.moviefinder.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.activity_search_result.*
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.maikbiskup.moviefinder.databinding.ActivitySearchResultBinding


class SearchResultActivity : AppCompatActivity() {

    private lateinit var movieListAdapter : MovieListAdapter
    private lateinit var viewModel: MovieListViewModel
    private lateinit var binding: ActivitySearchResultBinding

    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result)

        setupQuery()
        setUpViewModel()
        setupRecyclerView()
    }

    private fun setupQuery(){
        query = intent.getStringExtra(EXTRA_SEARCHRESULT)
        tv_searchresult.text = String.format(getString(R.string.header_searchentries), query)
    }

    private fun setupRecyclerView(){
        var spanCount = 2

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 3
        }

        movieListAdapter = MovieListAdapter(viewModel, query, this)
        rv_resultlist.adapter = movieListAdapter
        rv_resultlist.setHasFixedSize(false)
        rv_resultlist.layoutManager = GridLayoutManager(this, spanCount)
    }

    private fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)

        viewModel.getMovieList().observe(this, Observer{movieList->
            binding.viewModel = viewModel
            if (movieList?.Search != null){
                movieListAdapter.addItems(movieList.Search)
                tv_searchresult_count.text = String.format(getString(R.string.entries), movieList.totalResults.toString())
                tv_searchresult_count.visibility = View.VISIBLE
            }
        })

    }

    companion object {
        private const val EXTRA_SEARCHRESULT = "com.example.maikbiskup.moviefinder.EXTRA_SEARCHRESULT"

        fun getStartIntent(context: Context, query: String): Intent {
            var intent = Intent(context, SearchResultActivity::class.java)
            intent.putExtra(EXTRA_SEARCHRESULT, query)
            return intent
        }
    }
}
