package com.ntikhoa.gahu.presentation.game


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.business.domain.model.Platform
import com.ntikhoa.gahu.databinding.FragmentGameListBinding
import com.ntikhoa.gahu.presentation.BaseFragment
import com.ntikhoa.gahu.presentation.game.adapter.GameAdapter
import com.ntikhoa.gahu.presentation.game.adapter.PlatformAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GameListFragment : BaseFragment(R.layout.fragment_game_list) {
    private val TAG = "GameListFragment"

    private val viewModel: GameViewModel by viewModels()

    private var _binding: FragmentGameListBinding? = null
    private val binding get() = _binding!!

    private lateinit var platformAdapter: PlatformAdapter
    private lateinit var gameAdapter: GameAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGameListBinding.bind(view)

        initRecyclerView()

        subscribeObserver()
        viewModel.onTriggerEvent(GameEvent.GetPlatforms())
        viewModel.onTriggerEvent(GameEvent.GetGames())
    }

    private fun initRecyclerView() {
        initPlatformRv()
        initGameRv()
    }

    private fun initPlatformRv() {
        platformAdapter = PlatformAdapter()
        binding.rvPlatform.adapter = platformAdapter
        platformAdapter.setOnItemClickListener {
            Log.i(TAG, "onItemClick: ${it.id}")
            var platformId: String? = it.id
            if (it == Platform.ALL_PLATFORM) {
                platformId = null
            }
            viewModel.onTriggerEvent(GameEvent.SetPlatformFilter(platformId))
            viewModel.onTriggerEvent(GameEvent.GetGames())
        }
    }

    private fun initGameRv() {
        gameAdapter = GameAdapter()
        binding.rvGame.adapter = gameAdapter

        binding.rvGame.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == gameAdapter.itemCount.minus(1)
                    && viewModel.gameState.value?.isLoading == false
                    && viewModel.gameState.value?.isExhausted == false
                ) {
                    Log.i(TAG, "onScrollStateChanged: getting next page")
                    viewModel.onTriggerEvent(GameEvent.GetGamesNextPage())
                }
            }
        })
    }

    private fun subscribeObserver() {
        subscribePlatformState()
        subscribeGameState()
    }

    private fun subscribePlatformState() {
        viewModel.platformState.observe(viewLifecycleOwner, Observer { state ->
            //displayProgressBar(platformState.isLoading)

            state.platforms?.let {
                if (it.isNotEmpty()) {
                    Log.i(TAG, "subscribePlatformState: It get submitted: $it")
                    platformAdapter.submitList(it.toMutableList())
                }
            }

            state.message?.let {
                handleMessage(it)
            }
        })
    }

    private fun subscribeGameState() {
        viewModel.gameState.observe(viewLifecycleOwner, Observer { state ->

            displayProgressBar(state.isLoading)

            state.games?.let {
                if (it.isNotEmpty()) {
                    Log.i(TAG, "subscribeGameState: It get submitted: $it")
                    gameAdapter.submitList(it)
                }
            }

            state.message?.let {
                handleMessage(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.cancelJob()
    }
}