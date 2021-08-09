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
import com.jmachado.placeholdermenu.model.Todos

class TodosAdapter  (private val onClick: (Todos) -> Unit):
    ListAdapter<Todos, TodosAdapter.TodosViewHolder>(TodosDiffCallback){

    class TodosViewHolder (itemView: View, val onClick: (Todos) -> Unit) : RecyclerView.ViewHolder(itemView)
    {
        private val useridTextView: TextView = itemView.findViewById(R.id.todos_userid)
        private val IdTextView: TextView = itemView.findViewById(R.id.todos_id)
        private val titleTextView: TextView = itemView.findViewById(R.id.todos_title)
        private val completedTextView: TextView = itemView.findViewById(R.id.todos_completed)

        private var currentEntity: Todos? = null
        private val cardView: CardView = itemView.findViewById(R.id.todos_card_view)

        init {

            itemView.setOnClickListener {
                currentEntity?.let {
                    onClick(it)
                }
            }
        }

        fun bind(entity: Todos) {
            currentEntity = entity

            useridTextView.text = entity.userId.toString()
            IdTextView.text = entity.id.toString()
            titleTextView.text = entity.title
            completedTextView.text = entity.completed.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todos_item, parent, false)
        return TodosViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

object TodosDiffCallback : DiffUtil.ItemCallback<Todos>() {
    override fun areItemsTheSame(oldItem: Todos, newItem: Todos): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Todos, newItem: Todos): Boolean {
        return oldItem.id == newItem.id
    }
}