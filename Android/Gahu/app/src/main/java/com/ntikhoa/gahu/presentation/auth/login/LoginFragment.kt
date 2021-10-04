package com.ntikhoa.gahu.presentation.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        setBtnOnClickListener()
    }

    private fun setBtnOnClickListener() {
        binding.apply {
            btnLogin.setOnClickListener(this@LoginFragment)
            btnRegister.setOnClickListener(this@LoginFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_login -> {
                viewModel.onTriggerEvent(
                    LoginEvent.Login(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                )
            }
            R.id.btn_register -> {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            else -> {
                throw Exception("Button id is not handle")
            }
        }
    }
}