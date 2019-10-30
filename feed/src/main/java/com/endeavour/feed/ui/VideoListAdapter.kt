package com.endeavour.feed.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.endeavour.core.vo.Video
import com.endeavour.feed.R
import com.endeavour.feed.databinding.VideoItemBinding

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
                bind(createOnClickListener(binding,video.id), video)
            }
        }
    }

    private fun createOnClickListener(binding : VideoItemBinding, videoId: String): View.OnClickListener {
        return View.OnClickListener {
            val directions = MainFragmentDirections.viewVideoDetails(videoId)
            val extras = FragmentNavigatorExtras(
                binding.videoTitle to "title_$videoId",
                binding.videoDuration to "duration_$videoId",
                binding.videoThumbnail to "thumbnail_$videoId")
            it.findNavController().navigate(directions, extras)
        }
    }

    class ViewHolder(val binding: VideoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, value: Video) {
            ViewCompat.setTransitionName(binding.videoTitle, "title_${value.id}")
            ViewCompat.setTransitionName(binding.videoDuration, "duration_${value.id}")
            ViewCompat.setTransitionName(binding.videoThumbnail, "thumbnail_${value.id}")
            with(binding) {
                this.video = value
                executePendingBindings()
            }
            binding.root.setOnClickListener(listener)
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