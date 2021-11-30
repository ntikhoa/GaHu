package com.ntikhoa.gahu.presentation.game


import android.os.Bundle
import android.view.View
import android.widget.Adapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.business.domain.model.Platform
import com.ntikhoa.gahu.databinding.FragmentGameListBinding
import com.ntikhoa.gahu.presentation.BaseFragment
import com.ntikhoa.gahu.presentation.game.adapter.PlatformAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GameListFragment : BaseFragment(R.layout.fragment_game_list) {

    private val viewModel: GameViewModel by viewModels()

    private var _binding: FragmentGameListBinding? = null
    private val binding get() = _binding!!

    private lateinit var platformAdapter: PlatformAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGameListBinding.bind(view)

        initRecyclerView()

        subscribeObserver()
        viewModel.onTriggerEvent(GameEvent.GetPlatforms())
    }

    private fun initRecyclerView() {
        platformAdapter = PlatformAdapter()
        binding.rvPlatform.adapter = platformAdapter
    }

    private fun subscribeObserver() {
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            displayProgressBar(state.isLoading)

            state.platforms?.let {
                platformAdapter.submitList(it.toMutableList())
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