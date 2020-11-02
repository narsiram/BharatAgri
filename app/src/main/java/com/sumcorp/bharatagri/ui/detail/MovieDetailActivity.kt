package com.sumcorp.bharatagri.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.sumcorp.bharatagri.R
import com.sumcorp.bharatagri.data.local.MovieDatabase
import com.sumcorp.bharatagri.data.local.entity.model.MovieData
import com.sumcorp.bharatagri.data.repo.MovieRepoImplementation
import com.sumcorp.bharatagri.ui.base.BaseActivity
import com.sumcorp.bharatagri.ui.viewModel.MovieViewModel
import com.sumcorp.bharatagri.ui.viewModel.ViewModelFactory
import com.sumcorp.sliceassignment.retrofit.NetworkHelper
import com.sumcorp.sliceassignment.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : BaseActivity() {

    private lateinit var viewModel: MovieViewModel
    private var movieId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movieId = intent.getStringExtra("movieId")!!
        setupViewModel()
        observeViewModel()

        initToolbar()

    }

    private fun observeViewModel() {
        viewModel.movieResponse.observe(this, Observer {
            setMovieData(it)
        })

        viewModel.errorResponse.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setMovieData(data: MovieData) {
        val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/original"
        tvTitle.text = data.title
        tvTagLine.text = data.tagline
        tvRating.text = getString(R.string.rating, data.voteAverage.toString())
        tvStatus.text = getString(R.string.release_status, data.releaseStatus)
        tvBudget.text = getString(R.string.budget, data.budget)
        tvRevenue.text = getString(R.string.revenue, data.revenue)
        if (data.genere.size > 0)
            tvGenere.text = getString(R.string.genere, data.genere.get(0).name)
        tvOverview.text = data.overview

        Glide.with(this)
            .load(IMAGE_BASE_URL + data.posterPath)
            .centerCrop()
            .into(imageView)

        Glide.with(this)
            .load(IMAGE_BASE_URL + data.backgroundPath)
            .into(ivBackground)

    }

    fun initToolbar() {
        setSupportActionBar(toolbar)

        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                NetworkHelper(this),
                MovieRepoImplementation(
                    MovieDatabase.getInstance(this).movieDao(),
                    RetrofitBuilder.buildService()
                )
            )
        )[MovieViewModel::class.java]

        viewModel.getMovieDetail(movieId)
    }
}