package com.endeavour.gmbn.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.endeavour.gmbn.R
import com.endeavour.gmbn.databinding.MainFragmentBinding
import com.endeavour.gmbn.di.InjectionUtils
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels {
        InjectionUtils.provideMainViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<MainFragmentBinding>(
            inflater, R.layout.main_fragment, container, false).apply {
            status = viewModel.viewStatus
            lifecycleOwner = this@MainFragment
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = VideoListAdapter()
        videos_list.adapter = adapter
        videos_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetchIds()
                }
            }
        })

        viewModel.videos.observe(viewLifecycleOwner, Observer {
            val result = it.data
            if (!result.isNullOrEmpty()) adapter.submitList(result)
        })

        retry.setOnClickListener { viewModel.fetchIds() }
    }

}
