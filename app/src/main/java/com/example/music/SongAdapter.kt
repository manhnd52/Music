package com.example.music

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(
    private val onItemClick: (Song) -> Unit
) : ListAdapter<Song, SongAdapter.SongViewHolder>(object : DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean = oldItem.title == newItem.title
    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean = oldItem == newItem
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)
        holder.bind(song)
    }

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(android.R.id.text1)
        private val artist: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(song: Song) {
            title.text = song.title
            artist.text = song.artist
            itemView.setOnClickListener { onItemClick(song) }
        }
    }
}