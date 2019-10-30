package com.endeavour.details.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.endeavour.core.vo.Comment
import com.endeavour.details.R
import com.endeavour.details.databinding.CommentsItemBinding

class CommentsListAdapter : ListAdapter<Comment, CommentsListAdapter.ViewHolder>(CommentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.comments_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { comment ->
            with(holder) {
                itemView.tag = comment
                bind(comment)
            }
        }
    }

    class ViewHolder(private val binding: CommentsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind( value: Comment) {
            with(binding) {
                comment = value
                executePendingBindings()
            }
        }
    }
}

private class CommentDiffCallback : DiffUtil.ItemCallback<Comment>() {

    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.text == oldItem.text
    }
}