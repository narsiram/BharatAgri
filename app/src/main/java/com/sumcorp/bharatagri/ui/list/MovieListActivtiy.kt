package com.sumcorp.bharatagri.ui.list

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumcorp.bharatagri.R
import com.sumcorp.bharatagri.data.local.MovieDatabase
import com.sumcorp.bharatagri.data.local.entity.model.MovieData
import com.sumcorp.bharatagri.data.repo.MovieRepoImplementation
import com.sumcorp.bharatagri.ui.base.BaseActivity
import com.sumcorp.bharatagri.ui.detail.MovieDetailActivity
import com.sumcorp.bharatagri.ui.util.Constants
import com.sumcorp.bharatagri.ui.util.EndlessRecyclerViewScrollListener
import com.sumcorp.bharatagri.ui.viewModel.MovieViewModel
import com.sumcorp.bharatagri.ui.viewModel.ViewModelFactory
import com.sumcorp.sliceassignment.retrofit.NetworkHelper
import com.sumcorp.sliceassignment.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_movie_list_activtiy.*
import kotlin.collections.ArrayList

class MovieListActivtiy : BaseActivity(), MovieListAdapter.OnItemClick {

    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MovieListAdapter
    private var page = 1
    private var sortingString = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list_activtiy)

        setupViewModel()
        observeViewModel()
        setUpRecyclerView()

        listeners()
    }

    private fun listeners() {
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            page = 1
            sortingString = when (i) {
                R.id.rbPopularity -> Constants.POPULARITY
                R.id.rbRating -> Constants.RATING
                R.id.rbReleaseDate -> Constants.RELEASED_DATE
                else -> ""
            }

            getData()
        }
    }

    private fun observeViewModel() {
        viewModel.movieListResponse.observe(this, Observer {
            setDataToAdapter(it.results)
        })

        viewModel.errorResponse.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
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

        getData()
    }

    private fun setUpRecyclerView() {
        recyclerView.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(this, 3)

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        adapter = MovieListAdapter(this)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(p: Int, totalItemsCount: Int, view: RecyclerView?) {

                page = p + 1
                getData()

            }
        })
    }

    private fun getData() {
        viewModel.getMovies(page, sortingString)

    }

    private fun setDataToAdapter(movieList: ArrayList<MovieData>) {
        var isClear = false
        if (page == 1)
            isClear = true
        adapter.setData(movieList, isClear)
    }

    override fun onItemClick(movieId: String) {
        startActivity(Intent(this, MovieDetailActivity::class.java).putExtra("movieId", movieId))
    }
}