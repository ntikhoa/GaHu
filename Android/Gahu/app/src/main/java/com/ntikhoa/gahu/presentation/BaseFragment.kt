package com.ntikhoa.gahu.presentation

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.ntikhoa.gahu.presentation.auth.AuthActivity

abstract class BaseFragment(@LayoutRes val layoutRes: Int) : Fragment(layoutRes) {

    fun displayProgressBar(isVisible: Boolean) {
        (activity as BaseActivity).displayProgressBar(isVisible)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        displayProgressBar(false)
    }
}