package com.sumcorp.bharatagri.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sumcorp.bharatagri.data.repo.MovieRepository
import com.sumcorp.sliceassignment.retrofit.NetworkHelper

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private var networkHelper: NetworkHelper,
    private var tweetRepository: MovieRepository
) : ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(networkHelper, tweetRepository) as T
    }
}