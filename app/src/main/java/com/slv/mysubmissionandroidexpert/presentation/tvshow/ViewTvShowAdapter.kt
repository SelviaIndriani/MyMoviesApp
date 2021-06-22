package com.slv.mysubmissionandroidexpert.presentation.tvshow

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
import com.slv.mysubmissionandroidexpert.core.domain.model.TvShow
import com.slv.mysubmissionandroidexpert.presentation.detail.DetailMovieActivity

class ViewTvShowAdapter :
    PagedListAdapter<TvShow, ViewTvShowAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    private var listTvShow = ArrayList<TvShow>()

    fun setViewTvShow(tvShow: List<TvShow>?) {
        if (tvShow == null) return
        this.listTvShow.clear()
        this.listTvShow.addAll(tvShow)
        notifyDataSetChanged()
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
                oldItem.tvId == newItem.tvId

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
                oldItem == newItem

        }
    }

    class MovieViewHolder(private val binding: ItemsViewMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val url = BuildConfig.IMG_URL
        fun bind(tvShow: TvShow) {
            with(binding) {
                mTitle.text = tvShow.title
                mRate.text = tvShow.rate.toString()
                mOverview.text = tvShow.overview

                Glide.with(itemView.context)
                    .load(url + tvShow.poster)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(mPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.apply {
                        putExtra(DetailMovieActivity.EXTRA_MOVIES, 1)
                        putExtra(DetailMovieActivity.EXTRA_ID, tvShow.tvId)
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
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }
}