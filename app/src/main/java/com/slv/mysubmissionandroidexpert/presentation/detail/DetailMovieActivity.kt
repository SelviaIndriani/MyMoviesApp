package com.slv.mysubmissionandroidexpert.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.slv.mysubmissionandroidexpert.BuildConfig
import com.slv.mysubmissionandroidexpert.R
import com.slv.mysubmissionandroidexpert.core.domain.model.Movie
import com.slv.mysubmissionandroidexpert.core.domain.model.TvShow
import com.slv.mysubmissionandroidexpert.databinding.ActivityDetailMovieBinding
import com.slv.mysubmissionandroidexpert.databinding.ContentDetailMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()

    private lateinit var detailContentBinding: ContentDetailMovieBinding
    private lateinit var binding: ActivityDetailMovieBinding

    companion object {
        const val EXTRA_MOVIES = "extra_movies"
        const val EXTRA_ID = "extra_id"
    }

    private var titleMovies: String? = ""
    private var idMovies: String? = ""
    private var isFavorite = false

    private val url = BuildConfig.IMG_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        detailContentBinding = binding.contentDetail
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight = 400
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        loadProgress(true)
        val extras = intent.extras
        if (extras != null) {

            val id = extras.getInt(EXTRA_ID, 0)
            when (extras.getInt(EXTRA_MOVIES)) {
                0 -> {
                    detailViewModel.setSelectedId(id)
                    detailViewModel.getMovie.observe(this, { movie ->
                        movie.data?.let { populateMovie(it) }
                        val fav = movie.data?.isFavorite
                        if (fav != null) {
                            isFavorite = fav
                            setFavorite(isFavorite)
                        }
                        loadProgress(false)
                    })

                    binding.favAction.setOnClickListener {
                        if (!isFavorite) {
                            detailViewModel.setMoviesFav()
                            Toast.makeText(this, "Added to favorite movies", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            detailViewModel.setMoviesFav()
                            Toast.makeText(this, "Remove from favorite movies", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                1 -> {
                    detailViewModel.setSelectedId(id)
                    detailViewModel.getTvShow.observe(this, { tv ->
                        tv.data?.let { populateTvShow(it) }
                        val fav = tv.data?.isFavorite
                        if (fav != null) {
                            isFavorite = fav
                            setFavorite(isFavorite)
                        }
                        loadProgress(false)
                    })

                    binding.favAction.setOnClickListener {
                        if (!isFavorite) {
                            detailViewModel.setTvShowFav()
                            Toast.makeText(this, "Added to favorite Tv Show", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            detailViewModel.setTvShowFav()
                            Toast.makeText(this, "Remove from favorite Tv Show", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.m_share) {
            shareMovie()
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    private fun populateTvShow(tvShow: TvShow) {
        detailContentBinding.apply {
            dmTitle.text = tvShow.title
            dmStatus.text = tvShow.status
            dmRate.text = tvShow.rate.toString()
            dmGenre.text = tvShow.genre
            dmDuration.text = tvShow.duration.toString() + "m"
            dmDate.text = tvShow.release_date
            dmOverview.text = tvShow.overview
        }

        this.titleMovies = tvShow.title
        this.idMovies = tvShow.tvId.toString()

        Glide.with(this)
            .load(url + tvShow.poster)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.dmImgPoster)

        supportActionBar?.title = "${tvShow.title}"
    }

    @SuppressLint("SetTextI18n")
    private fun populateMovie(movie: Movie) {

        detailContentBinding.apply {
            dmTitle.text = movie.title
            dmRate.text = movie.rate.toString()
            dmStatus.text = movie.status
            dmGenre.text = movie.genre
            dmDuration.text = movie.duration.toString() + "m"
            dmDate.text = movie.release_date
            dmOverview.text = movie.overview
        }

        this.titleMovies = movie.title
        this.idMovies = movie.movieId.toString()

        Glide.with(this)
            .load(url + movie.poster)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.dmImgPoster)

        supportActionBar?.title = "${movie.title}"
    }

    private fun shareMovie() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val sub = this.resources.getString(
            R.string.sub_movie_share,
            "$titleMovies"
        )
        val titleShare = this.resources.getString(R.string.title_share, "$titleMovies")
        intent.putExtra(Intent.EXTRA_TEXT, sub)
        startActivity(Intent.createChooser(intent, titleShare))
    }

    private fun setFavorite(fav: Boolean) {
        if (!fav) {
            binding.favAction.setImageResource(R.drawable.ic_baseline_favorite_border_24)

        } else {
            binding.favAction.setImageResource(R.drawable.ic_favorite1)
        }
    }

    private fun loadProgress(load: Boolean) {
        binding.progressBar.visibility = if (load) View.VISIBLE else View.GONE
    }
}