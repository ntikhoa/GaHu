package com.ntikhoa.gahu.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ntikhoa.gahu.presentation.MainActivity
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.business.domain.util.ErrorHandler.Companion.EMAIL_NOT_EXIST
import com.ntikhoa.gahu.business.domain.util.ErrorHandler.Companion.WRONG_PASSWORD
import com.ntikhoa.gahu.business.domain.util.SuccessHandler.Companion.LOGIN_SUCCESSFULLY
import com.ntikhoa.gahu.databinding.FragmentLoginBinding
import com.ntikhoa.gahu.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment :
    BaseFragment(R.layout.fragment_login),
    View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        subscribeObserver()
        setBtnOnClickListener()
    }

    private fun subscribeObserver() {
        viewModel.state.observe(viewLifecycleOwner, Observer { loginState ->
            displayProgressBar(loginState.isLoading)

            loginState.message?.let {
                handleMessage(it)
            }
        })
    }

    private fun handleMessage(message: String) {
        when (message) {
            LOGIN_SUCCESSFULLY -> {
                goToMainActivity()
            }
            EMAIL_NOT_EXIST -> {
                showError(EMAIL_NOT_EXIST)
            }
            WRONG_PASSWORD -> {
                showError(WRONG_PASSWORD)
            }
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(activity, MainActivity::class.java))
        activity?.finish()
    }


    private fun showError(error: String) {
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = error
    }

    private fun setBtnOnClickListener() {
        binding.apply {
            btnLogin.setOnClickListener(this@LoginFragment)
            btnRegister.setOnClickListener(this@LoginFragment)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_login -> {
                if (isValidInput()) {
                    viewModel.onTriggerEvent(
                        LoginEvent.Login(
                            binding.etEmail.text.toString(),
                            binding.etPassword.text.toString()
                        )
                    )
                }
            }
            R.id.btn_register -> {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun isValidInput(): Boolean {
        binding.apply {
            var isValid = true
            if (etEmail.text.isNullOrBlank()) {
                etEmail.error = "Email cannot be blank"
                isValid = false
            }
            if (!etEmail.text.toString().isValidEmail()) {
                etEmail.error = "Invalid email"
                isValid = false
            }
            if (etPassword.text.isNullOrBlank()) {
                etPassword.error = "Password cannot be blank"
                isValid = false
            }
            return isValid
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        displayProgressBar(false)
        viewModel.cancelJob()
        _binding = null
    }
}