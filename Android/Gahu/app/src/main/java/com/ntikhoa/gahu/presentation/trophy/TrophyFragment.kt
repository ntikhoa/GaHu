package com.ntikhoa.gahu.presentation.trophy

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.databinding.FragmentTrophyBinding
import com.ntikhoa.gahu.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrophyFragment : BaseFragment(R.layout.fragment_trophy) {

    private var _binding: FragmentTrophyBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTrophyBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}