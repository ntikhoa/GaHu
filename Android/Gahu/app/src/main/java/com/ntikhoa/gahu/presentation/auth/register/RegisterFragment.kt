package com.ntikhoa.gahu.presentation.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ntikhoa.gahu.presentation.MainActivity
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.business.domain.util.ErrorHandler.Companion.EMAIL_ALREADY_EXIST
import com.ntikhoa.gahu.business.domain.util.SuccessHandler.Companion.REGISTER_SUCCESSFULLY
import com.ntikhoa.gahu.business.interactor.util.isValidEmail
import com.ntikhoa.gahu.databinding.FragmentRegisterBinding
import com.ntikhoa.gahu.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment(R.layout.fragment_register),
    View.OnClickListener {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)

        subscribeObserver()
        setOnClickListener()
    }

    private fun subscribeObserver() {
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            displayProgressBar(state.isLoading)

            state.message?.let { message ->
                println("state: $message")
                handleMessage(message)
            }
        })
    }

    private fun handleMessage(message: String) {
        when (message) {
            REGISTER_SUCCESSFULLY -> {
                gotoMainActivity()
            }
            EMAIL_ALREADY_EXIST -> {
                showError(EMAIL_ALREADY_EXIST)
            }
        }
    }

    private fun showError(message: String) {
        binding.apply {
            tvError.visibility = View.VISIBLE
            binding.tvError.text = message
        }
    }

    private fun gotoMainActivity() {
        startActivity(Intent(context, MainActivity::class.java))
        activity?.finish()
    }

    private fun setOnClickListener() {
        binding.btnRegister.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        displayProgressBar(false)
        viewModel.cancelJob()
        _binding = null
    }

    override fun onClick(view: View?) {
        binding.apply {
            when (view?.id) {
                R.id.btn_register -> {
                    if (isValidInput()) {
                        viewModel.onTriggerEvent(
                            RegisterEvent.Register(
                                etEmail.text.toString(),
                                etUsername.text.toString(),
                                etPassword.text.toString(),
                                etConfirmPassword.text.toString()
                            )
                        )
                    }
                }
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
            if (etUsername.text.isNullOrBlank()) {
                etUsername.error = "Username cannot be blank"
                isValid = false
            }
            if (etPassword.text.isNullOrBlank()) {
                etPassword.error = "Password cannot be blank"
                isValid = false
            }
            if (etPassword.text.toString() != etConfirmPassword.text.toString()) {
                etConfirmPassword.error = "Confirm password does not match"
                isValid = false
            }
            return isValid
        }
    }
}