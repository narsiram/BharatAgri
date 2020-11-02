package com.sumcorp.bharatagri.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumcorp.bharatagri.R
import com.sumcorp.bharatagri.data.local.entity.model.MovieData
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.ArrayList

class MovieListAdapter(onClick: OnItemClick) : RecyclerView.Adapter<MovieListAdapter.Holder>() {

    var movieList: ArrayList<MovieData>? = null
    var onItemClick: OnItemClick? = null

    init {
        movieList = ArrayList()
        this.onItemClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )


    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (movieList != null) {
            holder.bind(movieList!!.get(position))
        }

        holder.itemView.setOnClickListener {
            onItemClick!!.onItemClick(movieList!!.get(holder.adapterPosition).id)
        }

    }

    override fun getItemCount(): Int {
        if (movieList != null) {
            return movieList!!.size
        } else
            return 0
    }

    fun setData(movieList: ArrayList<MovieData>, isClear: Boolean) {

        if (isClear) {
            this.movieList!!.clear()
        }
        this.movieList!!.addAll(movieList)
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: MovieData) {
            itemView.txtName.text = data.title
            itemView.tvRating.text = data.voteAverage.toString()
            itemView.tvDate.text = data.releaseDate

            Glide.with(itemView.context)
                .load("http://image.tmdb.org/t/p/w342" + data.posterPath)
                .centerCrop()
                .into(itemView.imageView)

        }
    }

    interface OnItemClick {
        fun onItemClick(movieId: String)
    }
}