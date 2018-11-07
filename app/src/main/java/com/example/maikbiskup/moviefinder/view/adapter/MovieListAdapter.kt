package com.example.maikbiskup.moviefinder.view.adapter

import android.app.Activity
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.URLUtil
import com.example.maikbiskup.moviefinder.R
import com.example.maikbiskup.moviefinder.databinding.ItemMovieBinding
import com.example.maikbiskup.moviefinder.helper.GlideApp
import com.example.maikbiskup.moviefinder.model.data_model.Movie
import com.example.maikbiskup.moviefinder.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.activity_detailed_search_result.*


class MovieListAdapter (private var movieListViewModel: MovieListViewModel, private var query: String, private var acitvity: Activity):
    RecyclerView.Adapter<MovieListAdapter.BindingHolder>() {

    private var movies: MutableList<Movie> = ArrayList()
    private val buffer = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return BindingHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        if (position >= movies.size - buffer){
            movieListViewModel.doSearch(query)
        }
        val item = movies[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun addItems(newMovies: MutableList<Movie>) {
        if (newMovies.isEmpty()){
            this.movies = newMovies
            notifyDataSetChanged()
        }else{
            newMovies.forEach{mov->
                if (!movies.contains(mov)){
                    addItem(mov)
                }
            }
        }
    }

    fun addItem(movie : Movie) {
        movies.add(movie)
        notifyItemInserted(movies.size - 1)
    }

    inner class BindingHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(movie: Movie) {
            binding.movie = movie
            if (URLUtil.isValidUrl(movie.Poster)){
                GlideApp.with(acitvity).load(movie.Poster).into(binding.viewholderImg)
            }
            binding.cardViewMovie.setOnClickListener{
                movieListViewModel.onClickSearchResult(binding.movie?.imdbID)
            }

        }
    }

}