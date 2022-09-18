package com.raassh.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raassh.core.R
import com.raassh.core.databinding.ItemListMovieBinding
import com.raassh.core.ui.model.Movie
import com.raassh.core.utils.loadImage

class MovieAdapter : ListAdapter<Movie, MovieAdapter.ListViewHolder>(DIFF_CALLBACK) {
    var onItemClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_movie, parent, false)
        )

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = currentList[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListMovieBinding.bind(itemView)
        private val context = itemView.context

        fun bind(data: Movie) {
            with(binding) {
                ivItemImage.loadImage(data.posterPath)
                tvItemTitle.text = data.title.ifEmpty { context.getString(R.string.no_title) }
                tvItemRating.text = if (data.voteCount == 0) {
                    context.getString(R.string.no_vote)
                } else {
                    context.getString(R.string.vote_avg, data.voteAverage.toString())
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(currentList[adapterPosition])
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}