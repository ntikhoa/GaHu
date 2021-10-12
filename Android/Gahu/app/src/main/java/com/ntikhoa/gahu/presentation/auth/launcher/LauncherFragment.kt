package com.ntikhoa.gahu.presentation.auth.launcher

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ntikhoa.gahu.presentation.MainActivity
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.business.domain.util.ErrorHandler.Companion.CHECK_PREV_AUTH_FAILED
import com.ntikhoa.gahu.business.domain.util.SuccessHandler.Companion.CHECK_PREV_AUTH_SUCCESSFULLY
import com.ntikhoa.gahu.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LauncherFragment : BaseFragment(R.layout.fragment_launcher) {

    private val viewModel: LauncherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObserver()
        viewModel.onTriggerEvent(LauncherEvent.CheckPrevAuth())
    }

    private fun subscribeObserver() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            handleMessage(it)
        })
    }

    private fun handleMessage(it: String) {
        when (it) {
            CHECK_PREV_AUTH_SUCCESSFULLY -> {
                startActivity(Intent(context, MainActivity::class.java))
                activity?.finish()
            }
            CHECK_PREV_AUTH_FAILED -> {
                findNavController().navigate(R.id.action_launcherFragment_to_loginFragment)
            }
        }
    }
}