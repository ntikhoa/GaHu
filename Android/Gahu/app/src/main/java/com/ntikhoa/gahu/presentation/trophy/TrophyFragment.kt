package com.ntikhoa.gahu.presentation.trophy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.business.domain.model.TrophyProfile
import com.ntikhoa.gahu.business.interactor.util.formatThousand
import com.ntikhoa.gahu.databinding.FragmentTrophyBinding
import com.ntikhoa.gahu.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

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

            dataState.trophyProfile?.let {
                setTrophyView(it)
            }

            dataState.message?.let {
                handleMessage(it)
            }
        })
    }

    private fun setTrophyView(trophyProfile: TrophyProfile) {
        binding.apply {
            Glide.with(requireContext())
                //.setDefaultRequestOptions(requestOptions) //for cache image
                .load(trophyProfile.avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(ivAvatar)

            //"%,d".format(trophy.totalTrophies.toString())

            tvUsername.text = trophyProfile.username
            tvEarnedTrophies.text = trophyProfile.trophy.totalTrophies.formatThousand()
            tvGamesPlayed.text = trophyProfile.gamesPlayed.formatThousand()
            tvWorldRank.text = trophyProfile.worldRank.formatThousand()

            layoutTrophy.apply {
                tvTrophyLevel.text = trophyProfile.trophyLevel.toString()
                tvPlatinum.text = trophyProfile.trophy.platinum.toString()
                tvGold.text = trophyProfile.trophy.gold.toString()
                tvSilver.text = trophyProfile.trophy.silver.toString()
                tvBronze.text = trophyProfile.trophy.bronze.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.cancelJob()
    }
}