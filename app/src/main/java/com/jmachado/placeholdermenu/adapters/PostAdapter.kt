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
import com.jmachado.placeholdermenu.model.Post

class PostAdapter (private val onClick: (Post) -> Unit):
    ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback){

    class PostViewHolder (itemView: View, val onClick: (Post) -> Unit) : RecyclerView.ViewHolder(itemView)
    {

        private val post_useridTextView: TextView = itemView.findViewById(R.id.post_userid)
        private val post_IdTextView: TextView = itemView.findViewById(R.id.post_id)
        private val post_titleTextView: TextView = itemView.findViewById(R.id.post_title)
        private val post_bodyTextView: TextView = itemView.findViewById(R.id.post_body)

        private var currentEntity: Post? = null
        private val cardView: CardView = itemView.findViewById(R.id.card_view)

        init {

            itemView.setOnClickListener {
                currentEntity?.let {
                    onClick(it)
                }
            }
        }

        fun bind(entity: Post) {
            currentEntity = entity

            post_useridTextView.text = entity.userId.toString()
            post_IdTextView.text = entity.id.toString()
            post_titleTextView.text = entity.title
            post_bodyTextView.text = entity.body

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }
}