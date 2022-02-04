package com.ntikhoa.gahu.presentation.trophy

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.business.domain.model.TrophyGame
import com.ntikhoa.gahu.business.domain.model.TrophyProfile
import com.ntikhoa.gahu.business.interactor.util.formatThousand
import com.ntikhoa.gahu.databinding.FragmentTrophyBinding
import com.ntikhoa.gahu.presentation.BaseFragment
import com.ntikhoa.gahu.presentation.trophy.adapter.TrophyGameAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrophyFragment : BaseFragment(R.layout.fragment_trophy) {

    private var _binding: FragmentTrophyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TrophyViewModel by viewModels()

    private lateinit var adapter: TrophyGameAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTrophyBinding.bind(view)

        adapter = TrophyGameAdapter()
        binding.rvTrophyGame.adapter = adapter

        subscribeObserver()

        viewModel.onTriggerEvent(TrophyEvent.GetTrophy())
    }

    private fun subscribeObserver() {
        viewModel.state.observe(viewLifecycleOwner, Observer { dataState ->
            displayProgressBar(dataState.isLoading)

            dataState.trophyProfile?.let {
                setTrophyView(it)
                adapter.submitList(it.recentPlayed)
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

            tvUsername.text = trophyProfile.username
            tvEarnedTrophies.text = trophyProfile.trophy.totalTrophies.formatThousand()
            tvGamesPlayed.text = trophyProfile.gamesPlayed.formatThousand()
            tvWorldRank.text = trophyProfile.worldRank.formatThousand()

            setTrophyLevel(trophyProfile.trophyLevel)
            layoutTrophy.apply {
                tvPlatinum.text = trophyProfile.trophy.platinum.toString()
                tvGold.text = trophyProfile.trophy.gold.toString()
                tvSilver.text = trophyProfile.trophy.silver.toString()
                tvBronze.text = trophyProfile.trophy.bronze.toString()
            }
        }
    }

    private fun setTrophyLevel(level: Int) {
        binding.layoutTrophy.tvTrophyLevel.text = level.toString()
        when (level) {
            in 1..99 -> {
                setTrophyLevelView(R.drawable.level_bronze1, R.color.bronze)
            }
            in 100..199 -> {
                setTrophyLevelView(R.drawable.level_bronze2, R.color.bronze)
            }
            in 200..299 -> {
                setTrophyLevelView(R.drawable.level_bronze3, R.color.bronze)
            }

            in 300..399 -> {
                setTrophyLevelView(R.drawable.level_silver1, R.color.silver)
            }
            in 400..499 -> {
                setTrophyLevelView(R.drawable.level_silver2, R.color.silver)
            }
            in 500..599 -> {
                setTrophyLevelView(R.drawable.level_silver3, R.color.silver)
            }

            in 600..699 -> {
                setTrophyLevelView(R.drawable.level_gold1, R.color.gold)
            }
            in 700..799 -> {
                setTrophyLevelView(R.drawable.level_gold2, R.color.gold)
            }
            in 800..998 -> {
                setTrophyLevelView(R.drawable.level_gold3, R.color.gold)
            }
            999 -> {
                setTrophyLevelView(R.drawable.level_platinum, R.color.platinum)
            }
        }
    }

    private fun setTrophyLevelView(resId: Int, colorRes: Int) {
        binding.layoutTrophy.apply {
            ivTrophyLevel.setImageResource(resId)
            tvTrophyLevel.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    colorRes
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.cancelJob()
    }
}