package com.ntikhoa.gahu.presentation.game


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.databinding.FragmentGameListBinding
import com.ntikhoa.gahu.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GameListFragment : BaseFragment(R.layout.fragment_game_list) {

    private val viewModel: GameViewModel by viewModels()

    private var _binding: FragmentGameListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGameListBinding.bind(view)

        viewModel.onTriggerEvent(GameEvent.GetPlatforms())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.cancelJob()
    }
}