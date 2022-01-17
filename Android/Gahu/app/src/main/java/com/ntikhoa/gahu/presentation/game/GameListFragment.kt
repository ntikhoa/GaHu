package com.ntikhoa.gahu.presentation.game


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ntikhoa.gahu.R
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
        platformAdapter = PlatformAdapter()
        binding.rvPlatform.adapter = platformAdapter

        gameAdapter = GameAdapter()
        binding.rvGame.adapter = gameAdapter
    }

    private fun subscribeObserver() {
        subscribePlatformState()
        subscribeGameState()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.cancelJob()
    }
}