package com.endeavour.gmbn.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.endeavour.gmbn.R
import com.endeavour.gmbn.databinding.DetailsFragmentBinding
import com.endeavour.gmbn.di.InjectionUtils
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private val detailsViewModel: DetailsViewModel by viewModels {
        InjectionUtils.provideDetailsViewModelFactory(requireContext(), args.videoId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<DetailsFragmentBinding>(
            inflater, R.layout.details_fragment, container, false
        ).apply {
            this.video = detailsViewModel.video
            this.viewModel = detailsViewModel
            lifecycleOwner = this@DetailsFragment
        }
        return binding.root
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
