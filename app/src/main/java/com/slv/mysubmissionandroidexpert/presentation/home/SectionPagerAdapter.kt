package com.slv.mysubmissionandroidexpert.presentation.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.slv.mysubmissionandroidexpert.presentation.movie.MovieFragment
import com.slv.mysubmissionandroidexpert.presentation.tvshow.TvShowFragment

class SectionPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> MovieFragment()
            1 -> TvShowFragment()
            else -> Fragment()
        }
}