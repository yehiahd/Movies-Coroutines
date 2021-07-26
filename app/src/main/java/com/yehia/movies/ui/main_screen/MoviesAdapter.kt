package com.yehia.movies.ui.main_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yehia.movies.R
import com.yehia.movies.model.Movie
import com.yehia.movies.util.Constant.Api.BASE_IMAGE_URL

class MoviesAdapter :
    PagingDataAdapter<Movie, MoviesAdapter.MoviesHolder>(MovieComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        return MoviesHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class MoviesHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val posterImg: ImageView = itemView.findViewById(R.id.posterImg)

        fun bind(item: Movie) {
            Picasso.get()
                .load("${BASE_IMAGE_URL}${item.posterPath}")
                .into(posterImg)

            itemView.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }

    }

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    private var onItemClickListener: ((Movie) -> Unit)? = null

    fun setOnItemClickListener(callback: (Movie) -> Unit) {
        onItemClickListener = callback
    }


}