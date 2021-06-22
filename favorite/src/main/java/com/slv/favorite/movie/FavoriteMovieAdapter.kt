package com.slv.favorite.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.slv.mysubmissionandroidexpert.BuildConfig
import com.slv.mysubmissionandroidexpert.R
import com.slv.mysubmissionandroidexpert.core.databinding.ItemsViewMoviesBinding
import com.slv.mysubmissionandroidexpert.core.domain.model.Movie
import com.slv.mysubmissionandroidexpert.presentation.detail.DetailMovieActivity

class FavoriteMovieAdapter :
    PagedListAdapter<Movie, FavoriteMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    private var listMovie = ArrayList<Movie>()

    fun setFavMovie(movie: List<Movie>?) {
        if (movie == null) return
        this.listMovie.clear()
        this.listMovie.addAll(movie)
        notifyDataSetChanged()
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

        }
    }

    class MovieViewHolder(private val binding: ItemsViewMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val url = BuildConfig.IMG_URL
        fun bind(movie: Movie) {
            with(binding) {
                mTitle.text = movie.title
                mRate.text = movie.rate.toString()
                mOverview.text = movie.overview

                Glide.with(itemView.context)
                    .load(url + movie.poster)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(mPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.apply {
                        putExtra(DetailMovieActivity.EXTRA_MOVIES, 0)
                        putExtra(DetailMovieActivity.EXTRA_ID, movie.movieId)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsViewMoviesBinding =
            ItemsViewMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsViewMoviesBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }
}