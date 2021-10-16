package com.ntikhoa.gahu.presentation.account


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.business.domain.util.Constants.Companion.EXTRA_LOGIN
import com.ntikhoa.gahu.business.domain.util.SuccessHandler
import com.ntikhoa.gahu.business.domain.util.SuccessHandler.Companion.LOGOUT_SUCCESSFULLY
import com.ntikhoa.gahu.databinding.FragmentAccountBinding
import com.ntikhoa.gahu.presentation.BaseFragment
import com.ntikhoa.gahu.presentation.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragment(R.layout.fragment_account), View.OnClickListener {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAccountBinding.bind(view)

        subscribeObserver()
        setOnClick()
    }

    private fun setOnClick() {
        binding.apply {
            btnLogout.setOnClickListener(this@AccountFragment)
        }
    }

    private fun subscribeObserver() {
        viewModel.state.observe(viewLifecycleOwner, Observer { dataState ->
            dataState.message?.let { message ->
                handleMessage(message)
            }
        })
    }

    private fun handleMessage(message: String) {
        when (message) {
            LOGOUT_SUCCESSFULLY -> {
                gotoLogin()
            }
        }
    }

    private fun gotoLogin() {
        activity?.apply {
            intent = Intent(context, AuthActivity::class.java)
            intent.putExtra(EXTRA_LOGIN, true)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_logout -> {
                viewModel.onTriggerEvent(AccountEvent.Logout())
            }
        }
    }
}