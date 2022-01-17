package com.ntikhoa.gahu.presentation.game


import android.os.Bundle
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
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            handlePlatformState(state.platformState)
            handleGameState(state.gameState)
        })
    }

    private fun handleGameState(gameState: GameListState.GameState) {
        displayProgressBar(gameState.isLoading)

        gameState.games?.let {
            gameAdapter.submitList(it)
        }

        gameState.message?.let {
            handleMessage(it)
        }
    }

    private fun handlePlatformState(platformState: GameListState.PlatformState) {
        displayProgressBar(platformState.isLoading)

        platformState.platforms?.let {
            platformAdapter.submitList(it.toMutableList())
        }

        platformState.message?.let {
            handleMessage(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.cancelJob()
    }
}