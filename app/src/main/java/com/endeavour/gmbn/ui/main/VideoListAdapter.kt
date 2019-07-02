package com.endeavour.gmbn.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.endeavour.gmbn.R
import com.endeavour.gmbn.databinding.VideoItemBinding
import com.endeavour.gmbn.vm.Video

class VideoListAdapter : ListAdapter<Video, VideoListAdapter.ViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.video_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { video ->
            with(holder) {
                itemView.tag = video
                bind(createOnClickListener(video.id), video)
            }
        }
    }

    private fun createOnClickListener(videoId: String): View.OnClickListener {
        return View.OnClickListener {
            val directions = MainFragmentDirections.viewVideoDetails(videoId)
            it.findNavController().navigate(directions)
        }
    }

    class ViewHolder(private val binding: VideoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, value: Video) {
            with(binding) {
                clickListener = listener
                video = value
                executePendingBindings()
            }
        }
    }
}

private class VideoDiffCallback : DiffUtil.ItemCallback<Video>() {

    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.thumbnail == newItem.thumbnail
                && oldItem.title == oldItem.title
                && oldItem.description == oldItem.description
    }
}