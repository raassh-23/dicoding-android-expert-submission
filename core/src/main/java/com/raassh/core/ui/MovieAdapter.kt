package com.raassh.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raassh.core.R
import com.raassh.core.databinding.ItemListMovieBinding
import com.raassh.core.ui.model.Movie
import java.util.ArrayList

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_movie, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListMovieBinding.bind(itemView)
        private val context = itemView.context

        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.posterPath)
                    .into(ivItemImage)
                tvItemTitle.text = data.title
                tvItemSubtitle.text = if (data.voteCount == 0) {
                    context.getString(R.string.no_vote)
                } else {
                    context.getString(R.string.vote_avg, data.voteAverage.toString())
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}