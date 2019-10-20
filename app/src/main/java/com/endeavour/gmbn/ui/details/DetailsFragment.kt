package com.endeavour.gmbn.ui.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.endeavour.gmbn.databinding.DetailsFragmentBinding
import com.endeavour.gmbn.di.InjectionUtils
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : Fragment() {

    private lateinit var binding: DetailsFragmentBinding
    private val args: DetailsFragmentArgs by navArgs()
    private val detailsViewModel: DetailsViewModel by viewModels {
        InjectionUtils.provideDetailsViewModelFactory(requireContext(), args.videoId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DetailsFragmentBinding.inflate(inflater).apply {
            this.video = detailsViewModel.video
            this.viewModel = detailsViewModel
            lifecycleOwner = this@DetailsFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(binding.videoTitle, "title_${args.videoId}")
        ViewCompat.setTransitionName(binding.videoDuration, "duration_${args.videoId}")
        ViewCompat.setTransitionName(binding.videoThumbnail, "thumbnail_${args.videoId}")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = CommentsListAdapter()
        comments_list.adapter = adapter

        detailsViewModel.comments.observe(viewLifecycleOwner, Observer {
            val result = it.data
            if (!result.isNullOrEmpty()) adapter.submitList(result)
        })
    }

}
