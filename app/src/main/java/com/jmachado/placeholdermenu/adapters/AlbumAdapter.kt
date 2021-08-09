package com.jmachado.placeholdermenu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jmachado.placeholdermenu.R
import com.jmachado.placeholdermenu.model.Album
import com.jmachado.placeholdermenu.model.Post

class AlbumAdapter (private val onClick: (Album) -> Unit):
    ListAdapter<Album, AlbumAdapter.AlbumViewHolder>(AlbumDiffCallback){

    class AlbumViewHolder (itemView: View, val onClick: (Album) -> Unit) : RecyclerView.ViewHolder(itemView)
    {
        private val album_useridTextView: TextView = itemView.findViewById(R.id.album_userid)
        private val album_IdTextView: TextView = itemView.findViewById(R.id.album_id)
        private val album_titleTextView: TextView = itemView.findViewById(R.id.album_title)

        private var currentEntity: Album? = null
        private val cardView: CardView = itemView.findViewById(R.id.album_card_view)

        init {

            itemView.setOnClickListener {
                currentEntity?.let {
                    onClick(it)
                }
            }
        }

        fun bind(entity: Album) {
            currentEntity = entity

            album_useridTextView.text = entity.userId.toString()
            album_IdTextView.text = entity.id.toString()
            album_titleTextView.text = entity.title

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.album_item, parent, false)
        return AlbumViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

object AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }
}