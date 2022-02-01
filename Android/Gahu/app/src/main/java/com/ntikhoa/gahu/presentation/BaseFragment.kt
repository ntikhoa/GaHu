package com.ntikhoa.gahu.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.ntikhoa.gahu.business.domain.util.Constants
import com.ntikhoa.gahu.business.domain.util.Constants.Companion.NETWORK_ERROR_MSG
import com.ntikhoa.gahu.business.domain.util.Constants.Companion.TIMEOUT_ERROR_MSG
import com.ntikhoa.gahu.business.domain.util.Constants.Companion.UNKNOWN_ERROR
import com.ntikhoa.gahu.business.domain.util.ErrorHandler
import com.ntikhoa.gahu.business.domain.util.ErrorHandler.Companion.NETWORK_ERROR
import com.ntikhoa.gahu.presentation.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment(
    @LayoutRes val layoutRes: Int
) : Fragment(layoutRes) {

    var fragmentName = ""

    fun displayProgressBar(isVisible: Boolean) {
        (activity as BaseActivity).displayProgressBar(isVisible)
    }

    private fun networkError() {
        (activity as BaseActivity).networkError()
    }

    private fun unknownError() {
        (activity as BaseActivity).unknownError()
    }

    private fun timeoutError() {
        (activity as BaseActivity).timeoutError()
    }

    open fun handleMessage(message: String) {
        when (message) {
            UNKNOWN_ERROR -> {
                unknownError()
            }
            NETWORK_ERROR -> {
                networkError()
            }
            TIMEOUT_ERROR_MSG -> {
                timeoutError()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fragmentName != "")
            println("$fragmentName: onViewCreated")
    }

    override fun onDetach() {
        if (fragmentName != "")
            println("$fragmentName: onDetach")
        super.onDetach()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (fragmentName != "")
            println("$fragmentName: onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (fragmentName != "")
            println("$fragmentName: onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentName != "")
            println("$fragmentName: onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (fragmentName != "")
            println("$fragmentName: onStart")
    }

    override fun onResume() {
        super.onResume()
        if (fragmentName != "")
            println("$fragmentName: onResume")
    }

    override fun onPause() {
        super.onPause()
        if (fragmentName != "")
            println("$fragmentName: onPause")
    }

    override fun onStop() {
        super.onStop()
        if (fragmentName != "")
            println("$fragmentName: onStop")
        displayProgressBar(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (fragmentName != "")
            println("$fragmentName: onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (fragmentName != "")
            println("$fragmentName: onDestroy")
    }
}