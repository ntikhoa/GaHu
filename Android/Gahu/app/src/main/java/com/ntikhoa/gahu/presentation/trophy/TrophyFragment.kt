package com.ntikhoa.gahu.presentation.trophy

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.business.domain.model.Trophy
import com.ntikhoa.gahu.databinding.FragmentTrophyBinding
import com.ntikhoa.gahu.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrophyFragment : BaseFragment(R.layout.fragment_trophy) {

    private var _binding: FragmentTrophyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TrophyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTrophyBinding.bind(view)

        subscribeObserver()

        viewModel.onTriggerEvent(TrophyEvent.GetTrophy())
    }

    private fun subscribeObserver() {
        viewModel.state.observe(viewLifecycleOwner, Observer { dataState ->
            displayProgressBar(dataState.isLoading)

            dataState.trophy?.let {
                setTrophyView(it)
            }

            dataState.message?.let {
                handleMessage(it)
            }
        })
    }

    private fun setTrophyView(trophy: Trophy) {
        binding.apply {
            Glide.with(requireContext())
                //.setDefaultRequestOptions(requestOptions) //for cache image
                .load(trophy.avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(ivAvatar)

            //"%,d".format(trophy.totalTrophies.toString())

            tvUsername.text = trophy.username
            tvEarnedTrophies.text = "%,d".format(trophy.totalTrophies)
            tvGamesPlayed.text = "%,d".format(trophy.gamesPlayed)
            tvWorldRank.text = "%,d".format(trophy.worldRank)

            layoutTrophy.apply {
                tvTrophyLevel.text = trophy.trophyLevel.toString()
                tvPlatinum.text = trophy.platinum.toString()
                tvGold.text = trophy.gold.toString()
                tvSilver.text = trophy.silver.toString()
                tvBronze.text = trophy.bronze.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.cancelJob()
    }
}